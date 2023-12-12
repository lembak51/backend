package com.example.demo.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.exception.spi.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.concurrent.ThreadLocalRandom.current;
//Useless class
public class GeneratorForUser   implements IdentifierGenerator {

    private String prefix;

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {


        return current().nextInt(0,100);
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }
}
