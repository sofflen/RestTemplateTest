package com.study.resttemplatetest.client;

import com.study.resttemplatetest.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    private BeerClient beerClient;

    private BeerDto testBeerDto;

    @BeforeEach
    void setUp() {
        testBeerDto = beerClient.getAllBeers().getContent().getFirst();
    }

    @Test
    void testGetAllBeersWithBeerName() {
        beerClient.getAllBeers("ALE");
    }

    @Test
    void testGetAllBeersWithNoBeerName() {
        beerClient.getAllBeers(null);
    }

    @Test
    void testGetBeerById() {
        var beerById = beerClient.getBeerById(testBeerDto.getId());
        assertNotNull(beerById);
    }
}
