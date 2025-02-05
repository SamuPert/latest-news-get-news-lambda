package com.samupert.latestnews;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samupert.latestnews.exceptions.ApiRequestException;
import com.samupert.latestnews.service.NewsdataService;
import com.samupert.latestnews.service.dto.request.NewsdataServiceRequest;
import com.samupert.latestnews.service.dto.response.NewsApiResponse;
import io.quarkus.logging.Log;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@QuarkusTest
class GetLatestNewsResourceTest {

    @InjectMock
    @RestClient
    NewsdataService newsService;

    @Test
    void testDefaultResponseWhenNoParametersProvided() throws IOException {

        Log.debug("Fetching json api mock: \"src/test/resources/mock_news_generic_response.json\"");

        // Mock call returning german news
        NewsApiResponse newsApiResponse = getMockedApiResponse("src/test/resources/mock_news_generic_response.json");
        when(newsService.getLatest(new NewsdataServiceRequest(null)))
                .thenReturn(newsApiResponse);


        ValidatableResponse validatableResponse = given()
                .when().get("/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("count", equalTo(10))
                .body("articles.size()", equalTo(10))
                .body("articles[0].id", notNullValue())
                .body("articles[0].title", notNullValue())
                .body("articles[0].description", notNullValue())
                .body("articles[0].pubTimestamp", notNullValue());
    }

    @Test
    void testResponseForValidLangCode() {

        // Mock call returning german news
        NewsApiResponse newsApiResponse = getMockedApiResponse("src/test/resources/mock_news_german_response.json");
        when(newsService.getLatest(new NewsdataServiceRequest("de")))
                .thenReturn(newsApiResponse);

        given()
                .queryParam("lang", "de")
                .when().get("/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("count", equalTo(10))
                .body("articles.size()", equalTo(10))
                .body("articles[0].language", equalTo("german"))
                .body("articles[0].id", notNullValue())
                .body("articles[0].title", notNullValue())
                .body("articles[0].description", notNullValue())
                .body("articles[0].pubTimestamp", notNullValue());
    }

    @Test
    void testErrorResponseForInvalidLangCode() {

        // Mock call returning german news
        when(newsService.getLatest(new NewsdataServiceRequest("non-existent")))
                .thenThrow(new ApiRequestException(422, "422 Unprocessable Entity"));


        given()
                .queryParam("lang", "non-existent")
                .when().get("/")
                .then()
                .contentType("application/json")
                .statusCode(422)
                .body("message", equalTo("422 Unprocessable Entity"));
    }

    private NewsApiResponse getMockedApiResponse(String jsonApiFilePath) {
        try {
            String mockedApiResponse = new String(Files.readAllBytes(Paths.get(jsonApiFilePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(mockedApiResponse, NewsApiResponse.class);
        } catch (IOException e) {
            // Error in parsing the json test file
            throw new RuntimeException(e);
        }
    }
}