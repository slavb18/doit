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
import java.util.List;
import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ilb.doit.model.Work;
import ru.ilb.doit.repositories.WorkRepository;
import ru.ilb.doit.api.WorksResource;

@Path("works")
@Api("works")
public class WorksResourceImpl implements WorksResource {
    
    @Autowired
    private WorkRepository workRepository;

    @Override
    public List<Work> list() {
        return workRepository.findAll();
    }

    @Override
    public long create(Work work) {
        return workRepository.save(work).getId();
    }
    
}
