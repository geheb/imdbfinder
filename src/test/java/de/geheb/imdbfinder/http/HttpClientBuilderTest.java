package de.geheb.imdbfinder.http;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

class HttpClientBuilderTest {

  private final HttpClientBuilder httpClientBuilder = new HttpClientBuilder();
  private final WireMockServer mockServer = new WireMockServer(wireMockConfig().dynamicPort());

  @BeforeEach
  void startServer() {
    mockServer.start();
  }

  @AfterEach
  void stopServer() {
    mockServer.stop();
  }

  @Test
  void canRequestPost() throws IOException, InterruptedException {

    final int port = mockServer.port();
    configureFor("localhost", port);
    stubFor(get(urlPathMatching("/foo"))
            .willReturn(aResponse()
                    .withStatus(200)
                    .withBody("")));

    try (final var client = httpClientBuilder.createDefault()) {

      final var request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:" + port + "/foo"))
        .GET()
        .build();

        var response = client.send(request, BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }
  }
}