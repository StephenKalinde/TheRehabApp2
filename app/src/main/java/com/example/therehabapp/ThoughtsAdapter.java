package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.therehabapp.ValueObjects.Thought;

import java.util.ArrayList;

public class ThoughtsAdapter extends ArrayAdapter{

    private ArrayList<Thought> thoughtsList;
    private Activity context;

    public ThoughtsAdapter(Activity context, ArrayList<Thought> thoughtsList) {
        super(context, R.layout.thought_item_view, thoughtsList);

        this.thoughtsList = thoughtsList;
        this.context =context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.thought_item_view,null,true);

        TextView nameView =(TextView) view.findViewById(R.id.name_view);
        TextView thoughtView =(TextView) view.findViewById(R.id.thought_view);

        Thought thought = thoughtsList.get(position);

        nameView.setText(thought.Name);
        thoughtView.setText(thought.ThoughtMessage);

        return view;

    }
}
