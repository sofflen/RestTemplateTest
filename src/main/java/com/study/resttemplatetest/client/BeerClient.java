package com.study.resttemplatetest.client;

import com.study.resttemplatetest.model.BeerDto;
import com.study.resttemplatetest.model.BeerStyle;
import org.springframework.data.domain.Page;

public interface BeerClient {

    Page<BeerDto> getAllBeers();

    Page<BeerDto> getAllBeers(String beerName);

    Page<BeerDto> getAllBeers(String beerName, BeerStyle beerStyle);

    Page<BeerDto> getAllBeers(String beerName, BeerStyle beerStyle, Boolean showInventory);

    Page<BeerDto> getAllBeers(Integer pageNumber, Integer pageSize);

    Page<BeerDto> getAllBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
                              Integer pageNumber, Integer pageSize);
}
