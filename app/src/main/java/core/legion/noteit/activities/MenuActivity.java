package core.legion.noteit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import core.legion.noteit.NoteAdapter;

import com.example.legion.noteit.R;

import core.legion.noteit.Utils;

public class MenuActivity extends AppCompatActivity {
    private ListView notesList;

    private static long back_pressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        NoteAdapter noteAdapter = new NoteAdapter(this, getSupportFragmentManager());
        notesList.setAdapter(noteAdapter);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(this, R.string.press_again_for_exit, Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    private void initView() {
        //--> root layout
        FrameLayout rootLayout = new FrameLayout(this);
        rootLayout.setBackgroundResource(R.drawable.back_bamboo);
        setContentView(rootLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //--> notes list
        notesList = new ListView(this);
        notesList.setDivider(null);
        notesList.setDividerHeight(0);
        notesList.setBackgroundColor(Color.TRANSPARENT);
        rootLayout.addView(notesList, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //--> fab
        FloatingActionButton fab = new FloatingActionButton(this);
        FrameLayout.LayoutParams fabParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.END);
        fabParams.setMargins(Utils.dp(24), Utils.dp(24), Utils.dp(24), Utils.dp(24));
        fab.setImageResource(R.drawable.add);
        fab.setClickable(true);
        rootLayout.addView(fab, fabParams);
        fab.setOnClickListener(v -> startActivity(new Intent(this, NoteActivity.class)));
    }

}