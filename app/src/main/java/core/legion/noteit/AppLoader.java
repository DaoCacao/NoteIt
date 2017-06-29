package core.legion.noteit;

import android.app.Application;
import android.content.Context;

import core.legion.noteit.data.Note;
import io.realm.FieldAttribute;
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

        realm = Realm.getInstance(initRealmConfiguration());

        addFirstNote();

        Utils.getDisplaySizeAndDensity();
    }

    private void addFirstNote() {
        //--> first note if empty
        if (AppLoader.realm.isEmpty()) {
            Note note = new Note(getString(R.string.txt_first_note_title), getString(R.string.txt_title_text));
            AppLoader.realm.executeTransaction(realm -> realm.copyToRealm(note));
        }
    }

    private RealmConfiguration initRealmConfiguration() {
        RealmMigration migration = (realm1, oldVersion, newVersion) -> {
            RealmSchema schema = realm1.getSchema();

//            if (oldVersion == 0) {
//                schema.get("Note")
//                        .addField("pass", String.class);
//                oldVersion++;
//            } oldVersion++;
        };

        return new RealmConfiguration.Builder()
                .name("NotesRealm")
                .schemaVersion(0)
                .migration(migration)
                .build();
    }
}
