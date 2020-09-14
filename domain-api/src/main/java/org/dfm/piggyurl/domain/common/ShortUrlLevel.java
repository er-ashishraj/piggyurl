package org.dfm.piggyurl.domain.common;

public enum ShortUrlLevel {
  NONE("NONE"), USER("USER"), TRIBE("TRIBE"), FEATURE_TEAM("FEATURE_TEAM");
  private String shortUrlLevel;

  ShortUrlLevel(final String shortUrlLevel) {
    this.shortUrlLevel = shortUrlLevel;
  }

  public String getShortUrlLevel() {
    return this.shortUrlLevel;
  }
}
