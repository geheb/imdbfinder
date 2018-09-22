package de.geheb.imdbfinder.http;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import java.io.IOException;
import java.net.URL;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

class HttpClientBuilderTest {

  private final HttpClientBuilder httpClientBuilder = new HttpClientBuilder();

  @Rule
  private final WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

  @BeforeEach
  void setup() {
    wireMockRule.start();
  }

  @Test
  void canRequestGet() throws IOException {

    final int port = wireMockRule.port();
    configureFor("localhost", port);
    stubFor(get(urlPathMatching("/foo"))
            .willReturn(aResponse()
                    .withStatus(200)
                    .withBody("")));

    final var connection = httpClientBuilder.requestGet(new URL("http://localhost:" + port + "/foo"));

    try {
      assertEquals(200, connection.getResponseCode());
    } finally {
      connection.disconnect();
    }
  }
}