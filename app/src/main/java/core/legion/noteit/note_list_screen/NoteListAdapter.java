package core.legion.noteit.note_list_screen;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import core.legion.noteit.data.Note;
import io.realm.RealmResults;

public class NoteListAdapter extends RecyclerView.Adapter<NoteVH> {
    private RealmResults<Note> notes;
    private NoteListMvp.OnNoteClickListener onNoteClickListener;

    NoteListAdapter(RealmResults<Note> notes, NoteListMvp.OnNoteClickListener onNoteClickListener) {
        this.notes = notes;
        this.onNoteClickListener = onNoteClickListener;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteVH(parent, onNoteClickListener);
    }

    @Override
    public void onBindViewHolder(NoteVH holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

//    private Note getNoteById(long id) {
//        return AppLoaderDep.realm.where(Note.class).equalTo("id", id).findFirst();
//    }

//    private void showCheckPassDialog(long id, String pass, int action) {
//        if (TextUtils.isEmpty(pass)) makeAction(id, action);
//        else {
//
//            FrameLayout rootLayout = new FrameLayout(context);
//            rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            rootLayout.setPadding(AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16));
//
//            final EditText edPass = new EditText(context);
//            edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            edPass.setTextColor(Color.BLACK);
//            edPass.setHint("Enter Password");
//            edPass.setHintTextColor(Color.GRAY);
//            edPass.setLines(1);
//            rootLayout.addView(edPass);
//
//            AlertDialog alertDialog = new AlertDialog.Builder(context)
//                    .setTitle(R.string.txt_enter_password)
//                    .setIcon(R.drawable.ic_bamboo)
//                    .setView(rootLayout)
//                    .setPositiveButton(R.string.ok, null)
//                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
//                    .create();
//
//            alertDialog.setOnShowListener(dialog -> {
//                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
//                    if (TextUtils.equals(edPass.getText().toString(), pass)) {
//                        makeAction(id, action);
//                        alertDialog.dismiss();
//                    } else
//                        edPass.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim));
//                });
//                new Handler().postDelayed(() -> AppUtils.showKeyboard(edPass), 50);
//            });
//
//            alertDialog.setOnDismissListener(dialog -> AppUtils.hideKeyboard(edPass));
//
//            alertDialog.show();
//        }
//    }
//
//    private void makeAction(long id, int action) {
//        switch (action) {
//            case Helper.ACTION_SHOW:
//                showNote(id);
//                break;
//            case Helper.ACTION_EDIT:
//                EditNoteBottomSheet editNoteBottomSheet = new EditNoteBottomSheet();
//                editNoteBottomSheet.setAdapter(this);
//                editNoteBottomSheet.setId(id);
//                editNoteBottomSheet.show(fManager, "EditNoteBottomSheet");
//                break;
//        }
//    }
//
//    private void showNote(long id) {
//        final Intent intent = new Intent(context, NoteActivity.class);
//        intent.putExtra("extra_id", id);
//        context.startActivity(intent);
//    }
//
//    void showDeleteDialog(long id) {
//        new AlertDialog.Builder(context)
//                .setTitle(R.string.delete_note)
//                .setIcon(R.drawable.ic_bamboo)
//                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
//                    AppLoaderDep.realm.executeTransaction(realm -> getNoteById(id).deleteFromRealm());
//                    notifyDataSetChanged();
//                    Toast.makeText(context, R.string.txt_deleted, Toast.LENGTH_SHORT).show();
//                })
//                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel()).show();
//    }
//
//    void showRenameDialog(long id) {
//        FrameLayout rootLayout = new FrameLayout(context);
//        rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        rootLayout.setPadding(AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16));
//
//        final EditText edTitle = new EditText(context);
//        edTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        edTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        edTitle.setTextColor(Color.BLACK);
//        edTitle.setHint("New note");
//        edTitle.setHintTextColor(Color.GRAY);
//        edTitle.setLines(1);
//        rootLayout.addView(edTitle);
//
//        new AlertDialog.Builder(context)
//                .setTitle(R.string.txt_rename)
//                .setIcon(R.drawable.ic_bamboo)
//                .setView(rootLayout)
//                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
//                    AppUtils.hideKeyboard(edTitle);
//                    AppLoaderDep.realm.executeTransaction(realm -> getNoteById(id).setTitle(edTitle.getText().toString()));
//                    notifyDataSetChanged();
//                })
//                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
//                    AppUtils.hideKeyboard(edTitle);
//                    dialogInterface.cancel();
//                }).show();
//        new Handler().postDelayed(() -> AppUtils.showKeyboard(edTitle), 100);
//    }
//
//    void showSetPassDialog(long id) {
//        LinearLayout rootLayout = new LinearLayout(context);
//        rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        rootLayout.setOrientation(LinearLayout.VERTICAL);
//        rootLayout.setPadding(AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16), AppUtils.dp(16));
//
//        final EditText edPass = new EditText(context);
//        edPass.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        edPass.setHint("Enter password");
//        edPass.setTextColor(Color.BLACK);
//        edPass.setHintTextColor(Color.GRAY);
//        edPass.setLines(1);
//        rootLayout.addView(edPass);
//
//        final EditText edPassConfirm = new EditText(context);
//        edPassConfirm.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        edPassConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        edPassConfirm.setHint("Repeat password");
//        edPassConfirm.setTextColor(Color.BLACK);
//        edPassConfirm.setHintTextColor(Color.GRAY);
//        edPassConfirm.setLines(1);
//        rootLayout.addView(edPassConfirm);
//
//        AlertDialog alertDialog = new AlertDialog.Builder(context)
//                .setTitle(R.string.txt_set_password)
//                .setIcon(R.drawable.ic_bamboo)
//                .setView(rootLayout)
//                .setPositiveButton(R.string.ok, null)
//                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
//                .create();
//
//        alertDialog.setOnShowListener(dialog -> {
//            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
//                if (TextUtils.equals(edPass.getText().toString(), edPassConfirm.getText().toString())) {
//                    AppLoaderDep.realm.executeTransaction(realm -> getNoteById(id).setPass(edPass.getText().toString()));
//                    notifyDataSetChanged();
//                    alertDialog.dismiss();
//                } else {
//                    edPass.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim));
//                    edPassConfirm.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim));
//                }
//            });
//            AppUtils.showKeyboard(edPass);
//        });
//
//        alertDialog.setOnDismissListener(dialog -> AppUtils.hideKeyboard(edPass));
//
//        alertDialog.show();
//    }
}
