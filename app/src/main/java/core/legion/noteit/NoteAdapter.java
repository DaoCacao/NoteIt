package core.legion.noteit;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legion.noteit.R;

import core.legion.noteit.activities.NoteActivity;

import io.realm.RealmResults;

public class NoteAdapter extends BaseAdapter {
    private final RealmResults<Note> notes = AppLoader.realm.where(Note.class).findAll();
    private final FragmentManager fManager;
    private final Context context;

    public NoteAdapter(Context context, FragmentManager fManager) {
        this.context = context;
        this.fManager = fManager;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View v, final ViewGroup viewGroup) {
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);
        rootLayout.setOnClickListener(v12 -> {
            final String pass = AppLoader.realm.where(Note.class).findAll().get(i).getPass();
            if (pass == null || pass.equals("")) editNote(i);
            else {
                final EditText edPass = new EditText(context);
                edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                edPass.setLines(1);

                new AlertDialog.Builder(context)
                        .setTitle(R.string.txt_enter_password)
                        .setIcon(R.drawable.ic_bamboo)
                        .setView(edPass)
                        .setPositiveButton(R.string.ok, (dialogInterface, i13) -> {
                            if (edPass.getText().toString().equals(pass)) {
                                Utils.hideKeyboard(edPass);
                                editNote(i13);
                            }
                        })
                        .setNegativeButton(R.string.cancel, (dialogInterface, i12) -> {
                            Utils.hideKeyboard(edPass);
                            dialogInterface.cancel();
                        }).show();
                new Handler().postDelayed(() -> Utils.showKeyboard(edPass), 100);
            }
        });

        ImageButton btnMore = new ImageButton(context);
        LinearLayout.LayoutParams btnMoreParams = new LinearLayout.LayoutParams(Utils.dp(40), Utils.dp(40));
        btnMoreParams.setMargins(Utils.dp(8), Utils.dp(8), Utils.dp(8), Utils.dp(8));
        btnMore.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setImageResource((notes.get(i).getPass() != null && !notes.get(i).getPass().isEmpty()) ? R.drawable.ic_lock : R.drawable.ic_notebook);
        btnMore.setClickable(true);
        btnMore.setOnClickListener(v1 -> {
            final EditNoteBottomSheet editNoteBottomSheet = new EditNoteBottomSheet();
            editNoteBottomSheet.setNoteAdapter(NoteAdapter.this);
            editNoteBottomSheet.setPosition(i);

            final String pass = AppLoader.realm.where(Note.class).findAll().get(i).getPass();
            if (pass == null || pass.equals(""))
                editNoteBottomSheet.show(fManager, "EditNoteBottomSheet");
            else {
                final EditText edPass = new EditText(context);
                edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                edPass.setLines(1);

                new AlertDialog.Builder(context)
                        .setTitle(R.string.txt_enter_password)
                        .setIcon(R.drawable.ic_bamboo)
                        .setView(edPass)
                        .setPositiveButton(R.string.ok, (dialogInterface, i1) -> {
                            if (edPass.getText().toString().equals(pass)) {
                                Utils.hideKeyboard(edPass);
                                editNoteBottomSheet.show(fManager, "EditNoteBottomSheet");
                            }
                        })
                        .setNegativeButton(R.string.cancel, (dialogInterface, i1) -> {
                            Utils.hideKeyboard(edPass);
                            dialogInterface.cancel();
                        }).show();
                new Handler().postDelayed(() -> Utils.showKeyboard(edPass), 100);
            }
        });
        rootLayout.addView(btnMore, btnMoreParams);


        TextView txtTitle = new TextView(context);
        txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtTitle.setLines(1);
        txtTitle.setPadding(Utils.dp(8), Utils.dp(8), Utils.dp(8), Utils.dp(8));
        txtTitle.setHint(R.string.new_note);
        txtTitle.setHintTextColor(Color.GRAY);
        txtTitle.setTextSize(21);
        txtTitle.setTextColor(Color.BLACK);
        txtTitle.setText(notes.get(i).getTitle());
        txtTitle.setBackgroundColor(Color.TRANSPARENT);
        rootLayout.addView(txtTitle, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return rootLayout;
    }

    private void editNote(final int position) {
        final Intent intent = new Intent(context, NoteActivity.class);
        intent.putExtra("extra_id", position);
        context.startActivity(intent);
    }

    void deleteNote(final int position) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_note)
                .setIcon(R.drawable.ic_bamboo)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    AppLoader.realm.beginTransaction();
                    AppLoader.realm.where(Note.class).findAll().get(position).deleteFromRealm();
                    AppLoader.realm.commitTransaction();
                    notifyDataSetChanged();
                    Toast.makeText(context, R.string.txt_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel()).show();
    }

    void renameNote(final int position) {
        final EditText edTitle = new EditText(context);
        edTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edTitle.setLines(1);

        new AlertDialog.Builder(context)
                .setTitle(R.string.txt_rename)
                .setIcon(R.drawable.ic_bamboo)
                .setView(edTitle)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    Utils.hideKeyboard(edTitle);
                    AppLoader.realm.beginTransaction();
                    AppLoader.realm.where(Note.class).findAll().get(position).setTitle(edTitle.getText().toString());
                    AppLoader.realm.commitTransaction();
                    notifyDataSetChanged();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    Utils.hideKeyboard(edTitle);
                    dialogInterface.cancel();
                }).show();
        new Handler().postDelayed(() -> Utils.showKeyboard(edTitle), 100);
    }

    void setPassForNote(final int position) {
        final EditText edPass = new EditText(context);
        edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edPass.setLines(1);

        new AlertDialog.Builder(context)
                .setTitle(R.string.txt_set_password)
                .setIcon(R.drawable.ic_bamboo)
                .setView(edPass)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    Utils.hideKeyboard(edPass);
                    AppLoader.realm.beginTransaction();
                    AppLoader.realm.where(Note.class).findAll().get(position).setPass(edPass.getText().toString());
                    AppLoader.realm.commitTransaction();
                    notifyDataSetChanged();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    Utils.hideKeyboard(edPass);
                    dialogInterface.cancel();
                }).show();
        new Handler().postDelayed(() -> Utils.showKeyboard(edPass), 100);

    }

}
