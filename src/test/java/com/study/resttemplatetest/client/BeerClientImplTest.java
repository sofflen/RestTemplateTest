package com.study.resttemplatetest.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    private BeerClient beerClient;

    @Test
    void getAllBeers() {
        beerClient.getAllBeers();
    }
}