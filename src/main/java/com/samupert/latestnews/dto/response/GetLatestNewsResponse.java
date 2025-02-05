package com.samupert.latestnews.dto.response;

import java.util.List;

public record GetLatestNewsResponse(
        List<ArticleResponse> articles,
        Integer count
) {
    public record ArticleResponse(
            String id,
            String title,
            String description,
            String link,
            String imageUrl,
            String iconUrl,
            String creator,
            String language,
            String pubTimestamp
    ) {
    }
}
