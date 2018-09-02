package de.geheb.imdbfinder.imdb;

public class FinderException extends Exception {

  public FinderException(final String message) {
    super(message);
  }

  public FinderException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
