package core.legion.noteit;

import android.app.Dialog;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.legion.noteit.R;


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
        txtSetPass.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(AppLoader.appContext, R.drawable.ic_lock), null, null, null);
        txtSetPass.setCompoundDrawablePadding(Utils.dp(16));
        rootLayout.addView(txtSetPass, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtSetPass.setOnClickListener(v -> {
            noteAdapter.setPassForNote(position);
            dialog.dismiss();
        });

        //--> rename
        TextView txtRename = new TextView(getContext());
        txtRename.setClickable(true);
        txtRename.setGravity(Gravity.CENTER_VERTICAL);
        txtRename.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));
        txtRename.setText(R.string.txt_rename);
        txtRename.setTextSize(21);
        txtRename.setTextColor(Color.BLACK);
        txtRename.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(AppLoader.appContext, R.drawable.ic_edit), null, null, null);
        txtRename.setCompoundDrawablePadding(Utils.dp(16));
        rootLayout.addView(txtRename, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtRename.setOnClickListener(v -> {
            noteAdapter.renameNote(position);
            dialog.dismiss();
        });

        //--> delete
        TextView txtDelete = new TextView(getContext());
        txtDelete.setClickable(true);
        txtDelete.setGravity(Gravity.CENTER_VERTICAL);
        txtDelete.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));
        txtDelete.setText(R.string.txt_delete);
        txtDelete.setTextSize(21);
        txtDelete.setTextColor(Color.BLACK);
        txtDelete.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(AppLoader.appContext, R.drawable.ic_trash), null, null, null);
        txtDelete.setCompoundDrawablePadding(Utils.dp(16));
        rootLayout.addView(txtDelete, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtDelete.setOnClickListener(v -> {
            noteAdapter.deleteNote(position);
            dialog.dismiss();
        });

    }

    public void setNoteAdapter(NoteAdapter noteAdapter) {
        this.noteAdapter = noteAdapter;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
