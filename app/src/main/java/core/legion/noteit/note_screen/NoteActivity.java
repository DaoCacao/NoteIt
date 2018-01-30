package core.legion.noteit.note_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import core.legion.noteit.PasswordBottomSheet;
import core.legion.noteit.SimpleTextWatcher;

import core.legion.noteit.R;
import dagger.android.support.DaggerAppCompatActivity;

public class NoteActivity extends DaggerAppCompatActivity implements NoteMvp.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.ed_title) EditText edTitle;
    @BindView(R.id.ed_text) EditText edText;

    @Inject NoteMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> presenter.onBackClick());

        presenter.onViewInit(getIntent().getLongExtra("extra_id", -1));

        edTitle.addTextChangedListener((SimpleTextWatcher) s -> presenter.onTitleChange(s.toString()));
        edText.addTextChangedListener((SimpleTextWatcher) s -> presenter.onTextChange(s.toString()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                presenter.onSaveClick();
                break;
            case R.id.menu_password:
                presenter.onPasswordClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackClick();
    }

    @Override
    public void showTitle(String title) {
        edTitle.setText(title);
    }

    @Override
    public void showText(String text) {
        edText.setText(text);
    }

    @Override
    public void closeView() {
        finish();
    }

    @Override
    public void showQuitDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.save_before_exit)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> presenter.onQuitConfirmClick())
                .setNegativeButton(android.R.string.no, (dialog, which) -> presenter.onQuitDenyClick())
                .show();
    }

    @Override
    public void showPasswordDialog() {
        PasswordBottomSheet passwordBottomSheet = new PasswordBottomSheet();
        passwordBottomSheet.show(getSupportFragmentManager(), passwordBottomSheet.getTag());
    }
}