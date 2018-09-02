package de.geheb.imdbfinder.app;

import de.geheb.imdbfinder.imdb.ContentParser;
import de.geheb.imdbfinder.imdb.ContentResult;
import de.geheb.imdbfinder.imdb.FinderException;
import de.geheb.imdbfinder.imdb.UrlFinder;

import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;

class AppModule {

  private final UrlFinder urlFinder;
  private final ContentParser contentParser;

  @Inject
  AppModule(final UrlFinder urlFinder,
            final ContentParser contentParser) {

    this.contentParser = contentParser;
    this.urlFinder = urlFinder;
  }

  @NotNull
  ContentResult find(@NotNull final String title) throws FinderException {

    URL url;
    try {
      url = urlFinder.find(title);
    } catch (IOException e) {
      throw new FinderException("Find movie failed", e);
    }

    if (null == url) {
      throw new FinderException("No movie found");
    }

    try {
      return contentParser.downloadAndParse(url);
    } catch (IOException e) {
      throw new FinderException("IMDb information can't be extracted", e);
    }
  }
}
