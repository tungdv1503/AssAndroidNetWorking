package com.ph25579.assignment.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceUserLogin {
    @FormUrlEncoded
    @POST("loginUser.php") // Đường dẫn API hoặc máy chủ xử lý đăng nhập
    Call<ApiResponse> loginUser(
            @Field("Username") String username,
            @Field("Password") String password
    );
}
