package de.geheb.imdbfinder;

import de.geheb.imdbfinder.app.AppCommand;
import picocli.CommandLine;

class Main {

  public static void main(String[] args) {
    CommandLine.run(new AppCommand(args), args);
  }
}
