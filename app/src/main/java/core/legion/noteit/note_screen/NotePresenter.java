package core.legion.noteit.note_screen;

import javax.inject.Inject;

import core.legion.noteit.data.Note;
import core.legion.noteit.realm_manager.RealmManager;

public class NotePresenter implements NoteMvp.Presenter {

    private NoteMvp.View view;
    private RealmManager realmManager;

    private long id;
    private String title;
    private String text;

    private boolean isChanged = false;

    @Inject
    public NotePresenter(NoteMvp.View view, RealmManager realmManager) {
        this.view = view;
        this.realmManager = realmManager;
    }

    @Override
    public void onViewInit(long id) {
        this.id = id;

        if (realmManager.has(id)) {
            Note note = realmManager.get(id);
            title = note.getTitle();
            text = note.getText();

            view.showTitle(title);
            view.showText(text);
        }
    }

    @Override
    public void onTitleChange(String title) {
        isChanged = true;
        this.title = title;
    }

    @Override
    public void onTextChange(String text) {
        isChanged = true;
        this.text = text;
    }

    @Override
    public void onSaveClick() {
        if (isChanged) {
            saveChanges();
        }
        view.closeView();
    }

    @Override
    public void onPasswordClick() {
        view.showPasswordDialog();
    }

    @Override
    public void onBackClick() {
        if (isChanged) {
            view.showQuitDialog();
        } else {
            view.closeView();
        }
    }

    @Override
    public void onQuitConfirmClick() {
        saveChanges();
        view.closeView();
    }

    @Override
    public void onQuitDenyClick() {
        view.closeView();
    }

    private void saveChanges() {
        if (realmManager.has(id)) {
            realmManager.update(id, title, text);
        } else {
            realmManager.add(title, text);
        }
    }
}
