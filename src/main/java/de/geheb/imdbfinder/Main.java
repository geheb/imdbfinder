package de.geheb.imdbfinder;

import de.geheb.imdbfinder.app.AppCommand;
import picocli.CommandLine;

public class Main {

  public static void main(String[] args) {
    CommandLine.run(new AppCommand(args), args);
  }
}
