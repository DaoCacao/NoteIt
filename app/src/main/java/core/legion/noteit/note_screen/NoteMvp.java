package core.legion.noteit.note_screen;

public interface NoteMvp {

    interface View {
        void showTitle(String title);
        void showText(String text);

        void closeView();

        void showQuitDialog();
        void showPasswordDialog();
    }

    interface Presenter {
        void onViewInit(long id);

        void onTitleChange(String title);
        void onTextChange(String text);

        void onSaveClick();
        void onPasswordClick();

        void onBackClick();

        void onQuitConfirmClick();
        void onQuitDenyClick();

    }
}
