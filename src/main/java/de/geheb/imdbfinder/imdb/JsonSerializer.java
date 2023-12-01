package de.geheb.imdbfinder.imdb;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;

import java.net.URI;
import java.util.List;

import org.jetbrains.annotations.NotNull;

class JsonSerializer implements Serializer {

  @Override
  public String serialize(@NotNull final ContentResult result) {
    final var jsonObject = Json.object();

    add(jsonObject, "movieUrl", result.getMovieUrl());
    add(jsonObject, "title", result.getTitle());
    add(jsonObject, "datePublished", result.getDatePublished());
    addUserRating(jsonObject, result.getUserRating());
    add(jsonObject, "genre", result.getGenre());
    add(jsonObject, "contentRating", result.getContentRating());
    add(jsonObject, "keywords", result.getKeywords());
    add(jsonObject, "imageUrl", result.getImageUrl());
    add(jsonObject, "duration", result.getDuration());
    add(jsonObject, "description", result.getDescription());

    return jsonObject.toString(WriterConfig.PRETTY_PRINT);
  }

  private void add(final JsonObject jsonObject, final String key, final String value) {
    jsonObject.add(key, null != value ? Json.value(value) : Json.NULL);
  }

  private void add(final JsonObject jsonObject, final String key, final URI value) {
    jsonObject.add(key, null != value ? Json.value(value.toString()) : Json.NULL);
  }

  private void addUserRating(final JsonObject jsonObject, final Double value) {
    jsonObject.add("userRating", null != value ? Json.value(value.toString()) : Json.NULL);
  }

  private void add(final JsonObject jsonObject, final String key, final List<String> value) {
    jsonObject.add(key, null != value ? Json.array(value.toArray(new String[0])) : Json.NULL);
  }
}
