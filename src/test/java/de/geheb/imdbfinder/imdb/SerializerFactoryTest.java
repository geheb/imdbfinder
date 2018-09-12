package de.geheb.imdbfinder.imdb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SerializerFactoryTest {

  private final SerializerFactory serializerFactory = new SerializerFactory();

  @Test
  void canCreateKeyValue() {
    final var serializer = serializerFactory.create(ContentType.KeyValue);
    assertTrue(serializer instanceof KeyValueSerializer);
  }

  @Test
  void canCreateJson() {
    final var serializer = serializerFactory.create(ContentType.Json);
    assertTrue(serializer instanceof JsonSerializer);
  }

  @Test
  void cannotCreateNullSerializer() {
    //noinspection ConstantConditions
    assertThrows(IllegalArgumentException.class, () -> serializerFactory.create(null));
  }
}