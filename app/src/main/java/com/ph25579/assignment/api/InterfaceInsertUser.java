package com.ph25579.assignment.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceInsertUser {
    @FormUrlEncoded
    @POST("insertUser.php")
    Call<ApiResponse> insertUser (
            @Field("Username") String username,
            @Field("Password") String password,
            @Field("Email") String email
    );
    @FormUrlEncoded
    @POST("checkUsernameExists.php")
    Call<ApiResponse> checkUsernameExists(
            @Field("Username") String username
    );
}
