package com.study.resttemplatetest.client;

import com.study.resttemplatetest.model.BeerDto;
import org.springframework.data.domain.Page;

public interface BeerClient {
    Page<BeerDto> getAllBeers();
}
