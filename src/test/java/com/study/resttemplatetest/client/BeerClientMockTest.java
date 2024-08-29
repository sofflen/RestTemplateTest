package com.study.resttemplatetest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.resttemplatetest.config.RestTemplateBuilderConfig;
import com.study.resttemplatetest.model.BeerDto;
import com.study.resttemplatetest.model.BeerDtoPage;
import com.study.resttemplatetest.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static com.study.resttemplatetest.client.BeerClientImpl.BEER_ID_PATH;
import static com.study.resttemplatetest.client.BeerClientImpl.BEER_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withAccepted;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest(BeerClientImpl.class)
@Import(RestTemplateBuilderConfig.class)
public class BeerClientMockTest {

    static final String URL = "http://localhost:8080";

    @Autowired
    BeerClient beerClient;
    @Autowired
    MockRestServiceServer mockRestServiceServer;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    ObjectMapper objectMapper;

    BeerDto testDto;
    UUID testDtoId;
    String testDtoJsonString;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        testDto = getBeerDto();
        testDtoId = testDto.getId();
        testDtoJsonString = objectMapper.writeValueAsString(testDto);
    }

    @Test
    void testGetAllBeers() throws JsonProcessingException {
        var response = objectMapper.writeValueAsString(getPage());

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestTo(URL + BEER_PATH))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        var dtosPage = beerClient.getAllBeers();
        assertThat(dtosPage.getContent().size()).isGreaterThan(0);

        mockRestServiceServer.verify();
    }

    @Test
    void testGetAllBeersWithQueryParam() throws JsonProcessingException {
        var response = objectMapper.writeValueAsString(getPage());
        String queryName = "beerName";
        String queryValue = "ALE";

        var uri = UriComponentsBuilder.fromUriString(URL + BEER_PATH)
                .queryParam(queryName, queryValue)
                .build().toUri();

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestTo(uri))
                .andExpect(queryParam(queryName, queryValue))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        var responsePage = beerClient.getAllBeers(queryValue);

        assertThat(responsePage.getContent().size()).isGreaterThan(0);

        mockRestServiceServer.verify();
    }

    @Test
    void testGetBeerById() {
        mockGetByIdRequest();

        var responseDto = beerClient.getBeerById(testDtoId);
        assertThat(responseDto.getId()).isEqualTo(testDtoId);

        mockRestServiceServer.verify();
    }

    @Test
    void testCreateBeer() {
        var uri = UriComponentsBuilder.fromPath(BEER_ID_PATH).build(testDtoId);

        mockRestServiceServer.expect(method(HttpMethod.POST))
                .andExpect(requestTo(URL + BEER_PATH))
                .andRespond(withAccepted().location(uri));

        mockGetByIdRequest();

        var responseDto = beerClient.createBeer(testDto);
        assertThat(responseDto.getId()).isEqualTo(testDtoId);

        mockRestServiceServer.verify();
    }

    @Test
    void testUpdateBeer() {
        mockRestServiceServer.expect(method(HttpMethod.PUT))
                .andExpect(requestToUriTemplate(URL + BEER_ID_PATH, testDtoId))
                .andRespond(withNoContent());

        mockGetByIdRequest();

        var responseDto = beerClient.updateBeer(testDto);
        assertThat(responseDto.getId()).isEqualTo(testDtoId);

        mockRestServiceServer.verify();
    }

    @Test
    void testDeleteBeer() {
        mockRestServiceServer.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL + BEER_ID_PATH, testDtoId))
                .andRespond(withNoContent());

        beerClient.deleteBeer(testDtoId);

        mockRestServiceServer.verify();
    }

    @Test
    void testDeleteNonExistingBeer() {
        mockRestServiceServer.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL + BEER_ID_PATH, testDtoId))
                .andRespond(withResourceNotFound());

        assertThrows(HttpClientErrorException.class,
                () -> beerClient.deleteBeer(testDtoId));

        mockRestServiceServer.verify();
    }

    private BeerDto getBeerDto() {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(333)
                .upc("123321")
                .build();
    }

    private BeerDtoPage getPage() {
        return new BeerDtoPage(Collections.singletonList(getBeerDto()), 1, 25, 1);
    }

    private void mockGetByIdRequest() {
        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(URL + BEER_ID_PATH, testDtoId))
                .andRespond(withSuccess(testDtoJsonString, MediaType.APPLICATION_JSON));
    }
}
