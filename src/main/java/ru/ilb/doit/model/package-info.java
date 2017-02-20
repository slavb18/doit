@javax.xml.bind.annotation.XmlSchema(namespace = "urn:ru:ilb:doit:model", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
@XmlAccessorFactory(LazyAccessorFactoryImpl.class)
@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateXmlAdapter.class),
    @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeXmlAdapter.class),
    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
})
@XmlSchemaTypes({
    @XmlSchemaType(name = "date", type = LocalDate.class),
    @XmlSchemaType(name = "time", type = LocalTime.class),
    @XmlSchemaType(name = "dateTime", type = LocalDateTime.class)
})
package ru.ilb.doit.model;
import com.migesok.jaxb.adapter.javatime.LocalDateTimeXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalDateXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;

import com.sun.xml.internal.bind.XmlAccessorFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSchemaTypes;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import ru.ilb.common.jpa.jaxb.LazyAccessorFactoryImpl;

