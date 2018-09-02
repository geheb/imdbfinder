package de.geheb.imdbfinder.imdb;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class ContentResult implements Serializable {

  private static final long serialVersionUID = 1L;

  private URL movieUrl;
  private String title;
  private URL imageUrl;
  private List<String> genre;
  private String contentRating;
  private String description;
  private String datePublished;
  private Double userRating;
  private List<String> keywords;
  private String duration;

  URL getMovieUrl() {
    return movieUrl;
  }

  void setMovieUrl(URL movieUrl) {
    this.movieUrl = movieUrl;
  }

  String getTitle() {
    return title;
  }

  void setTitle(String title) {
    this.title = title;
  }

  URL getImageUrl() {
    return imageUrl;
  }

  void setImageUrl(URL imageUrl) {
    this.imageUrl = imageUrl;
  }

  List<String> getGenre() {
    return genre;
  }

  void setGenre(List<String> genre) {
    this.genre = genre;
  }

  String getContentRating() {
    return contentRating;
  }

  void setContentRating(String contentRating) {
    this.contentRating = contentRating;
  }

  String getDescription() {
    return description;
  }

  void setDescription(String description) {
    this.description = description;
  }

  String getDatePublished() {
    return datePublished;
  }

  void setDatePublished(String datePublished) {
    this.datePublished = datePublished;
  }

  Double getUserRating() {
    return userRating;
  }

  void setUserRating(Double userRating) {
    this.userRating = userRating;
  }

  List<String> getKeywords() {
    return keywords;
  }

  void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }

  String getDuration() {
    return duration;
  }

  void setDuration(String duration) {
    this.duration = duration;
  }
}
