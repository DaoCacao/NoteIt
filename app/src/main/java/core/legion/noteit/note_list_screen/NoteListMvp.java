package core.legion.noteit.note_list_screen;

public interface NoteListMvp {

    interface View {
        void navigateToNoteScreen();
        void navigateToNoteScreen(long id);
    }

    interface Presenter extends OnNoteClickListener{
        void onFabClick();
    }

    interface OnNoteClickListener {
        void onNoteClick(long id);
    }
}
