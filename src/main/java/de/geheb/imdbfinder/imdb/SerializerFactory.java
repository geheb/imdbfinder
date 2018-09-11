package de.geheb.imdbfinder.imdb;

import org.jetbrains.annotations.NotNull;

public class SerializerFactory {

  public Serializer create(@NotNull ContentType contentType) {
    if (null == contentType) throw new IllegalArgumentException("null");
    switch (contentType) {
      case KeyValue:
        return new KeyValueSerializer();
      case Json:
        return new JsonSerializer();
      default:
        throw new IllegalArgumentException(contentType.toString());
    }
  }
}
