package com.example.arefin.dagger2loginlistapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arefin.dagger2loginlistapplication.models.Todo;
import com.example.arefin.dagger2loginlistapplication.network.APIService;
import com.example.arefin.dagger2loginlistapplication.network.injections.DaggerRetrofitNetworkComponent;
import com.example.arefin.dagger2loginlistapplication.network.injections.RetrofitNetworkComponent;
import com.example.arefin.dagger2loginlistapplication.network.injections.RetrofitNetworkModule;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TodoDetailsActivity extends AppCompatActivity {

    TextView user, title;
    ProgressBar progressBar;
    int todoId;
    @Inject
    APIService apiService;
    //the component which will help dagger injection
    private RetrofitNetworkComponent retrofitNetworkComponent;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);
        user = findViewById(R.id.tvDetailsUser);
        title = findViewById(R.id.tvDetailsTitle);
        progressBar = findViewById(R.id.progressBar);
        disposables = new CompositeDisposable();
        initInjections();
        todoId = getIntent().getIntExtra(Constants.TODO_ID,-1);
        if(todoId != -1){
            makeAPiCall();
        }
    }

    private void initInjections() {
        retrofitNetworkComponent = DaggerRetrofitNetworkComponent
                .builder()
                //the number of modules declared in the pref module, all can be here to use
                .retrofitNetworkModule(new RetrofitNetworkModule())
                .build();
        retrofitNetworkComponent.doInjections(this);
    }

    private void makeAPiCall() {
//        apiService.getTodoDetails(todoId);
        apiService.getTodoDetails(todoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Todo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressBar.setVisibility(View.VISIBLE);
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Todo todo) {
                        user.setText(todo.getUserId().toString());
                        title.setText(todo.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "some error occurred", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    //NON reactx listener
//    Call getTodoDetailsRequest =
//            call.enqueue(new Callback<List<Todo>>() {
//                @Override
//                public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
//                    if(response.isSuccessful()) {
//                        List<Todo> todoList = response.body();
//                        String titles = "";
//                        for (Todo todo: todoList){
//                            titles = titles + " " + todo.getTitle() + "\n";
//                        }
//                        ((TextView)findViewById(R.id.tvUp)).setText(titles);
//                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
////                    changesList.forEach(change -> System.out.println(change.subject));
//                    } else {
//                        System.out.println(response.errorBody());
//                        try {
//                            Toast.makeText(getApplicationContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Todo>> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                }
//            });

}
