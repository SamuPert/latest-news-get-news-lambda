package com.samupert.latestnews.mapper;

import com.samupert.latestnews.dto.request.GetLatestNewsRequest;
import com.samupert.latestnews.dto.response.GetLatestNewsResponse;
import com.samupert.latestnews.service.dto.request.NewsdataServiceRequest;
import com.samupert.latestnews.service.dto.response.NewsApiResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NewsMapper {

    public NewsdataServiceRequest toServiceRequest(GetLatestNewsRequest requestDTO) {
        return new NewsdataServiceRequest(
                requestDTO.language()
        );
    }

    public GetLatestNewsResponse toResponse(NewsApiResponse latestNews) {

        List<GetLatestNewsResponse.ArticleResponse> articles = latestNews.results().stream()
                .map(newsArticle -> {

                    // Create a DateTimeFormatter with the expected pattern
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    // Parse the string into a LocalDateTime object
                    LocalDateTime localDateTime = LocalDateTime.parse(newsArticle.pubDate(), formatter);

                    // Convert the LocalDateTime to ZonedDateTime using the specified timezone
                    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(newsArticle.pubDateTZ()));

                    return new GetLatestNewsResponse.ArticleResponse(
                            newsArticle.article_id(),
                            newsArticle.title(),
                            newsArticle.description(),
                            newsArticle.link(),
                            newsArticle.image_url(),
                            newsArticle.source_icon(),
                            (newsArticle.creator() != null) ? newsArticle.creator().stream().reduce((s, s2) -> s.concat(",").concat(s2)).orElse("") : "",
                            newsArticle.language(),
                            zonedDateTime.toString()
                    );
                })
                .collect(Collectors.toList());

        return new GetLatestNewsResponse(articles, articles.size());
    }
}
