package com.example.arefin.dagger2loginlistapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arefin.dagger2loginlistapplication.sharedpref_injections.DaggerSharedPrefComponent;
import com.example.arefin.dagger2loginlistapplication.sharedpref_injections.SharedPrefComponent;
import com.example.arefin.dagger2loginlistapplication.sharedpref_injections.SharedPreferenceModule;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputUsername, inputPassword;
    Button btnLogin;

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
        setContentView(R.layout.activity_login);
        initViews();
        initDependencyInjections();
    }

    private void showTodos()
    {
        Intent intent = new Intent(this, TodoListActivity.class);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogin:
                sharedPrefEditor.putString(Constants.USERNAME_KEY, inputUsername.getText().toString());
                sharedPrefEditor.putString(Constants.PASSWORD_KEY, inputPassword.getText().toString());
                sharedPrefEditor.apply();
                showTodos();
                break;
        }
    }
}
