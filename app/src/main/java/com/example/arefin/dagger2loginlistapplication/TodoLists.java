package com.example.arefin.dagger2loginlistapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arefin.dagger2loginlistapplication.models.Todo;
import com.example.arefin.dagger2loginlistapplication.network.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.example.arefin.dagger2loginlistapplication.Constants.BASE_URL;

public class TodoLists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_lists);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIService apiService = retrofit.create(APIService.class);

        Call<List<Todo>> call = apiService.getAllTodos();
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if(response.isSuccessful()) {
                    List<Todo> todoList = response.body();
                    String titles = "";
                    for (Todo todo: todoList){
                        titles = titles + " " + todo.getTitle() + "\n";
                    }
                    ((TextView)findViewById(R.id.tvUp)).setText(titles);
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
//                    changesList.forEach(change -> System.out.println(change.subject));
                } else {
                    System.out.println(response.errorBody());
                    try {
                        Toast.makeText(getApplicationContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
