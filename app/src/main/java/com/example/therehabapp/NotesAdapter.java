package com.example.therehabapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class NotesAdapter   {


    private List<NoteBuilder> myList;
    private View itemView;




    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView title;
        public TextView content;
        public MyViewHolder(View view)
        {
            super(view);
            title =(TextView) view.findViewById(R.id.title);
            content=(TextView) view.findViewById(R.id.content);
        }

    }

    public NotesAdapter(List<NoteBuilder> myList )
    { this.myList=myList;}


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType)
    {
        itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new  MyViewHolder(itemView);
    }


    public void onBindViewHolder( MyViewHolder holder, int position)
    {
        NoteBuilder note= myList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
    }


    public int getItemCount()
    {return myList.size();}
}
