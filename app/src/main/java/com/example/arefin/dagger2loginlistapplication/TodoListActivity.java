package com.example.arefin.dagger2loginlistapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arefin.dagger2loginlistapplication.models.Todo;
import com.example.arefin.dagger2loginlistapplication.network.APIService;
import com.example.arefin.dagger2loginlistapplication.network.injections.DaggerRetrofitNetworkComponent;
import com.example.arefin.dagger2loginlistapplication.network.injections.RetrofitNetworkComponent;
import com.example.arefin.dagger2loginlistapplication.network.injections.RetrofitNetworkModule;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoListActivity extends AppCompatActivity {

    @Inject
    APIService apiService;

    //the component which will help dagger injection
    private RetrofitNetworkComponent retrofitNetworkComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_lists);
        initDependencyInjections();
        Observable<List<Todo>> allTodosObservable = apiService.getAllTodos();
        allTodosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNextListener,this::onErrorListener, this::onCompleteListener);
    }

    private void onCompleteListener() {
        Toast.makeText(this, "Api call complete",
                Toast.LENGTH_LONG).show();
    }

    private void initDependencyInjections() {
        //the DaggerSharedPrefComponent name is generated after a rebuild
        retrofitNetworkComponent = DaggerRetrofitNetworkComponent
                .builder()
                //the number of modules declared in the pref module, all can be here to use
                .retrofitNetworkModule(new RetrofitNetworkModule())
                .build();
        retrofitNetworkComponent.inject(this);
    }
    private void onNextListener(List<Todo> todos) {
        if (todos != null && todos.size() != 0) {
            String titles = "";
            for (Todo todo: todos){
                titles = titles + " " + todo.getTitle() + "\n";
            }
            ((TextView)findViewById(R.id.tvUp)).setText(titles);
            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "NO RESULTS FOUND", Toast.LENGTH_LONG).show();

    }

    private void onErrorListener(Throwable t) {

        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }

    //NON reactx listener
    //        call.enqueue(new Callback<List<Todo>>() {
//            @Override
//            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
//                if(response.isSuccessful()) {
//                    List<Todo> todoList = response.body();
//                    String titles = "";
//                    for (Todo todo: todoList){
//                        titles = titles + " " + todo.getTitle() + "\n";
//                    }
//                    ((TextView)findViewById(R.id.tvUp)).setText(titles);
//                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
////                    changesList.forEach(change -> System.out.println(change.subject));
//                } else {
//                    System.out.println(response.errorBody());
//                    try {
//                        Toast.makeText(getApplicationContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Todo>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
//                t.printStackTrace();
//            }
//        });
}
