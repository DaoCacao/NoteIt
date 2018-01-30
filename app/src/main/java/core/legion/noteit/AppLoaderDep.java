package core.legion.noteit;

import android.app.Application;
import android.content.Context;

import core.legion.noteit.data.Note;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class AppLoaderDep extends Application {

    public static volatile Realm realm;
    public static volatile Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
