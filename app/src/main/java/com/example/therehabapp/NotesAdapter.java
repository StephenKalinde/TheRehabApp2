package com.example.therehabapp;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<NoteBuilder> myList;
    private View itemView;
    private OnNoteListener onNoteListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        public TextView title;
        public TextView date;
        public ImageButton actionBtn;
        public OnNoteListener onNoteListener;

        public MyViewHolder(View view, OnNoteListener onNoteListener)
        {

            super(view);
            this.title =(TextView) view.findViewById(R.id.title);
            this.date=(TextView) view.findViewById(R.id.date);
            this.actionBtn=(ImageButton)view.findViewById(R.id.action_btn);
            this.onNoteListener=onNoteListener;

            view.setOnClickListener(this);
            actionBtn.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteListener.onLongClick(getAdapterPosition());
            return  false;
        }
    }

    public interface OnNoteListener
    {
        void onNoteClick(int position);

        void onLongClick(int position);
    }




    public NotesAdapter(List<NoteBuilder> myList, OnNoteListener onNoteListener )
    {

        super();
        this.myList=myList;
        this.onNoteListener=onNoteListener;

    }

    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType)
    {

        itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new  MyViewHolder(itemView,onNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        NoteBuilder note= myList.get(position);
        holder.title.setText(note.getTitle().substring(0,note.getTitle().length()-4));
        holder.date.setText(note.getDate());


    }

    @Override
    public int getItemCount()
    {
        return myList.size();
    }

    public void onActivityResult(int requestCode,int resultCode)
    {
        this.notifyDataSetChanged();
    }

}
