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

    public static final String BASE_URL_PATH = "http://localhost:8080";
    public static final String BEER_PATH = "/api/v1/beer";

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDto> getAllBeers() {
        var restTemplate = restTemplateBuilder.build();
        //different ways of usage
        var stringResponseEntity = restTemplate
                .getForEntity(BASE_URL_PATH + BEER_PATH, String.class);
        var mapResponseEntity = restTemplate
                .getForEntity(BASE_URL_PATH + BEER_PATH, Map.class);
        var jsonResponseEntity = restTemplate
                .getForEntity(BASE_URL_PATH + BEER_PATH, JsonNode.class);

        jsonResponseEntity.getBody().findPath("content").elements()
                .forEachRemaining(node -> System.out.println(node.get("beerName").asText()));

        //Proper Mapping
        var pageResponseEntity = restTemplate
                .getForEntity(BASE_URL_PATH + BEER_PATH, BeerDtoPage.class);

        return null;
    }
}
