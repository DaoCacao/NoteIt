package core.legion.noteit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import core.legion.noteit.AppLoader;
import core.legion.noteit.Note;

import core.legion.noteit.R;
import core.legion.noteit.Utils;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edTitle, edText;
    private boolean isTextChanged = false;

    private RealmResults<Note> notes;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edTitle = (EditText) findViewById(R.id.ed_title);
        edText = (EditText) findViewById(R.id.ed_text);

        id = getIntent().getIntExtra("extra_id", -1);
        notes = AppLoader.realm.where(Note.class).findAll();
        if (id >= 0) {
            edTitle.setText(notes.get(id).getTitle());
            edText.setText(notes.get(id).getText());
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
        }

        edTitle.addTextChangedListener(textChangeWatcher());
        edText.addTextChangedListener(textChangeWatcher());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.apply) {
            applyChanges();
            goToMenu();
        } else onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isTextChanged) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.save_before_exit)
                    .setIcon(R.drawable.ic_bamboo)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        applyChanges();
                        goToMenu();
                    })
                    .setNegativeButton(R.string.no, (dialogInterface, i) -> goToMenu())
                    .setNeutralButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        } else super.onBackPressed();
    }

    private TextWatcher textChangeWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isTextChanged = true;
            }
        };
    }

    private void applyChanges() {
        AppLoader.realm.beginTransaction();
        if (id == -1)
            AppLoader.realm.copyToRealm(new Note(edTitle.getText().toString(), edText.getText().toString()));
        else {
            notes.get(id).setTitle(edTitle.getText().toString());
            notes.get(id).setText(edText.getText().toString());
        }
        AppLoader.realm.commitTransaction();

        Toast.makeText(getApplicationContext(), R.string.txt_saved, Toast.LENGTH_SHORT).show();
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}