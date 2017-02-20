/*
 * Copyright 2016 slavb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ilb.doit.providers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author slavb
 */
public class AuthorizationHandler implements ContainerRequestFilter {

    private final ThreadLocal currentAuditor = new ThreadLocal();

    private String xremoteUsersGroup;

    public void setXremoteUsersGroup(String xremoteUsersGroup) {
        this.xremoteUsersGroup = xremoteUsersGroup;
    }



    public String getCurrentAuditor() {
        return (String) currentAuditor.get();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        SecurityContext securityContext = requestContext.getSecurityContext();
        String userName = securityContext.getUserPrincipal().getName();
        if (xremoteUsersGroup!=null && securityContext.isUserInRole(xremoteUsersGroup)) {
            String xremoteUserName = requestContext.getHeaderString("X-Remote-User");
            if (xremoteUserName != null) {
                userName = xremoteUserName;
            }
        }
        currentAuditor.set(userName);
    }
}
