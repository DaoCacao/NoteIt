package core.legion.noteit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import core.legion.noteit.AppLoader;
import core.legion.noteit.Note;
import com.example.legion.noteit.R;
import core.legion.noteit.Utils;

public class NoteActivity extends AppCompatActivity {
    private EditText edText;
    private int id;
    private boolean is_text_changed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edText = new EditText(this);
        edText.setBackgroundResource(R.drawable.paper);
        edText.setGravity(Gravity.TOP | Gravity.START);
        edText.setPadding(Utils.dp(8), Utils.dp(8), Utils.dp(8), Utils.dp(8));
        edText.setHint(R.string.hint_text);
        edText.setHintTextColor(Color.GRAY);
        edText.setTextSize(18);
        edText.setInputType(edText.getInputType() | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        setContentView(edText,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        is_text_changed = false;

        id = getIntent().getIntExtra("extra_id", -1);
        if (id >= 0) {
            edText.setText(AppLoader.realm.where(Note.class).findAll().get(id).getText());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(AppLoader.realm.where(Note.class).findAll().get(id).getTitle());
            }
        }


        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                is_text_changed = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        applyChanges();
        goToMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (is_text_changed) {
            new AlertDialog.Builder(NoteActivity.this)
                    .setTitle(R.string.save_before_exit)
                    .setIcon(R.drawable.ic_bamboo)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        applyChanges();
                        goToMenu();
                    })
                    .setNegativeButton(R.string.no, (dialogInterface, i) -> goToMenu())
                    .setNeutralButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel()).show();
        } else super.onBackPressed();
    }

    private void applyChanges() {
        AppLoader.realm.beginTransaction();
        if (id == -1) AppLoader.realm.copyToRealm(new Note("", edText.getText().toString()));
        else
            AppLoader.realm.where(Note.class).findAll().get(id).setText(edText.getText().toString());
        AppLoader.realm.commitTransaction();

        Toast.makeText(getApplicationContext(), R.string.txt_saved, Toast.LENGTH_SHORT).show();
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}