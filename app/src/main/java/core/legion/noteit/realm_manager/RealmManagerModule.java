package core.legion.noteit.realm_manager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

@Module
public abstract class RealmManagerModule {

    private static final String REALM_FILENAME = "NotesRealm";

    @Binds
    abstract RealmManager realmManager(RealmManagerImpl realmManager);

    @Provides
    static Realm realm(RealmConfiguration configuration) {
        return Realm.getInstance(configuration);
    }

    @Provides
    static RealmConfiguration configuration(RealmMigration migration) {
        return new RealmConfiguration.Builder()
                .name(REALM_FILENAME)
                .schemaVersion(0)
                .migration(migration)
                .build();
    }

    @Provides
    static RealmMigration migration() {
        return (realm1, oldVersion, newVersion) -> {
            RealmSchema schema = realm1.getSchema();

//            if (oldVersion == 0) {
//                schema.get("Note")
//                        .addField("pass", String.class);
//                oldVersion++;
//            } oldVersion++;
        };
    }
}
