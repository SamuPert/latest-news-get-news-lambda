package com.samupert.latestnews.service.dto.response;

import java.util.List;

public record NewsApiResponse(
        String status,
        Double totalResults,
        String nextPage,
        List<NewsArticle> results
) {
}