package core.legion.noteit.application;

import core.legion.noteit.note_list_screen.NoteListActivity;
import core.legion.noteit.note_list_screen.NoteListModule;
import core.legion.noteit.note_screen.NoteActivity;
import core.legion.noteit.note_screen.NoteModule;
import core.legion.noteit.note_list_screen.NoteListScope;
import core.legion.noteit.note_screen.NoteScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ActivityBindingModule {

    @NoteListScope
    @ContributesAndroidInjector(modules = NoteListModule.class)
    NoteListActivity noteListActivity();

    @NoteScope
    @ContributesAndroidInjector(modules = NoteModule.class)
    NoteActivity noteActivity();
}
