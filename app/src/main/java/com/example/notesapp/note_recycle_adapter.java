package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class note_recycle_adapter extends RecyclerView.Adapter<note_recycle_adapter.MyViewHolder> {
    Context context;
    ArrayList<notes> note = new ArrayList<>();

    private recyclerInterface recyclerInterface;

    public note_recycle_adapter(Context context, ArrayList<notes> note, recyclerInterface recyclerInterface) {
        this.context = context;
        this.note = note;
        this.recyclerInterface=recyclerInterface;
    }

    @NonNull
    @Override
    public note_recycle_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardlayout,parent,false);
        return new note_recycle_adapter.MyViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull note_recycle_adapter.MyViewHolder holder, int position) {

        holder.text.setText(note.get(position).getText());
        holder.title.setText(note.get(position).getTitle());

        //below method is for setting on click response of the view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerInterface.onRecycleClick(note.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return note.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text, title;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            title = itemView.findViewById(R.id.title);
            cardView=itemView.findViewById(R.id.Cardlayout);
        }
    }
}
