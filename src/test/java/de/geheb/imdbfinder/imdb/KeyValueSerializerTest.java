package de.geheb.imdbfinder.imdb;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyValueSerializerTest {

  private final Serializer serializer = new KeyValueSerializer();

  @Test
  void canSerialize() throws MalformedURLException {
    final var result = new ContentResult();
    result.setMovieUrl(URI.create("http://localhost"));
    result.setTitle("test");
    result.setDatePublished("2000-01-01");
    result.setUserRating(0.0);
    result.setGenre(Arrays.asList("foo", "bar"));
    result.setContentRating("G");
    result.setKeywords(Collections.singletonList("baz"));
    result.setImageUrl(URI.create("https://localhost"));
    result.setDuration("PT0H00M");
    result.setDescription("junit");

    final var output = serializer.serialize(result);
    final var lineSeparator = System.lineSeparator();

    assertEquals("movieUrl=http://localhost" + lineSeparator +
            "title=test" + lineSeparator +
            "datePublished=2000-01-01" + lineSeparator +
            "userRating=0.0" + lineSeparator +
            "genre=foo, bar" + lineSeparator +
            "contentRating=G" + lineSeparator +
            "keywords=baz" + lineSeparator +
            "imageUrl=https://localhost" + lineSeparator +
            "duration=PT0H00M" + lineSeparator +
            "description=junit" + lineSeparator, output);

  }
}