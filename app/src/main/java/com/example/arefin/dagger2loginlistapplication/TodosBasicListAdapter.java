package com.example.arefin.dagger2loginlistapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arefin.dagger2loginlistapplication.models.Todo;

import java.util.List;

public class TodosBasicListAdapter extends RecyclerView.Adapter<ViewHolderBasicList> {

    private List<Todo> todoList;

    public TodosBasicListAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public ViewHolderBasicList onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cell, parent, false);

        return new ViewHolderBasicList(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderBasicList holder, int position) {
        Todo todo = todoList.get(position);
        holder.title.setText(todo.getTitle());
        holder.userId.setText(todo.getId().toString());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TodoDetailsActivity.class);
                intent.putExtra(Constants.TODO_ID,todo.getId() );
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
