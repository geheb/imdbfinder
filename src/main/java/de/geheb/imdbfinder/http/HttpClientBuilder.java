package de.geheb.imdbfinder.http;

import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.time.Duration;

import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SpellCheckingInspection")
class HttpClientBuilder {

  @Inject
  HttpClientBuilder() {
  }

  @NotNull
  HttpClient createDefault() {

    var client = HttpClient.newBuilder()
      .version(Version.HTTP_1_1)
      .followRedirects(Redirect.NORMAL)
      .connectTimeout(Duration.ofSeconds(60))
      .proxy(ProxySelector.getDefault())
      .build();

    return client;
  }
}
