package com.samupert.latestnews;

import com.samupert.latestnews.exceptions.ApiRequestException;
import com.samupert.latestnews.service.dto.exceptions.ApiException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionHandler implements ExceptionMapper<ApiRequestException> {

    @Override
    public Response toResponse(ApiRequestException e) {
        return Response
            .status(e.httpStatusCode())
                .entity(new ApiException(e.message()))
                .build();
    }
}
