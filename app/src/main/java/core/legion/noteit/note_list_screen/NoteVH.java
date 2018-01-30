package core.legion.noteit.note_list_screen;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import core.legion.noteit.R;
import core.legion.noteit.data.Note;

class NoteVH extends RecyclerView.ViewHolder {

    private final NoteListMvp.OnNoteClickListener onNoteClickListener;
    @BindView(R.id.iv_icon) ImageView ivIcon;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_date) TextView tvDate;

    @BindDrawable(R.drawable.ic_notebook) Drawable imgNotebook;
    @BindDrawable(R.drawable.ic_lock) Drawable imgLock;

    NoteVH(ViewGroup parent, NoteListMvp.OnNoteClickListener onNoteClickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
        ButterKnife.bind(this, itemView);

        this.onNoteClickListener = onNoteClickListener;
    }

    void bind(Note note) {
        long id = note.getId();
        String title = note.getTitle();
        String pass = note.getPass();
        String date = DateFormat.format("dd.MM.yyyy", note.getDate()).toString();

        ivIcon.setImageDrawable(TextUtils.isEmpty(pass) ? imgNotebook : imgLock);
        tvTitle.setText(title);
        tvDate.setText(date);

        itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(id));
    }
}

