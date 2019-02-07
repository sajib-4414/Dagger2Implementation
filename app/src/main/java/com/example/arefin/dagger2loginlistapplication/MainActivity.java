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
    Button btnLogin,btnShowSavedData;

    @Inject
    SharedPreferences sharedPreferences;
    //the component which will help dagger injection
    private SharedPrefComponent sharedPrefComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDependencyInjections();
        checkIsLoggedIn();
    }

    private void checkIsLoggedIn() {
        if(sharedPreferences.getString(Constants.USERNAME_KEY,"").length() != 0
            && sharedPreferences.getString(Constants.PASSWORD_KEY,"").length() != 0) {
            Toast.makeText(getApplicationContext(),"You are logged in", Toast.LENGTH_SHORT).show();
            showTodos();
        }
    }

    private void showTodos()
    {
        Intent intent = new Intent(this, TodoLists.class);
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
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnShowSavedData = findViewById(R.id.btnShowData);
        btnShowSavedData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogin:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.USERNAME_KEY, inputUsername.getText().toString());
                editor.putString(Constants.PASSWORD_KEY, inputPassword.getText().toString());
                editor.apply();
                showTodos();
                break;
            case R.id.btnShowData:
                inputUsername.setText(sharedPreferences.getString(Constants.USERNAME_KEY,""));
                inputPassword.setText(sharedPreferences.getString(Constants.PASSWORD_KEY,""));
                Toast.makeText(getApplicationContext(),"Data shown",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
