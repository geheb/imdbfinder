package de.geheb.imdbfinder.app;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component
interface AppComponent {

  AppModule getAppModule();
}
