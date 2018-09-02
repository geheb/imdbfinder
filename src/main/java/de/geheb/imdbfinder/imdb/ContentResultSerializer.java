package de.geheb.imdbfinder.imdb;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class ContentResultSerializer {

  private static final String COMMA = ", ";
  private static final String MOVIE_URL = "movieUrl";
  private static final String TITLE = "title";
  private static final String DATE_PUBLISHED = "datePublished";
  private static final String USER_RATING = "userRating";
  private static final String GENRE = "genre";
  private static final String CONTENT_RATING = "contentRating";
  private static final String KEYWORDS = "keywords";
  private static final String IMAGE_URL = "imageUrl";
  private static final String DURATION = "duration";
  private static final String DESCRIPTION = "description";

  private final ContentType contentType;

  public ContentResultSerializer(@NotNull final ContentType contentType) {
    this.contentType = contentType;
  }

  public void serialize(@NotNull final ContentResult result,
                        @NotNull final OutputStream out) throws IOException {

    final var stringBuilder = new StringBuilder();
    final var lineSeparator = System.lineSeparator();

    if (ContentType.KeyValue == contentType) {

      append(stringBuilder, MOVIE_URL, result.getMovieUrl());
      append(stringBuilder, TITLE, result.getTitle());
      append(stringBuilder, DATE_PUBLISHED, result.getDatePublished());
      append(stringBuilder, USER_RATING, result.getUserRating());
      append(stringBuilder, GENRE,
              null != result.getGenre() ? String.join(COMMA, result.getGenre()) : null);
      append(stringBuilder, CONTENT_RATING, result.getContentRating());
      append(stringBuilder, KEYWORDS,
              null != result.getKeywords() ? String.join(COMMA, result.getKeywords()) : null);
      append(stringBuilder, IMAGE_URL, result.getImageUrl());
      append(stringBuilder, DURATION, result.getDuration());
      append(stringBuilder, DESCRIPTION, result.getDescription());

    } else if (ContentType.Json == contentType) {

      final var jsonObject = Json.object();
      add(jsonObject, MOVIE_URL, result.getMovieUrl());
      add(jsonObject, TITLE, result.getTitle());
      add(jsonObject, DATE_PUBLISHED, result.getDatePublished());
      add(jsonObject, USER_RATING, result.getUserRating());
      add(jsonObject, GENRE, result.getGenre());
      add(jsonObject, CONTENT_RATING, result.getContentRating());
      add(jsonObject, KEYWORDS, result.getKeywords());
      add(jsonObject, IMAGE_URL, result.getImageUrl());
      add(jsonObject, DURATION, result.getDuration());
      add(jsonObject, DESCRIPTION, result.getDescription());

      stringBuilder.append(jsonObject.toString(WriterConfig.PRETTY_PRINT));
      stringBuilder.append(lineSeparator);

    } else {
      throw new UnsupportedOperationException();
    }

    out.write(stringBuilder.toString().getBytes());
  }

  private void add(final JsonObject jsonObject, final String key, final String value) {
    jsonObject.add(key, null != value ? Json.value(value) : Json.NULL);
  }

  private void add(final JsonObject jsonObject, final String key, final URL value) {
    jsonObject.add(key, null != value ? Json.value(value.toString()) : Json.NULL);
  }

  private void add(final JsonObject jsonObject, final String key, final Double value) {
    jsonObject.add(key, null != value ? Json.value(value.toString()) : Json.NULL);
  }

  private void add(final JsonObject jsonObject, final String key, final List<String> value) {
    jsonObject.add(key, null != value ? Json.array(value.toArray(new String[0])) : Json.NULL);
  }

  private void append(final StringBuilder stringBuilder,
                      final String key,
                      final Object value) {
    stringBuilder.append(key).append("=");
    if (null != value) {
      stringBuilder.append(value);
    }
    stringBuilder.append(System.lineSeparator());
  }
}
