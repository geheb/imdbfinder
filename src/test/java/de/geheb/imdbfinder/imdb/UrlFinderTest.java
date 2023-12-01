package de.geheb.imdbfinder.imdb;

import de.geheb.imdbfinder.http.HttpRequestExecutor;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UrlFinderTest {

  @Test
  void canFind() throws IOException {

    final var imdbUrl = "https://www.imdb.com/title/tt0120903";
    final var content = String.format("{\"data\":{ \"result\": { \"items\": [{\"url\":\"%s\"}]}}}",
      imdbUrl);

    final var url = mockUrlFinder(content).find("x-men");

    assertEquals(imdbUrl, Objects.requireNonNull(url).toString());
  }

  @Test
  void canNotFind() throws IOException {

    final var content = "{\"data\":{ \"result\": { \"items\": []}}}";

    final var url = mockUrlFinder(content).find("bar");

    assertNull(url);
  }

  private UrlFinder mockUrlFinder(final String content) throws IOException {

    var mockHttpRequestExecutor = mock(HttpRequestExecutor.class);
    when(mockHttpRequestExecutor.post(any())).thenReturn(content);
    return new UrlFinder(mockHttpRequestExecutor);
  }
}