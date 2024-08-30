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
    @Value(("${rest.template.auth.username}"))
    private String authName;
    @Value(("${rest.template.auth.password}"))
    private String authPassword;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        var restTemplateBuilder = new RestTemplateBuilder();
        var uriBuilderFactory = new DefaultUriBuilderFactory(rootUrl);

        return configurer
                .configure(restTemplateBuilder)
                .basicAuthentication(authName, authPassword)
                .uriTemplateHandler(uriBuilderFactory);
    }
}
