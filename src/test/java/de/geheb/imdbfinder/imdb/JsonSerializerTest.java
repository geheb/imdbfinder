package de.geheb.imdbfinder.imdb;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {

  private final Serializer serializer = new JsonSerializer();

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

    assertEquals("{\n" +
            "  \"movieUrl\": \"http://localhost\",\n" +
            "  \"title\": \"test\",\n" +
            "  \"datePublished\": \"2000-01-01\",\n" +
            "  \"userRating\": \"0.0\",\n" +
            "  \"genre\": [\n" +
            "    \"foo\",\n" +
            "    \"bar\"\n" +
            "  ],\n" +
            "  \"contentRating\": \"G\",\n" +
            "  \"keywords\": [\n" +
            "    \"baz\"\n" +
            "  ],\n" +
            "  \"imageUrl\": \"https://localhost\",\n" +
            "  \"duration\": \"PT0H00M\",\n" +
            "  \"description\": \"junit\"\n" +
            "}", output);
  }
}