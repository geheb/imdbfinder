package de.geheb.imdbfinder.imdb;

import de.geheb.imdbfinder.util.ListComparator;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Objects;

public class ContentResult implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final char COMMA = ',';
  private static final String SEMICOLON = ";";

  private URI movieUrl;
  private String title;
  private URI imageUrl;
  private List<String> genre;
  private String contentRating;
  private String description;
  private String datePublished;
  private Double userRating;
  private List<String> keywords;
  private String duration;

  URI getMovieUrl() {
    return movieUrl;
  }

  void setMovieUrl(URI movieUrl) {
    this.movieUrl = movieUrl;
  }

  String getTitle() {
    return title;
  }

  void setTitle(String title) {
    this.title = title;
  }

  URI getImageUrl() {
    return imageUrl;
  }

  void setImageUrl(URI imageUrl) {
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

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof ContentResult)) return false;
    ContentResult otherObj = (ContentResult) other;

    if (!Objects.equals(getMovieUrl(), otherObj.getMovieUrl())) return false;
    if (!Objects.equals(getTitle(), otherObj.getTitle())) return false;
    if (!Objects.equals(getImageUrl(), otherObj.getImageUrl())) return false;
    if (!ListComparator.equals(getGenre(), otherObj.getGenre())) return false;
    if (!Objects.equals(getContentRating(), otherObj.getContentRating())) return false;
    if (!Objects.equals(getDescription(), otherObj.getDescription())) return false;
    if (!Objects.equals(getDatePublished(), otherObj.getDatePublished())) return false;
    if (!Objects.equals(getUserRating(), otherObj.getUserRating())) return false;
    if (!ListComparator.equals(getKeywords(), otherObj.getKeywords())) return false;
    return Objects.equals(getDuration(), otherObj.getDuration());
  }

  @Override
  public String toString() {
    return "movieUrl=" + movieUrl +
      COMMA + "title=" + title +
      COMMA + "imageUrl=" + imageUrl +
      COMMA + "genre=" + (null == genre ? null : String.join(SEMICOLON, genre)) +
      COMMA + "contentRating=" + contentRating +
      COMMA + "description=" + description +
      COMMA + "datePublished=" + datePublished +
      COMMA + "userRating=" + userRating +
      COMMA + "keywords=" + (null == keywords ? null : String.join(SEMICOLON, keywords)) +
      COMMA + "duration=" + duration;
  }
}