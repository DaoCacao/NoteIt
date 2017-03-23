package com.example.legion.noteit.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legion.noteit.AppLoader;
import com.example.legion.noteit.Note;
import com.example.legion.noteit.NoteAdapter;
import com.example.legion.noteit.R;
import com.example.legion.noteit.Utils;

public class MenuActivity extends AppCompatActivity {

    private NoteAdapter noteAdapter;

    private static long back_pressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout rootLayout = new FrameLayout(this);
        rootLayout.setBackgroundResource(R.drawable.back_bamboo);
        setContentView(rootLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //--> notes list
        ListView notesList = new ListView(this);
        notesList.setDivider(null);
        notesList.setDividerHeight(0);
        notesList.setBackgroundColor(Color.TRANSPARENT);
        rootLayout.addView(notesList, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //--> fab
        FloatingActionButton fab = new FloatingActionButton(this);
        FrameLayout.LayoutParams fabParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM|Gravity.END);
        fabParams.setMargins(Utils.dp(24), Utils.dp(24), Utils.dp(24), Utils.dp(24));
        fab.setImageResource(R.drawable.add);
        fab.setClickable(true);
        rootLayout.addView(fab, fabParams);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        //--> first note if empty
        if (AppLoader.realm.isEmpty()) {
            Note note = new Note(getString(R.string.txt_first_note_title), getString(com.example.legion.noteit.R.string.txt_title_text));
            AppLoader.realm.beginTransaction();
            AppLoader.realm.copyToRealm(note);
            AppLoader.realm.commitTransaction();
        }

        noteAdapter = new NoteAdapter(this, getSupportFragmentManager());
        notesList.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                addNote();
                break;
            case R.id.apply:
                saveNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(this, R.string.press_again_for_exit, Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    private void addNote() {
        startActivity(new Intent(this, NoteActivity.class));
    }

    private void saveNote() {}
}