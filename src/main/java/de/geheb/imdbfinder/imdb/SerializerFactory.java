package de.geheb.imdbfinder.imdb;

import org.jetbrains.annotations.NotNull;

public class SerializerFactory {

  public Serializer create(@NotNull ContentType contentType) {
    //noinspection ConstantConditions
    if (null == contentType) throw new IllegalArgumentException("missing contentType");
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
