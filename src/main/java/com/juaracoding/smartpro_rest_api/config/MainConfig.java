package com.juaracoding.smartpro_rest_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juaracoding.smartpro_rest_api.security.Crypto;
import org.apache.commons.codec.digest.Crypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/***
 * Author: Michael, 2025-05-19
 * Last updated date: 2025-06-01
 * Bug fixed:
 * Jackson LocalDateTime Serialization Issues
 */

@Configuration
public class MainConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url(Crypto.performDecrypt(env.getProperty("spring.datasource.url")));
        dataSourceBuilder.username(Crypto.performDecrypt(env.getProperty("spring.datasource.username")));
        dataSourceBuilder.password(Crypto.performDecrypt(env.getProperty("spring.datasource.password")));

        return dataSourceBuilder.build();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
