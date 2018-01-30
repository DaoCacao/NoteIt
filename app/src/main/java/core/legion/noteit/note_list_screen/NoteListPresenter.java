package core.legion.noteit.note_list_screen;

import javax.inject.Inject;

@NoteListScope
public class NoteListPresenter implements NoteListMvp.Presenter {

    private NoteListMvp.View view;

    @Inject
    NoteListPresenter(NoteListMvp.View view) {
        this.view = view;
    }

    @Override
    public void onFabClick() {
        view.navigateToNoteScreen();
    }

    @Override
    public void onNoteClick(long id) {
        view.navigateToNoteScreen(id);
    }
}
