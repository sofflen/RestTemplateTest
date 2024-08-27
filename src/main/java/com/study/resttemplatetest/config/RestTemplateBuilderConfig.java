package com.study.resttemplatetest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateBuilderConfig {

    @Value("${rest.template.root-url}")
    private String rootUrl;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        var restTemplateBuilder = configurer.configure(new RestTemplateBuilder());
        var uriBuilderFactory = new DefaultUriBuilderFactory(rootUrl);

        return restTemplateBuilder.uriTemplateHandler(uriBuilderFactory);
    }
}
