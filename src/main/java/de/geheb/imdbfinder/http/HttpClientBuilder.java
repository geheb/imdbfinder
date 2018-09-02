package de.geheb.imdbfinder.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SpellCheckingInspection")
class HttpClientBuilder {

  @Inject
  HttpClientBuilder() {
  }

  @NotNull
  HttpURLConnection requestGet(@NotNull final URL url) throws IOException {

    final var httpConnection = (HttpURLConnection) url.openConnection();
    httpConnection.setConnectTimeout(5 * 1000);
    httpConnection.setReadTimeout(5 * 1000);
    httpConnection.setRequestMethod("GET");
    httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
    httpConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    httpConnection.setRequestProperty("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    httpConnection.connect();

    return httpConnection;
  }
}
