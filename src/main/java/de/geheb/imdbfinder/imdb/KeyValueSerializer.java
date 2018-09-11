package de.geheb.imdbfinder.imdb;

import org.jetbrains.annotations.NotNull;

class KeyValueSerializer implements Serializer {

  private static final String COMMA = ", ";

  @Override
  public String serialize(@NotNull final ContentResult result) {

    final var stringBuilder = new StringBuilder();

    append(stringBuilder, "movieUrl", result.getMovieUrl());
    append(stringBuilder, "title", result.getTitle());
    append(stringBuilder, "datePublished", result.getDatePublished());
    append(stringBuilder, "userRating", result.getUserRating());
    append(stringBuilder, "genre",
            null != result.getGenre() ? String.join(COMMA, result.getGenre()) : null);
    append(stringBuilder, "contentRating", result.getContentRating());
    append(stringBuilder, "keywords",
            null != result.getKeywords() ? String.join(COMMA, result.getKeywords()) : null);
    append(stringBuilder, "imageUrl", result.getImageUrl());
    append(stringBuilder, "duration", result.getDuration());
    append(stringBuilder, "description", result.getDescription());

    return stringBuilder.toString();
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
