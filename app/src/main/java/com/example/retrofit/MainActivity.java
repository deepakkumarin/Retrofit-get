package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    TextView tx;
    String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx=findViewById(R.id.text1);

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.getPosts().enqueue(new Callback<List<PostPojo>>() {
            @Override
            public void onResponse(Call<List<PostPojo>> call, Response<List<PostPojo>> response) {
                if (!response.isSuccessful()){
                    String res = "Code"+response.code();
                    tx.setText(res);
                }
                List<PostPojo> models = response.body();
                for (PostPojo model:models){
                    content += "ID:"+model.getId()+"\n";
                    content += "User ID:"+model.getUserId()+"\n";
                    content += "Title:"+model.getTitle()+"\n";
                    content += "Text:"+model.getBody()+"\n\n\n\n\n\n";
                    tx.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<PostPojo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}