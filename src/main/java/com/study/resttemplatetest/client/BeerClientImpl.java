package com.study.resttemplatetest.client;

import com.study.resttemplatetest.model.BeerDto;
import com.study.resttemplatetest.model.BeerDtoPage;
import com.study.resttemplatetest.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    public static final String BEER_PATH = "/api/v1/beer";

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDto> getAllBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
                                     Integer pageNumber, Integer pageSize) {
        var restTemplate = restTemplateBuilder.build();
        var uriComponentsBuilder = UriComponentsBuilder.fromPath(BEER_PATH);

        buildQueryParam(uriComponentsBuilder, beerName, beerStyle, showInventory, pageNumber, pageSize);

        var pageResponseEntity = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDtoPage.class);

        return pageResponseEntity.getBody();
    }

    private void buildQueryParam(UriComponentsBuilder uriComponentsBuilder, String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }
        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle.name());
        }
        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", showInventory);
        }
        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", pageNumber);
        }
        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", pageSize);
        }
    }

    @Override
    public Page<BeerDto> getAllBeers() {
        return getAllBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDto> getAllBeers(String beerName) {
        return getAllBeers(beerName, null, null, null, null);
    }

    @Override
    public Page<BeerDto> getAllBeers(String beerName, BeerStyle beerStyle) {
        return getAllBeers(beerName, beerStyle, null, null, null);
    }

    @Override
    public Page<BeerDto> getAllBeers(String beerName, BeerStyle beerStyle, Boolean showInventory) {
        return getAllBeers(beerName, beerStyle, showInventory, null, null);
    }

    @Override
    public Page<BeerDto> getAllBeers(Integer pageNumber, Integer pageSize) {
        return getAllBeers(null, null, null, pageNumber, pageSize);
    }
}
