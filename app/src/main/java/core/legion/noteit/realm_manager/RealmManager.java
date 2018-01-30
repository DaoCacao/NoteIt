package core.legion.noteit.realm_manager;

import core.legion.noteit.data.Note;
import io.realm.RealmResults;

public interface RealmManager {
    void add(String title, String text);
    void update(long id, String title, String text);
    void remove(Note note);
    Note get(long id);
    boolean has(long id);
    RealmResults<Note> getAll();

}
