package de.geheb.imdbfinder.http;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import java.io.IOException;
import java.net.URL;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestExecutorTest {

  private final HttpClientBuilder httpClientBuilder = new HttpClientBuilder();
  private final HttpRequestExecutor httpRequestExecutor = new HttpRequestExecutor(httpClientBuilder);

  @Rule
  private final WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

  @BeforeEach
  void setup() {
    wireMockRule.start();
  }

  @Test
  void getAsString() throws IOException {

    final int port = configureStub(200);

    var result = httpRequestExecutor.getAsString(new URL("http://localhost:" + port + "/foo"));

    assertEquals("bar", result);
  }

  @Test
  void getAsStringWithUnknownUrlThrowsException() throws IOException {

    final int port = configureStub(200);

    var url = new URL("http://localhost:" + port + "/baz");

    assertThrows(IOException.class, () -> httpRequestExecutor.getAsString(url));
  }

  @Test
  void getAsStringWithWrongStatusCodeThrowsException() throws IOException {

    final int port = configureStub(301);

    var url = new URL("http://localhost:" + port + "/foo");

    assertThrows(IOException.class, () -> httpRequestExecutor.getAsString(url));
  }

  private int configureStub(int statusCode) {
    final int port = wireMockRule.port();
    configureFor("localhost", port);
    stubFor(get(urlPathMatching("/foo"))
            .willReturn(aResponse()
                    .withStatus(statusCode)
                    .withBody("bar")));
    return port;
  }
}