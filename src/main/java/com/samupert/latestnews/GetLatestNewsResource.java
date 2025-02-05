package com.samupert.latestnews;

import com.samupert.latestnews.dto.request.GetLatestNewsRequest;
import com.samupert.latestnews.dto.response.GetLatestNewsResponse;
import com.samupert.latestnews.mapper.NewsMapper;
import com.samupert.latestnews.service.dto.request.NewsdataServiceRequest;
import com.samupert.latestnews.service.dto.response.NewsApiResponse;
import com.samupert.latestnews.service.NewsdataService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class GetLatestNewsResource {

    @RestClient
    NewsdataService newsdataService;

    @Inject
    NewsMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestNews(@BeanParam GetLatestNewsRequest requestDTO) {

        NewsdataServiceRequest serviceRequest = mapper.toServiceRequest(requestDTO);

        NewsApiResponse latestResponse = newsdataService.getLatest(serviceRequest);

        GetLatestNewsResponse response = mapper.toResponse(latestResponse);

        return Response.ok(response).build();
    }
}
