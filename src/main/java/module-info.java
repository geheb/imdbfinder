module de.geheb.imdbfinder {
  requires javax.inject;
  requires dagger;
  requires info.picocli;
  requires annotations;
  requires minimal.json;

  exports de.geheb.imdbfinder.app to info.picocli;
  opens de.geheb.imdbfinder.app to info.picocli;
}
