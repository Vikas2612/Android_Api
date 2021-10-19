package com.example.poetryapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

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

public class UpdatePoetry extends AppCompatActivity {
    Toolbar toolbar;
    EditText updatepoetrydata;
    AppCompatButton updatethetbtn;
    int poetryId;
    String poetryDataString;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);
        initialization();
        setuptoolbar();


        updatethetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String p_data=updatepoetrydata.getText().toString();
               if(p_data.equals(""))
               {
                   updatepoetrydata.setError("Field is empty");
               }
               else
               {
                   callapi(p_data,poetryId+"");
               }
            }
        });
    }

    private void initialization(){
        toolbar=findViewById(R.id.update_poetry_toolbar);
        updatepoetrydata=findViewById(R.id.update_poetry_data_edittext);
        updatethetbtn=findViewById(R.id.update_submit_data_btn);

        poetryId=getIntent().getIntExtra("p_id",0);
        poetryDataString=getIntent().getStringExtra("p_data");

        updatepoetrydata.setText(poetryDataString);

        Retrofit retrofit= ApiClient.getclient();
        apiInterface= retrofit.create(ApiInterface.class);

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

  private void callapi(String pdata,String pid){

        apiInterface.updatepoetry(pdata,pid).enqueue(new Callback<Deleteresponse>() {
            @Override
            public void onResponse(Call<Deleteresponse> call, Response<Deleteresponse> response) {
                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(UpdatePoetry.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(UpdatePoetry.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.e("updateexp",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<Deleteresponse> call, Throwable t) {
                Log.e("update failed",t.getLocalizedMessage());
            }
        });
  }


}