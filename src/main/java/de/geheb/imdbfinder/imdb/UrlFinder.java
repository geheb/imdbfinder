package de.geheb.imdbfinder.imdb;

import de.geheb.imdbfinder.http.HttpRequestExecutor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("SpellCheckingInspection")
public class UrlFinder {

  private static final String googleSearchTemplate = "https://www.google.com/search?ie=utf-8&oe=utf-8&q=";
  private final Pattern urlPattern;
  private final HttpRequestExecutor httpRequestExecutor;

  @Inject
  public UrlFinder(final HttpRequestExecutor httpRequestExecutor) {

    this.httpRequestExecutor = httpRequestExecutor;
    urlPattern = Pattern.compile("https?://([a-z]+\\.)?imdb\\.com/title/(?<id>tt\\d{7})");
  }

  @Nullable
  public URL find(@NotNull final String title) throws IOException {

    final URL url;
    try {
      url = new URL(googleSearchTemplate
              + URLEncoder.encode(title + " imdb", StandardCharsets.UTF_8));

    } catch (MalformedURLException e) {
      return null;
    }
    final var googleResult = httpRequestExecutor.getAsString(url);

    final var imdbUrlMatcher = urlPattern.matcher(googleResult);
    if (!imdbUrlMatcher.find()) {
      return null;
    }

    return new URL(imdbUrlMatcher.group());
  }
}
