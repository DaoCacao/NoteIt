package core.legion.noteit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import core.legion.noteit.activities.NoteActivity;
import core.legion.noteit.data.Note;
import io.realm.RealmResults;
import io.realm.Sort;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.VH> {
    private final RealmResults<Note> notes;
    private final FragmentManager fManager;
    private final Context context;

    public NoteRecyclerAdapter(Context context, FragmentManager fManager) {
        notes = AppLoader.realm.where(Note.class).findAll().sort("date", Sort.DESCENDING);
        this.context = context;
        this.fManager = fManager;
    }

    class VH extends RecyclerView.ViewHolder {

        final RelativeLayout rootLayout;
        final ImageView imgMore;
        final TextView txtTitle;
        final TextView txtDate;

        VH(View rootLayout) {
            super(rootLayout);

            this.rootLayout = (RelativeLayout) rootLayout;
            imgMore = (ImageView) rootLayout.findViewById(R.id.img_more);
            txtTitle = (TextView) rootLayout.findViewById(R.id.txt_title);
            txtDate = (TextView) rootLayout.findViewById(R.id.txt_date);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater
                .from(context)
                .inflate(R.layout.note_item, new RelativeLayout(context)));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        long id = notes.get(position).getId();
        String title = notes.get(position).getTitle();
        String pass = notes.get(position).getPass();
        String date = DateFormat.format("dd.MM.yyyy", notes.get(position).getDate()).toString();
        int img_resource = (TextUtils.isEmpty(pass)) ? R.drawable.ic_notebook : R.drawable.ic_lock;

        holder.imgMore.setImageResource(img_resource);
        holder.txtTitle.setText(title);
        holder.txtDate.setText(date);

        holder.rootLayout.setOnClickListener(v -> showCheckPassDialog(id, pass, Helper.ACTION_SHOW));
        holder.imgMore.setOnClickListener(v -> showCheckPassDialog(id, pass, Helper.ACTION_EDIT));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private Note getNoteById(long id) {
        return AppLoader.realm.where(Note.class).equalTo("id", id).findFirst();
    }

    private void showCheckPassDialog(long id, String pass, int action) {
        if (TextUtils.isEmpty(pass)) makeAction(id, action);
        else {

            FrameLayout rootLayout = new FrameLayout(context);
            rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rootLayout.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));

            final EditText edPass = new EditText(context);
            edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edPass.setTextColor(Color.BLACK);
            edPass.setHint("Enter Password");
            edPass.setHintTextColor(Color.GRAY);
            edPass.setLines(1);
            rootLayout.addView(edPass);

            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.txt_enter_password)
                    .setIcon(R.drawable.ic_bamboo)
                    .setView(rootLayout)
                    .setPositiveButton(R.string.ok, null)
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .create();

            alertDialog.setOnShowListener(dialog -> {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
                    if (TextUtils.equals(edPass.getText().toString(), pass)) {
                        makeAction(id, action);
                        alertDialog.dismiss();
                    } else
                        edPass.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_txt_anim));
                });
                new Handler().postDelayed(() -> Utils.showKeyboard(edPass), 50);
            });

            alertDialog.setOnDismissListener(dialog -> Utils.hideKeyboard(edPass));

            alertDialog.show();
        }
    }

    private void makeAction(long id, int action) {
        switch (action) {
            case Helper.ACTION_SHOW:
                showNote(id);
                break;
            case Helper.ACTION_EDIT:
                EditNoteBottomSheet editNoteBottomSheet = new EditNoteBottomSheet();
                editNoteBottomSheet.setAdapter(this);
                editNoteBottomSheet.setId(id);
                editNoteBottomSheet.show(fManager, "EditNoteBottomSheet");
                break;
        }
    }

    private void showNote(long id) {
        final Intent intent = new Intent(context, NoteActivity.class);
        intent.putExtra("extra_id", id);
        context.startActivity(intent);
    }

    void showDeleteDialog(long id) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_note)
                .setIcon(R.drawable.ic_bamboo)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    AppLoader.realm.executeTransaction(realm -> getNoteById(id).deleteFromRealm());
                    notifyDataSetChanged();
                    Toast.makeText(context, R.string.txt_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel()).show();
    }

    void showRenameDialog(long id) {
        FrameLayout rootLayout = new FrameLayout(context);
        rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rootLayout.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));

        final EditText edTitle = new EditText(context);
        edTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edTitle.setTextColor(Color.BLACK);
        edTitle.setHint("New note");
        edTitle.setHintTextColor(Color.GRAY);
        edTitle.setLines(1);
        rootLayout.addView(edTitle);

        new AlertDialog.Builder(context)
                .setTitle(R.string.txt_rename)
                .setIcon(R.drawable.ic_bamboo)
                .setView(rootLayout)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    Utils.hideKeyboard(edTitle);
                    AppLoader.realm.executeTransaction(realm -> getNoteById(id).setTitle(edTitle.getText().toString()));
                    notifyDataSetChanged();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    Utils.hideKeyboard(edTitle);
                    dialogInterface.cancel();
                }).show();
        new Handler().postDelayed(() -> Utils.showKeyboard(edTitle), 100);
    }

    void showSetPassDialog(long id) {
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setPadding(Utils.dp(16), Utils.dp(16), Utils.dp(16), Utils.dp(16));

        final EditText edPass = new EditText(context);
        edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edPass.setHint("Enter password");
        edPass.setTextColor(Color.BLACK);
        edPass.setHintTextColor(Color.GRAY);
        edPass.setLines(1);
        rootLayout.addView(edPass);

        final EditText edPassConfirm = new EditText(context);
        edPassConfirm.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edPassConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edPassConfirm.setHint("Repeat password");
        edPassConfirm.setTextColor(Color.BLACK);
        edPassConfirm.setHintTextColor(Color.GRAY);
        edPassConfirm.setLines(1);
        rootLayout.addView(edPassConfirm);

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.txt_set_password)
                .setIcon(R.drawable.ic_bamboo)
                .setView(rootLayout)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();

        alertDialog.setOnShowListener(dialog -> {
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
                if (TextUtils.equals(edPass.getText().toString(), edPassConfirm.getText().toString())) {
                    AppLoader.realm.executeTransaction(realm -> getNoteById(id).setPass(edPass.getText().toString()));
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                } else {
                    edPass.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_txt_anim));
                    edPassConfirm.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_txt_anim));
                }
            });
            Utils.showKeyboard(edPass);
        });

        alertDialog.setOnDismissListener(dialog -> Utils.hideKeyboard(edPass));

        alertDialog.show();
    }
}
