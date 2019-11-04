package com.example.therehabapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<NoteBuilder> myList;
    private View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView title;
        public TextView date;

        public MyViewHolder(View view)
        {
            super(view);
            title =(TextView) view.findViewById(R.id.title);
            date=(TextView) view.findViewById(R.id.date);
        }

    }

    public NotesAdapter(List<NoteBuilder> myList )
    {

        super();
        this.myList=myList;

    }

    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType)
    {

        itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new  MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        NoteBuilder note= myList.get(position);
        holder.title.setText(note.getTitle());
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
