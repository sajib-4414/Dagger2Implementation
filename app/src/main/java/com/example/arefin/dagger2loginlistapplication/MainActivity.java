package com.example.arefin.dagger2loginlistapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputUsername, inputPassword;
    Button btnShowTodos, btnShowSavedCredentials,btnClearSavedCredentials;

    //once you injected a module, you can inject as many things as you want
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor sharedPrefEditor;

    //the component which will help dagger injection
    private SharedPrefComponent sharedPrefComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDependencyInjections();
    }

    private void showTodos() {
        if(sharedPreferences.contains(Constants.USERNAME_KEY) && sharedPreferences.contains(Constants.PASSWORD_KEY))
                showTodosScreen();
        else {
            Toast.makeText(getApplicationContext(),"Login first",Toast.LENGTH_SHORT).show();
            showLoginScreen();
        }
    }

    private void showTodosScreen()
    {
        Intent intent = new Intent(this, TodoLists.class);
        startActivity(intent);
    }

    private void showLoginScreen()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void initDependencyInjections() {
        //the DaggerSharedPrefComponent name is generated after a rebuild
        //the sharedPreferenceModule is also generated
        sharedPrefComponent  = DaggerSharedPrefComponent
                .builder()
                //the number of modules declared in the pref module, all can be here to use
                .sharedPreferenceModule(new SharedPreferenceModule(getApplicationContext()))
                .build();
        sharedPrefComponent.inject(this);
    }

    private void initViews() {
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        btnShowTodos = findViewById(R.id.btnShowTodos);
        btnShowTodos.setOnClickListener(this);
        btnShowSavedCredentials = findViewById(R.id.btnShowSavedCredentials);
        btnShowSavedCredentials.setOnClickListener(this);
        btnClearSavedCredentials = findViewById(R.id.btnClearSavedCredentials);
        btnClearSavedCredentials.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnShowTodos:
                showTodos();
                break;
            case R.id.btnShowSavedCredentials:
                if(inputPassword.getVisibility() == View.VISIBLE){
                    btnShowSavedCredentials.setText(btnShowSavedCredentials.getText().toString().replace("Hide","Show"));
                    inputUsername.setVisibility(View.GONE);
                    inputPassword.setVisibility(View.GONE);
                }
                else{
                    inputUsername.setText(sharedPreferences.getString(Constants.USERNAME_KEY,""));
                    inputPassword.setText(sharedPreferences.getString(Constants.PASSWORD_KEY,""));
                    inputUsername.setVisibility(View.VISIBLE);
                    inputPassword.setVisibility(View.VISIBLE);
                    btnShowSavedCredentials.setText(btnShowSavedCredentials.getText().toString().replace("Show","Hide"));
                    Toast.makeText(getApplicationContext(),"Data shown",Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.btnClearSavedCredentials:
                sharedPrefEditor.remove(Constants.USERNAME_KEY);
                sharedPrefEditor.remove(Constants.PASSWORD_KEY);
                inputPassword.setText("");
                inputUsername.setText("");
                Toast.makeText(getApplicationContext(),"Data cleared",Toast.LENGTH_SHORT).show();
                sharedPrefEditor.apply();
        }
    }
}
