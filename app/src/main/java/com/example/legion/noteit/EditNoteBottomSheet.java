package com.example.legion.noteit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legion.noteit.activities.MenuActivity;
import com.example.legion.noteit.activities.NoteActivity;

public class EditNoteBottomSheet extends BottomSheetDialogFragment {

    private NoteAdapter noteAdapter;
    private int position;

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        LinearLayout rootLayout = new LinearLayout(getContext());
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        dialog.setContentView(rootLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //--> set password
        TextView txtSetPass = new TextView(getContext());
        txtSetPass.setClickable(true);
        txtSetPass.setGravity(Gravity.CENTER_VERTICAL);
        txtSetPass.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));
        txtSetPass.setText(R.string.txt_set_password);
        txtSetPass.setTextSize(21);
        txtSetPass.setTextColor(Color.BLACK);
        txtSetPass.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.ic_key), null, null, null);
        txtSetPass.setCompoundDrawablePadding(Utils.dp(16));
        rootLayout.addView(txtSetPass, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtSetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapter.setPassForNote(position);
                dialog.dismiss();
            }
        });

        //--> rename
        TextView txtRename = new TextView(getContext());
        txtRename.setClickable(true);
        txtRename.setGravity(Gravity.CENTER_VERTICAL);
        txtRename.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));
        txtRename.setText(R.string.txt_rename);
        txtRename.setTextSize(21);
        txtRename.setTextColor(Color.BLACK);
        txtRename.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.ic_rename), null, null, null);
        txtRename.setCompoundDrawablePadding(Utils.dp(16));
        rootLayout.addView(txtRename, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapter.renameNote(position);
                dialog.dismiss();
            }
        });

        //--> delete
        TextView txtDelete = new TextView(getContext());
        txtDelete.setClickable(true);
        txtDelete.setGravity(Gravity.CENTER_VERTICAL);
        txtDelete.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));
        txtDelete.setText(R.string.txt_delete);
        txtDelete.setTextSize(21);
        txtDelete.setTextColor(Color.BLACK);
        txtDelete.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete), null, null, null);
        txtDelete.setCompoundDrawablePadding(Utils.dp(16));
        rootLayout.addView(txtDelete, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapter.deleteNote(position);
                dialog.dismiss();
            }
        });

    }

    public void setNoteAdapter(NoteAdapter noteAdapter) {
        this.noteAdapter = noteAdapter;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
