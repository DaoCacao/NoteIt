package core.legion.noteit.note_screen;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class NoteModule {

    @Binds
    abstract NoteMvp.View view(NoteActivity activity);

    @Binds
    abstract NoteMvp.Presenter presenter(NotePresenter presenter);
}
