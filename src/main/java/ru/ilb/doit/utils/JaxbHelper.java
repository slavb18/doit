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
package ru.ilb.doit.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Source;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author slavb
 */
@Component
public class JaxbHelper {

    @Autowired
    ContextResolver<JAXBContext> jaxbContextResolver;

    /**
     * unmarshalls object instance
     *
     * @param <T>
     * @param source example from String: new StreamSource(new java.io.StringReader(string)), from InputStream: new StreamSource(is)
     * @param type
     * @param mediaType
     * @return
     */
    public <T> T unmarshal(Source source, Class<T> type, String mediaType) {

        JAXBContext jaxbContext = jaxbContextResolver.getContext(type);
        try {
            T result;
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, mediaType);
            result = (T) unmarshaller.unmarshal(source, type);

            return result;

        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * unmarshalls list of object instances
     *
     * @param <T>
     * @param source example from String: new StreamSource(new java.io.StringReader(string)), from InputStream: new StreamSource(is)
     * @param type
     * @param mediaType
     * @return
     */
    public <T> List<T> unmarshalList(Source source, Class<T> type, String mediaType) {

        JAXBContext jaxbContext = jaxbContextResolver.getContext(type);
        try {
            List<T> result = new ArrayList();
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, mediaType);
            Collection tmp = (Collection) unmarshaller.unmarshal(source, type).getValue();
            for (Object element : tmp) {
                result.add((T) JAXBIntrospector.getValue(element));
            }
            return result;

        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String marshal(Object obj, String mediaType) {

        StringWriter sw = new StringWriter();
        XmlType ann = obj.getClass().getAnnotation(XmlType.class);
        ann.name();
        try {
            JAXBContext jaxbContext = jaxbContextResolver.getContext(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("eclipselink.media-type", mediaType);
            if (MediaType.APPLICATION_JSON.equals(mediaType)) {
                marshaller.setProperty("eclipselink.json.include-root", false);
            }
            marshaller.marshal(obj, sw);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }

        return sw.toString();

    }

}
