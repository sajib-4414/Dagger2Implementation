package com.example.arefin.dagger2loginlistapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolderBasicList extends RecyclerView.ViewHolder {
    public TextView title, userId;

    public ViewHolderBasicList(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.tvTodoTitle);
        userId = (TextView) itemView.findViewById(R.id.tvTodoUserId);
    }
}
