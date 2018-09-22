package de.geheb.imdbfinder.imdb;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import de.geheb.imdbfinder.http.HttpRequestExecutor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;

public class ContentParser {

  private static final String jsonObjStart = "<script type=\"application/ld+json\">";
  private static final String jsonObjEnd = "</script>";

  private final HttpRequestExecutor httpRequestExecutor;

  @Inject
  public ContentParser(final HttpRequestExecutor httpRequestExecutor) {

    this.httpRequestExecutor = httpRequestExecutor;
  }

  @NotNull
  public ContentResult downloadAndParse(@NotNull final URL url) throws IOException {

    var content = httpRequestExecutor.getAsString(url);

    var result = parseHtml(content);
    result.setMovieUrl(url);

    return result;
  }

  private ContentResult parseHtml(final String content) throws IOException {

    final var startJsonObj = content.indexOf(jsonObjStart);
    if (startJsonObj < 0)  {
      throw new IOException("Missing json obj: " + jsonObjStart);
    }

    final var endJsonObj = content.indexOf(jsonObjEnd, startJsonObj);
    if (endJsonObj < 0) {
      throw new IOException("Missing json obj: " + jsonObjEnd);
    }

    final var jsonObj = content.substring(startJsonObj + jsonObjStart.length(), endJsonObj);

    return parseJson(jsonObj);
  }

  private ContentResult parseJson(final String json) throws IOException {

    final var jsonObj = Json.parse(json).asObject();

    final var result = new ContentResult();
    result.setTitle(jsonObj.get("name").asString());

    final var contentRating = jsonObj.get("contentRating");
    result.setContentRating(null != contentRating ? contentRating.asString() : null);

    final var genre = jsonObj.get("genre");
    final var genreList = genre.isString() ? Collections.singletonList(genre.asString()) :
            genre.asArray()
                    .values()
                    .stream()
                    .map(JsonValue::asString)
                    .collect(Collectors.toList());
    result.setGenre(genreList);

    final var description = jsonObj.get("description");
    result.setDescription(null != description ? description.asString() : null);

    final var datePublished = jsonObj.get("datePublished");
    result.setDatePublished(null != datePublished ? datePublished.asString() : null);

    final var aggregateRating = jsonObj.get("aggregateRating");
    final String ratingValue = null != aggregateRating
            ? aggregateRating.asObject().get("ratingValue").asString()
            : null;
    result.setUserRating(null != ratingValue ? Double.parseDouble(ratingValue) : null);

    final var keywords = jsonObj.get("keywords");
    result.setKeywords(null != keywords ? Arrays.asList(keywords.asString().split("\\s*,\\s*")) : null);

    result.setImageUrl(new URL(jsonObj.get("image").asString()));

    final var duration = jsonObj.get("duration");
    result.setDuration(null != duration ? duration.asString() : null);

    return result;
  }
}
