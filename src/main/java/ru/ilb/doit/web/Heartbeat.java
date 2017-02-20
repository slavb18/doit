/*
 * Copyright 2017 slavb.
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
package ru.ilb.doit.web;

import io.swagger.annotations.Api;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author slavb
 */
@Path("heartbeat")
@Api("heartbeat")
public class Heartbeat {

    @PersistenceContext(unitName = "doit")
    private EntityManager em;

    @GET
    @Transactional
    public String heartbeat() {
        // http://wiki.eclipse.org/EclipseLink/Examples/JPA/EMAPI#Getting_a_JDBC_Connection_from_an_EntityManager
        // @Transactional required
        em.unwrap(Connection.class);
        return "OK";
    }

}
