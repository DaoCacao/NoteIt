package core.legion.noteit.note_list_screen;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import core.legion.noteit.realm_manager.RealmManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NoteListModule {

    @Binds
    abstract NoteListMvp.View view(NoteListActivity activity);

    @Binds
    abstract NoteListMvp.Presenter presenter(NoteListPresenter presenter);

    @Binds
    abstract NoteListMvp.OnNoteClickListener onNoteClickListener(NoteListPresenter presenter);

    @Provides
    static LinearLayoutManager layoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    static NoteListAdapter adapter(RealmManager realmManager, NoteListMvp.OnNoteClickListener onNoteClickListener) {
        return new NoteListAdapter(realmManager.getAll(), onNoteClickListener);
    }
}
