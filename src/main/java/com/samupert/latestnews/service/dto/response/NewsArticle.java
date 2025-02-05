package com.samupert.latestnews.service.dto.response;

import java.util.Set;

public record NewsArticle(
        String article_id,
        String title,
        String link,
        Set<String> keywords,
        Set<String> creator,
        String video_url,
        String description,
        String content,
        String pubDate,
        String pubDateTZ,
        String image_url,
        String source_id,
        Double source_priority,
        String source_name,
        String source_url,
        String source_icon,
        String language,
        Set<String> country,
        Set<String> category,
        String ai_tag,
        String sentiment,
        String sentiment_stats,
        String ai_region,
        String ai_org,
        Boolean duplicate
) { }