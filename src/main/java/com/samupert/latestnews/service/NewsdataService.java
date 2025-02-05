package com.samupert.latestnews.service;

import com.samupert.latestnews.exceptions.ApiRequestException;
import com.samupert.latestnews.service.dto.response.NewsApiResponse;
import com.samupert.latestnews.service.dto.request.NewsdataServiceRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ClientQueryParam(name = "apikey", value = "${services.rest.apikey}")
@RegisterRestClient
public interface NewsdataService {

    @GET
    @Path("/latest")
    NewsApiResponse getLatest(@BeanParam NewsdataServiceRequest request);


    @ClientExceptionMapper
    static RuntimeException toException(Response response) {

        int httpStatus = response.getStatus();

        if(httpStatus >= 400 && httpStatus < 600) {
            return new ApiRequestException(httpStatus, HttpResponseStatus.valueOf(httpStatus).toString());
        }

        return null;
    }
}