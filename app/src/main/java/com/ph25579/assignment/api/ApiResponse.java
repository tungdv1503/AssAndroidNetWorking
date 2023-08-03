package com.ph25579.assignment.api;

import com.google.gson.annotations.SerializedName;
import com.ph25579.assignment.model.User;

public class ApiResponse {
    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    private boolean exists; // Thêm trường này để chứa kết quả kiểm tra username tồn tại hay không

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    // Getters and setters (hoặc tùy chỉnh theo phản hồi từ API)
    public boolean exists() {
        return exists;
    }

}
