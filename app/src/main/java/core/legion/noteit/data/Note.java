package core.legion.noteit.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private String text;
    private String pass;
    private long date;

    public Note() {
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

    public void setId(long id) {
        this.id = id;
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
