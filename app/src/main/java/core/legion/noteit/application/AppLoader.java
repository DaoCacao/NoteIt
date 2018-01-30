package core.legion.noteit.application;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;

public class AppLoader extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
