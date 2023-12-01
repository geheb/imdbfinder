package de.geheb.imdbfinder.http;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestExecutorTest {

  private final HttpClientBuilder httpClientBuilder = new HttpClientBuilder();
  private final HttpRequestExecutor httpRequestExecutor = new HttpRequestExecutor(httpClientBuilder);
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
  void getAsString() throws IOException {

    final int port = configureStub(200);

    var result = httpRequestExecutor.get(URI.create("http://localhost:" + port + "/foo"));

    assertEquals("bar", result);
  }

  @Test
  void getAsStringWithUnknownUrlThrowsException() throws IOException {

    final int port = configureStub(200);

    var url = URI.create("http://localhost:" + port + "/baz");

    assertThrows(IOException.class, () -> httpRequestExecutor.post(url));
  }

  @Test
  void getAsStringWithWrongStatusCodeThrowsException() throws IOException {

    final int port = configureStub(301);

    var url = URI.create("http://localhost:" + port + "/foo");

    assertThrows(IOException.class, () -> httpRequestExecutor.post(url));
  }

  private int configureStub(int statusCode) {
    final int port = mockServer.port();
    configureFor("localhost", port);
    stubFor(get(urlPathMatching("/foo"))
            .willReturn(aResponse()
                    .withStatus(statusCode)
                    .withBody("bar")));
    return port;
  }
}