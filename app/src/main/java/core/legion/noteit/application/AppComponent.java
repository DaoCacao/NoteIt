package core.legion.noteit.application;

import javax.inject.Singleton;

import core.legion.noteit.realm_manager.RealmManagerModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        AppModule.class,
        RealmManagerModule.class})
interface AppComponent extends AndroidInjector<AppLoader> {
}
