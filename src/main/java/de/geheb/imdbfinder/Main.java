package de.geheb.imdbfinder;

import de.geheb.imdbfinder.app.AppCommand;
import picocli.CommandLine;

class Main {

  public static void main(String[] args) {
    int exitCode = new CommandLine(new AppCommand()).execute(args);
    System.exit(exitCode);
  }
}
