package de.geheb.imdbfinder.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.CharBuffer;
import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;

public class HttpRequestExecutor {

  private final HttpClientBuilder httpClientBuilder;

  @Inject
  HttpRequestExecutor(final HttpClientBuilder httpClientBuilder) {

    this.httpClientBuilder = httpClientBuilder;
  }

  @NotNull
  public String getAsString(@NotNull final URL url) throws IOException {

    var httpConnection = httpClientBuilder.requestGet(url);

    try {

      if (HttpURLConnection.HTTP_OK != httpConnection.getResponseCode()) {
        throw new IOException("Unexpected response: " + httpConnection.getResponseCode());
      }

      var stringBuilder = new StringBuilder();

      try (var reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()),
              65535)) {
        var charBuffer = CharBuffer.allocate(4096);
        while (reader.read(charBuffer) > 0) {
          stringBuilder.append(charBuffer.flip());
          charBuffer.clear();
        }
      }
      return stringBuilder.toString();

    } finally {
      httpConnection.disconnect();
    }
  }
}
