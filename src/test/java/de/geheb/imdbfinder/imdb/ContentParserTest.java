package de.geheb.imdbfinder.imdb;

import de.geheb.imdbfinder.http.HttpRequestExecutor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContentParserTest {

  @Test
  void canDownloadAndParse() throws IOException {
    final var contentParser = mockContentParser();
    final var contentResult = contentParser.downloadAndParse(new URL("http://localhost/foo"));

    var expectedContentResult = new ContentResult();
    expectedContentResult.setMovieUrl(new URL("http://localhost/foo"));
    expectedContentResult.setTitle("bar");
    expectedContentResult.setImageUrl(new URL("http://localhost/baz.jpg"));
    expectedContentResult.setGenre(Arrays.asList("foo", "bar", "baz"));
    expectedContentResult.setContentRating("PG-13");
    expectedContentResult.setDescription("test");
    expectedContentResult.setDatePublished("2000-01-01");
    expectedContentResult.setKeywords(Arrays.asList("föö", "bär", "bäz"));
    expectedContentResult.setUserRating(5.0);
    expectedContentResult.setDuration("PT0H00M");

    assertEquals(expectedContentResult, contentResult);
  }

  private ContentParser mockContentParser() throws IOException {

    var mockHttpRequestExecutor = mock(HttpRequestExecutor.class);
    when(mockHttpRequestExecutor.getAsString(any())).thenReturn(
            "<html>\n" +
            "<script type=\"application/ld+json\">{\n" +
            "  \"url\": \"foo\",\n" +
            "  \"name\": \"bar\",\n" +
            "  \"image\": \"http://localhost/baz.jpg\",\n" +
            "  \"genre\": [\n" +
            "    \"foo\",\n" +
            "    \"bar\",\n" +
            "    \"baz\"\n" +
            "  ],\n" +
            "  \"contentRating\": \"PG-13\",\n" +
            "  \"description\": \"test\",\n" +
            "  \"datePublished\": \"2000-01-01\",\n" +
            "  \"keywords\": \"föö, bär, bäz\",\n" +
            "  \"aggregateRating\": {\n" +
            "    \"ratingValue\": \"5.0\"\n" +
            "  },\n" +
            "  \"duration\": \"PT0H00M\"\n" +
            "}</script>\n" +
            "</html>\n");
    return new ContentParser(mockHttpRequestExecutor);
  }
}