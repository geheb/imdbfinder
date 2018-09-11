package de.geheb.imdbfinder.imdb;

import org.jetbrains.annotations.NotNull;

public interface Serializer {
  String serialize(@NotNull final ContentResult input);
}
