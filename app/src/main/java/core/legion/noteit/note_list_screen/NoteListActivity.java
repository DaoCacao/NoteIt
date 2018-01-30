package core.legion.noteit.note_list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import core.legion.noteit.EditNoteBottomSheet;
import core.legion.noteit.R;
import core.legion.noteit.note_screen.NoteActivity;
import dagger.android.support.DaggerAppCompatActivity;

public class NoteListActivity extends DaggerAppCompatActivity implements NoteListMvp.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Inject NoteListMvp.Presenter presenter;
    @Inject LinearLayoutManager layoutManager;
    @Inject NoteListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> presenter.onFabClick());
    }

    @Override
    public void navigateToNoteScreen() {
        startActivity(new Intent(this, NoteActivity.class));
    }

    @Override
    public void navigateToNoteScreen(long id) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("extra_id", id);
        startActivity(intent);
    }
}
