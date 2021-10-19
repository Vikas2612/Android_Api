package com.example.poetryapi.Adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapi.Api.ApiClient;
import com.example.poetryapi.Api.ApiInterface;
import com.example.poetryapi.MainActivity;
import com.example.poetryapi.Models.PoetryModel;
import com.example.poetryapi.R;
import com.example.poetryapi.Response.Deleteresponse;
import com.example.poetryapi.UpdatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends  RecyclerView.Adapter<PoetryAdapter.ViewHolder>{

    Context context;
    List<PoetryModel> poetryModels;
    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        Retrofit retrofit= ApiClient.getclient();
        apiInterface=retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(context).inflate(R.layout.poetry_list_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.poetname.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.date_time.setText(poetryModels.get(position).getDate_time());

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletepoetry(poetryModels.get(position).getId()+"",position);
            }
        });

        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,poetryModels.get(position).getId()+"\n"+poetryModels.get(position).getPoetry_data(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id",poetryModels.get(position).getId());
                intent.putExtra("p_data",poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView poetname,poetry,date_time;
        AppCompatButton updatebtn,deletebtn;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);



            poetname=itemView.findViewById(R.id.textview_poetname);
            poetry=itemView.findViewById(R.id.textview_poetrydata);
            date_time=itemView.findViewById(R.id.textview_poetrydateandtime);

           updatebtn=itemView.findViewById(R.id.update_btn);
           deletebtn=itemView.findViewById(R.id.delete_btn);
        }
    }

    private void deletepoetry(String id,int pose){
        apiInterface.deletepoetry(id).enqueue(new Callback<Deleteresponse>() {
            @Override
            public void onResponse(Call<Deleteresponse> call, Response<Deleteresponse> response) {
                try{

                    if(response!=null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if(response.body().getStatus().equals("1")){
                            poetryModels.remove(pose);
                            notifyDataSetChanged();
                        }
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
