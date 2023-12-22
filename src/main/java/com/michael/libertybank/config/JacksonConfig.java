package com.michael.libertybank.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

import static com.michael.libertybank.util.GeneralConstants.DATETIME_FORMAT;
import static com.michael.libertybank.util.GeneralConstants.LOCAL_DATE_TIME_SERIALIZER;

@Configuration
public class JacksonConfig {
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        return objectMapper;
//    }
    @Bean("libertyBankObjectMapper")
    public ObjectMapper objectMapper(@Qualifier("libertyBankTimeModule")JavaTimeModule javaTimeModule) {
        return JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .defaultDateFormat(new SimpleDateFormat(DATETIME_FORMAT))
                .addModule(javaTimeModule)
                .build();
    }

    @Bean("libertyBankTimeModule")
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATE_TIME_SERIALIZER);
        return module;
    }
}
