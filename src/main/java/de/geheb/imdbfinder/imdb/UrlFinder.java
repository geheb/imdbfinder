package de.geheb.imdbfinder.imdb;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
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

  private static final String searchEngineTemplate = "https://api.qwant.com/api/search/web?count=10&offset=0&q={query}&t=web&uiv=1";
  private final Pattern urlPattern;
  private final HttpRequestExecutor httpRequestExecutor;

  @Inject
  public UrlFinder(final HttpRequestExecutor httpRequestExecutor) {

    this.httpRequestExecutor = httpRequestExecutor;
    urlPattern = Pattern.compile("https?://([a-z]+\\.)?imdb\\.com/title/(?<id>tt\\d{7})");
  }

  @Nullable
  public URL find(@NotNull final String title) throws IOException {

    var searchTerms = URLEncoder.encode(title + " imdb", StandardCharsets.UTF_8);
    var searchEngineUrl = searchEngineTemplate.replace("{query}",searchTerms);

    final URL url;
    try {
      url = new URL(searchEngineUrl);

    } catch (MalformedURLException e) {
      return null;
    }
    final var searchResult = httpRequestExecutor.getAsString(url);

    final var jsonObj = Json.parse(searchResult).asObject();
    final var jsonArray = jsonObj
            .get("data").asObject()
            .get("result").asObject()
            .get("items").asArray();

    for (JsonValue value : jsonArray) {
      final var jsonUrl = value.asObject().get("url").toString();
      final var imdbUrlMatcher = urlPattern.matcher(jsonUrl);
      if (imdbUrlMatcher.find()) {
        return new URL(imdbUrlMatcher.group());
      }
    }
    return null;
  }
}
