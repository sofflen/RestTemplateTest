package com.study.resttemplatetest.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.study.resttemplatetest.model.BeerDto;
import com.study.resttemplatetest.model.BeerDtoPage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    public static final String BEER_PATH = "/api/v1/beer";

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDto> getAllBeers() {
        var restTemplate = restTemplateBuilder.build();
        var pageResponseEntity = restTemplate.getForEntity(BEER_PATH, BeerDtoPage.class);

        return pageResponseEntity.getBody();
    }
}
