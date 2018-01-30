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

import core.legion.noteit.note_list_screen.NoteListAdapter;

public class EditNoteBottomSheet extends BottomSheetDialogFragment {

    private NoteListAdapter adapter;
    private long id;

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
        txtSetPass.setPadding(AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16));
        txtSetPass.setText(R.string.txt_set_password);
        txtSetPass.setTextSize(21);
        txtSetPass.setTextColor(Color.BLACK);
        txtSetPass.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(AppLoaderDep.appContext, R.drawable.ic_lock), null, null, null);
        txtSetPass.setCompoundDrawablePadding(AppUtils.dp(16));
        rootLayout.addView(txtSetPass, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtSetPass.setOnClickListener(v -> {
//            adapter.showSetPassDialog(id);
            dialog.dismiss();
        });

        //--> rename
        TextView txtRename = new TextView(getContext());
        txtRename.setClickable(true);
        txtRename.setGravity(Gravity.CENTER_VERTICAL);
        txtRename.setPadding(AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16));
        txtRename.setText(R.string.txt_rename);
        txtRename.setTextSize(21);
        txtRename.setTextColor(Color.BLACK);
        txtRename.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(AppLoaderDep.appContext, R.drawable.ic_edit), null, null, null);
        txtRename.setCompoundDrawablePadding(AppUtils.dp(16));
        rootLayout.addView(txtRename, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtRename.setOnClickListener(v -> {
//            adapter.showRenameDialog(id);
            dialog.dismiss();
        });

        //--> delete
        TextView txtDelete = new TextView(getContext());
        txtDelete.setClickable(true);
        txtDelete.setGravity(Gravity.CENTER_VERTICAL);
        txtDelete.setPadding(AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16));
        txtDelete.setText(R.string.txt_delete);
        txtDelete.setTextSize(21);
        txtDelete.setTextColor(Color.BLACK);
        txtDelete.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(AppLoaderDep.appContext, R.drawable.ic_trash), null, null, null);
        txtDelete.setCompoundDrawablePadding(AppUtils.dp(16));
        rootLayout.addView(txtDelete, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        txtDelete.setOnClickListener(v -> {
//            adapter.showDeleteDialog(id);
            dialog.dismiss();
        });

    }

    public void setAdapter(NoteListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setId(long id) {
        this.id = id;
    }
}
