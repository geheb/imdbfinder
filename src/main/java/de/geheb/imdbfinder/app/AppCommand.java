package de.geheb.imdbfinder.app;

import de.geheb.imdbfinder.imdb.ContentType;
import de.geheb.imdbfinder.imdb.FinderException;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import de.geheb.imdbfinder.imdb.SerializerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@SuppressWarnings("SpellCheckingInspection")
@Command(
        name = "imdbfinder",
        footer = "Copyright (c) 2023",
        description = "%nfind any movie on IMDb and extract some basic meta information%n",
        versionProvider = ManifestVersionProvider.class
)
public class AppCommand implements Callable<Integer> {

  @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help")
  private boolean hasHelp;

  @Option(names = {"-v", "--version"}, versionHelp = true, description = "display version info")
  private boolean hasVersion;

  @Option(names = {"-t", "--title"}, description = "title of the movie to find", required = true)
  private String movieTitle;

  @Option(names = {"-o", "--output"}, description = "Sets the output file. Default: stdout")
  private File outputFile;

  @Option(names = {"-c", "--contentType"},
          description = "Content type: ${COMPLETION-CANDIDATES}. Default: ${DEFAULT-VALUE}")
  private ContentType contentType = ContentType.KeyValue;

  @Override
  public Integer call() {

    final var app = DaggerAppComponent.create().getAppModule();

    try {
      final var result = app.find(movieTitle);
      final var serializer = new SerializerFactory().create(contentType);

      if (null != outputFile) {
        try (var outputStream = new FileOutputStream(outputFile)) {
          final String output = serializer.serialize(result);
          outputStream.write(output.getBytes(StandardCharsets.UTF_8));
        }
      } else {
        System.out.println(serializer.serialize(result));
      }

      return 0;

    } catch (FinderException e) {
      System.err.println(e.getMessage());
      if (null != e.getCause()) {
        System.err.println("Cause: " + e.getCause().getMessage());
      }
    } catch (Exception e) {
      System.err.println("Unexpected error:");
      e.printStackTrace(System.err);
    }
    return 1;
  }
}
