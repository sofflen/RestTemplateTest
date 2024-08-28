package com.study.resttemplatetest.client;

import com.study.resttemplatetest.model.BeerDto;
import com.study.resttemplatetest.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testCreateBeer() {
        var newBeerDto = BeerDto.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(323)
                .upc("123321")
                .price(new BigDecimal("10.99"))
                .build();

        var savedDto = beerClient.createBeer(newBeerDto);
        assertNotNull(savedDto);
    }

    @Test
    void testUpdateBeer() {
        var newBeerDto = BeerDto.builder()
                .beerName("Mango Bobs - 2")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(323)
                .upc("123321")
                .price(new BigDecimal("10.99"))
                .build();

        var savedDto = beerClient.createBeer(newBeerDto);
        final String newBeerName = "Mango Bobs - 3 - updated";

        savedDto.setBeerName(newBeerName);
        savedDto = beerClient.updateBeer(savedDto);

        assertEquals(newBeerName, savedDto.getBeerName());
    }

    @Test
    void testDeleteBeer() {
        var newBeerDto = BeerDto.builder()
                .beerName("Mango Bobs 2")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(323)
                .upc("123321")
                .price(new BigDecimal("10.99"))
                .build();

        var savedDto = beerClient.createBeer(newBeerDto);

        beerClient.deleteBeer(savedDto.getId());

        assertThrows(HttpClientErrorException.class, () -> beerClient.getBeerById(savedDto.getId()));
    }
}
