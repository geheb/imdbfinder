package de.geheb.imdbfinder.imdb;

import de.geheb.imdbfinder.http.HttpRequestExecutor;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("SpellCheckingInspection")
public class UrlFinder {

  private static final String searchEngineTemplate = "https://lite.duckduckgo.com/lite/?q=";
  private final Pattern urlPattern;
  private final HttpRequestExecutor httpRequestExecutor;

  @Inject
  public UrlFinder(final HttpRequestExecutor httpRequestExecutor) {

    this.httpRequestExecutor = httpRequestExecutor;
    urlPattern = Pattern.compile("https?://([a-z]+\\.)?imdb\\.com/title/(?<id>tt\\d{7})");
  }

  @Nullable
  public URI find(@NotNull final String title) throws IOException {

    final var searchTerms = URLEncoder.encode(title + " imdb", StandardCharsets.UTF_8);
    final var searchEngineUrl = searchEngineTemplate + searchTerms;

    final var uri = URI.create(searchEngineUrl);
    final var searchResult = httpRequestExecutor.post(uri);

    final var imdbUrlMatcher = urlPattern.matcher(searchResult);
    if (imdbUrlMatcher.find()) {
      return URI.create(imdbUrlMatcher.group());
    }

    return null;
  }
}
