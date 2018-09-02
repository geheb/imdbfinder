package de.geheb.imdbfinder.app;

import de.geheb.imdbfinder.Main;
import picocli.CommandLine.IVersionProvider;


class ManifestVersionProvider implements IVersionProvider {

  @Override
  public String[] getVersion() {

    final String version = Main.class.getPackage().getImplementationVersion();
    return new String[]{null != version ? version : "unknown"};
  }
}
