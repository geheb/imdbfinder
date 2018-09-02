package de.geheb.imdbfinder.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
              4096)) {
        String line;
        while ((line = reader.readLine()) != null) {
          stringBuilder.append(line).append("\n");
        }
      }

      return stringBuilder.toString();

    } finally {
      httpConnection.disconnect();
    }
  }
}
