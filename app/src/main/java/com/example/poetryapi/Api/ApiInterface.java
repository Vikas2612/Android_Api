package com.example.poetryapi.Api;

import com.example.poetryapi.Response.Deleteresponse;
import com.example.poetryapi.Response.Getpoetryresponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("readtable.php")
    Call<Getpoetryresponse> getpoetry();

    @FormUrlEncoded
    @POST("deleteapi.php")
    Call<Deleteresponse> deletepoetry(@Field("poetry_id")String poetry_id);

    @FormUrlEncoded
    @POST("addpoetry.php")
    Call<Deleteresponse> addpoetry(@Field("poetry")String poetryData,@Field("poet_name")String poet_name);

    @FormUrlEncoded
    @POST("update.php")
    Call<Deleteresponse> updatepoetry(@Field("enterpoet_name")String poetryData,@Field("enterid")String id);

}
