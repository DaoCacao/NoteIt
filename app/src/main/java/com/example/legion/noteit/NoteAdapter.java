package com.example.legion.noteit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legion.noteit.activities.MenuActivity;
import com.example.legion.noteit.activities.NoteActivity;

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

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = AppLoader.realm.where(Note.class).findAll().get(i).getPass();
                if (pass == null || pass.equals("")) editNote(i);
                else {
                    final EditText edPass = new EditText(context);
                    edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    edPass.setLines(1);

                    new AlertDialog.Builder(context)
                            .setTitle(R.string.txt_enter_password)
                            .setIcon(R.drawable.alert_icon)
                            .setView(edPass)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (edPass.getText().toString().equals(pass)) editNote(i);

                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).show();
                }
            }
        });

        ImageButton btnMore = new ImageButton(context);
        LinearLayout.LayoutParams btnMoreParams = new LinearLayout.LayoutParams(Utils.dp(24), Utils.dp(24));
        btnMoreParams.setMargins(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));
        btnMore.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setImageResource(R.drawable.ic_dots);
        btnMore.setClickable(true);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            .setIcon(R.drawable.alert_icon)
                            .setView(edPass)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (edPass.getText().toString().equals(pass))
                                        editNoteBottomSheet.show(fManager, "EditNoteBottomSheet");

                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).show();
                }
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

        ImageView imgLock = new ImageView(context);

        if (notes.get(i).getPass() != null && !notes.get(i).getPass().isEmpty())
            imgLock.setImageResource(R.drawable.ic_locked);
        rootLayout.addView(imgLock, new LinearLayout.LayoutParams(Utils.dp(16), ViewGroup.LayoutParams.MATCH_PARENT));

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
                .setIcon(R.drawable.alert_icon)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppLoader.realm.beginTransaction();
                        AppLoader.realm.where(Note.class).findAll().get(position).deleteFromRealm();
                        AppLoader.realm.commitTransaction();
                        notifyDataSetChanged();
                        Toast.makeText(context, R.string.txt_deleted, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

    void renameNote(final int position) {
        final EditText edTitle = new EditText(context);
        edTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edTitle.setLines(1);

        new AlertDialog.Builder(context)
                .setTitle(R.string.txt_rename)
                .setIcon(R.drawable.alert_icon)
                .setView(edTitle)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppLoader.realm.beginTransaction();
                        AppLoader.realm.where(Note.class).findAll().get(position).setTitle(edTitle.getText().toString());
                        AppLoader.realm.commitTransaction();
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

    void setPassForNote(final int position) {
        final EditText edPass = new EditText(context);
        edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edPass.setLines(1);

        new AlertDialog.Builder(context)
                .setTitle(R.string.txt_set_password)
                .setIcon(R.drawable.alert_icon)
                .setView(edPass)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppLoader.realm.beginTransaction();
                        AppLoader.realm.where(Note.class).findAll().get(position).setPass(edPass.getText().toString());
                        AppLoader.realm.commitTransaction();
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

}
