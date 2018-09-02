package de.geheb.imdbfinder.app;

import de.geheb.imdbfinder.imdb.ContentResultSerializer;
import de.geheb.imdbfinder.imdb.ContentType;
import de.geheb.imdbfinder.imdb.FinderException;

import java.io.File;
import java.io.FileOutputStream;

import org.jetbrains.annotations.NotNull;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@SuppressWarnings("SpellCheckingInspection")
@Command(
        name = "imdbfinder",
        footer = "Copyright (c) 2018",
        description = "%nfind and print movie information from IMDb%n",
        versionProvider = ManifestVersionProvider.class
)
public class AppCommand implements Runnable {

  private final boolean hasArgs;

  @SuppressWarnings("unused")
  @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help")
  private boolean hasHelp;

  @SuppressWarnings("unused")
  @Option(names = {"-v", "--version"}, versionHelp = true, description = "display version info")
  private boolean hasVersion;

  @SuppressWarnings("unused")
  @Option(names = {"-t", "--title"}, description = "title of the movie to find", required = true)
  private String movieTitle;

  @SuppressWarnings("unused")
  @Option(names = {"-o", "--output"}, description = "Sets the output file. Default: stdout")
  private File outputFile;

  @SuppressWarnings("CanBeFinal")
  @Option(names = {"-c", "--contentType"},
          description = "Content type: ${COMPLETION-CANDIDATES}. Default: ${DEFAULT-VALUE}")
  private ContentType contentType = ContentType.KeyValue;

  public AppCommand(@NotNull final String[] args) {
    hasArgs = args.length > 0;
  }

  @Override
  public void run() {

    if (!hasArgs) {
      CommandLine.usage(this, System.out);
      return;
    }

    final var app = DaggerAppComponent.create().getAppModule();

    try {
      final var result = app.find(movieTitle);
      final var serializer = new ContentResultSerializer(contentType);

      if (null != outputFile) {
        try (var outputStream = new FileOutputStream(outputFile)) {
          serializer.serialize(result, outputStream);
        }
      } else {
        serializer.serialize(result, System.out);
      }
      System.exit(0);

    } catch (FinderException e) {
      System.err.println(e.getMessage());
      if (null != e.getCause()) {
        System.err.println("Cause: " + e.getCause().getMessage());
      }
      System.exit(1);

    } catch (Exception e) {
      System.err.println("Unexpected error:");
      e.printStackTrace(System.err);
      System.exit(-1);
    }
  }
}
