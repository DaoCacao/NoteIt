package core.legion.noteit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import core.legion.noteit.NoteAdapter;


import core.legion.noteit.R;
import core.legion.noteit.Utils;

public class MenuActivity extends AppCompatActivity {

    private FrameLayout containerLayout;
    private Toolbar toolbar;
    private ListView list;
    private FloatingActionButton fab;

    private NoteAdapter noteAdapter;

    private static long back_pressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        containerLayout = (FrameLayout) findViewById(R.id.container_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        list = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        setSupportActionBar(toolbar);


        noteAdapter = new NoteAdapter(this, getSupportFragmentManager());
        list.setAdapter(noteAdapter);

        fab.setOnClickListener(v -> startActivity(new Intent(this, NoteActivity.class)));

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(this, R.string.press_again_for_exit, Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}