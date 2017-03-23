package com.example.legion.noteit;

import android.app.Application;
import android.content.Context;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class AppLoader extends Application {

    public static volatile Realm realm;
    public static volatile Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        Realm.init(getApplicationContext());

        RealmMigration migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                RealmSchema schema = realm.getSchema();

                if (oldVersion == 0) {
                    schema.get("Note")
                            .removeField("isPrivate")
                            .addField("pass", String.class);
                    oldVersion++;
                }
            }
        };

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("NotesRealm")
                .schemaVersion(0)
                .migration(migration)
                .build();

        realm = Realm.getInstance(configuration);

        Utils.getDisplaySizeAndDensity();
    }
}
