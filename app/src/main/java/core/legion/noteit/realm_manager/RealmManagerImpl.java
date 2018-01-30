package core.legion.noteit.realm_manager;

import javax.inject.Inject;

import core.legion.noteit.data.Note;
import io.realm.Realm;
import io.realm.RealmResults;

class RealmManagerImpl implements RealmManager {

    private static final String ID = "id";

    private Realm realm;

    @Inject
    RealmManagerImpl(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void add(String title, String text) {
        realm.executeTransaction(realm -> {
            Note note = realm.createObject(Note.class, realm.where(Note.class).max(ID).intValue() + 1);
            note.setTitle(title);
            note.setText(text);
            note.setPass("");
            note.setDate(System.currentTimeMillis());
        });
    }

    @Override
    public void update(long id, String title, String text) {
        realm.executeTransaction(realm -> {
            Note note = realm.where(Note.class).equalTo(ID, id).findFirst();
            note.setTitle(title);
            note.setText(text);
            note.setDate(System.currentTimeMillis());
        });
    }

    @Override
    public void remove(Note note) {
    }

    @Override
    public Note get(long id) {
        return id >= 0
                ? realm.where(Note.class).equalTo(ID, id).findFirst()
                : null;
    }

    @Override
    public boolean has(long id) {
        return realm.where(Note.class).equalTo(ID, id).count() != 0;
    }

    @Override
    public RealmResults<Note> getAll() {
        return realm.where(Note.class).findAll();
    }
}

