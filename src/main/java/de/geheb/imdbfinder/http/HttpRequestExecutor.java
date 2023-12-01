package de.geheb.imdbfinder.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;

public class HttpRequestExecutor implements AutoCloseable {

  private final HttpClient httpClient;

  @Inject
  HttpRequestExecutor(final HttpClientBuilder httpClientBuilder) {

    httpClient = httpClientBuilder.createDefault();
  }

  @NotNull
  public String post(@NotNull final URI uri) throws IOException {

    try {
      final var request = HttpRequest.newBuilder()
        .uri(uri)
        .timeout(Duration.ofMinutes(1))
        .header("User-Agent", "Mozilla/5.0")
        .header("Accept-Language", "en-US,en;q=0.5")
        .POST(BodyPublishers.noBody())
        .build();

      final var asyncResponse = httpClient.sendAsync(request, BodyHandlers.ofString());
      final var response = asyncResponse.join();
      final var statusCode = asyncResponse.thenApply(HttpResponse::statusCode).get(60, TimeUnit.SECONDS);
      if (statusCode != 200) {
        final var message = "POST request with Status Code %s failed".formatted(statusCode);
        throw new IOException(message);
      }
      return response.body();
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new IOException("POST request aborted", e);
    }
  }

  @NotNull
  public String get(@NotNull final URI uri) throws IOException {

    try {
      final var request = HttpRequest.newBuilder()
        .uri(uri)
        .timeout(Duration.ofMinutes(1))
        .header("User-Agent", "Mozilla/5.0")
        .header("Accept-Language", "en-US,en;q=0.5")
        .GET()
        .build();

      final var asyncResponse = httpClient.sendAsync(request, BodyHandlers.ofString());
      final var response = asyncResponse.join();
      final var statusCode = asyncResponse.thenApply(HttpResponse::statusCode).get(60, TimeUnit.SECONDS);
      if (statusCode != 200) {
        final var message = "GET request with Status Code %s failed".formatted(statusCode);
        throw new IOException(message);
      }
      return response.body();
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new IOException("GET request failed", e);
    }
  }

  @Override
  public void close() throws Exception {
    httpClient.close();
  }
}
