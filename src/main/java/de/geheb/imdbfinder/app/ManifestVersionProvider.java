package de.geheb.imdbfinder.app;

import picocli.CommandLine.IVersionProvider;


class ManifestVersionProvider implements IVersionProvider {

  private final String currentVersion;

  public ManifestVersionProvider() {
     currentVersion = ManifestVersionProvider.class.getPackage().getImplementationVersion();
  }

  @Override
  public String[] getVersion() {
    return new String[]{null != currentVersion ? currentVersion : "unknown"};
  }
}
