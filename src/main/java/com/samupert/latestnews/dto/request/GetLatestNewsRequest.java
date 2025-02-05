package com.samupert.latestnews.dto.request;

import jakarta.ws.rs.QueryParam;

public record GetLatestNewsRequest(
        @QueryParam("lang") String language
) {
}
