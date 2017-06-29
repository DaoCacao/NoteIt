package core.legion.noteit.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import core.legion.noteit.AppLoader;
import core.legion.noteit.data.Note;

import core.legion.noteit.R;

public class NoteActivity extends AppCompatActivity {

    private EditText edTitle, edText;
    private boolean isTextChanged = false;

    private Note note;
    private long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        edTitle = (EditText) findViewById(R.id.ed_title);
        edText = (EditText) findViewById(R.id.ed_text);


        id = getIntent().getLongExtra("extra_id", -1);
        if (id >= 0) {
            note = AppLoader.realm.where(Note.class).equalTo("id", id).findFirst();
            edTitle.setText(note.getTitle());
            edText.setText(note.getText());
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
        }

        EditTextWatcher editTextWatcher = new EditTextWatcher();
        edTitle.addTextChangedListener(editTextWatcher);
        edText.addTextChangedListener(editTextWatcher);
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
            super.onBackPressed();
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
                        super.onBackPressed();
                    })
                    .setNegativeButton(R.string.no, (dialogInterface, i) -> super.onBackPressed())
                    .setNeutralButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        } else super.onBackPressed();
    }

    private void applyChanges() {
        AppLoader.realm.executeTransaction(realm -> {
            if (id == -1)
                realm.copyToRealmOrUpdate(new Note(edTitle.getText().toString(), edText.getText().toString()));
            else {
                note.setTitle(edTitle.getText().toString());
                note.setText(edText.getText().toString());
                note.setDate(System.currentTimeMillis());
            }
        });
        Toast.makeText(getApplicationContext(), R.string.txt_saved, Toast.LENGTH_SHORT).show();
    }

    private class EditTextWatcher implements TextWatcher {

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
    }
}