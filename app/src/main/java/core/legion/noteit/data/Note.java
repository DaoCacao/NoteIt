package core.legion.noteit.data;

import core.legion.noteit.AppLoader;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {

    @PrimaryKey
    private long id;
    private String title, text, pass;
    private long date;

    public Note() {
    }
    public Note(String title, String text) {
        id = (AppLoader.realm.where(Note.class).max("id") == null)
                ? 0
                : AppLoader.realm.where(Note.class).max("id").longValue() + 1;
        this.title = title;
        this.text = text;
        this.pass = "";
        date = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {
        return text;
    }
    public String getPass() {
        return pass;
    }
    public long getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
