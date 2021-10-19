package com.example.poetryapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapi.Api.ApiClient;
import com.example.poetryapi.Api.ApiInterface;
import com.example.poetryapi.Response.Deleteresponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {


    Toolbar toolbar;
    EditText poetName,poetryData;
    AppCompatButton submitBtn;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        initialization();
        setuptoolbar();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString=poetryData.getText().toString();
                String poetryNameString=poetName.getText().toString();

                if(poetryDataString.equals("")){
                    poetryData.setError("Field is empty");
                }
                else{
                   if(poetryNameString.equals("")){
                       poetName.setError("Field is empty");
                   }
                   else{
                       Toast.makeText(AddPoetry.this, "Calling Api", Toast.LENGTH_SHORT).show();
                       callapi(poetryDataString,poetryNameString);

                   }
                }
            }
        });
    }

    public void initialization(){
        toolbar=findViewById(R.id.add_poetry_toolbar);
        poetName=findViewById(R.id.add_poet_name_edittext);
       poetryData=findViewById(R.id.add_poetry_data_edittext);

       submitBtn=findViewById(R.id.submit_data_btn);

        Retrofit retrofit= ApiClient.getclient();
        apiInterface=retrofit.create(ApiInterface.class);

    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callapi(String poetryData,String poetName){
        apiInterface.addpoetry(poetryData,poetName).enqueue(new Callback<Deleteresponse>() {
            @Override
            public void onResponse(Call<Deleteresponse> call, Response<Deleteresponse> response) {
                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(AddPoetry.this, "Added Successully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(AddPoetry.this, "Not Added Successully", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    Log.e("failure",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<Deleteresponse> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }
}