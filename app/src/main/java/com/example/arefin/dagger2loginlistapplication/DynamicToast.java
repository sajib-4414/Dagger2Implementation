package com.example.arefin.dagger2loginlistapplication;

import android.content.Context;
import android.widget.Toast;

public class DynamicToast {

    Context context;

    public DynamicToast(Context context){
        this.context = context;
    }

    public void show(String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
