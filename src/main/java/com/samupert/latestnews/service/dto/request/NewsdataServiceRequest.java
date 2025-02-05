package com.samupert.latestnews.service.dto.request;

import org.jboss.resteasy.reactive.RestQuery;

public record NewsdataServiceRequest(
        @RestQuery("language") String language
) {
}
