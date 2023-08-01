package com.ph25579.assignment.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ph25579.assignment.MainActivity;
import com.ph25579.assignment.R;
import com.ph25579.assignment.api.ApiResponse;
import com.ph25579.assignment.api.InterfaceUserLogin;
import com.ph25579.assignment.api.RetrofitClientUser;
import com.ph25579.assignment.model.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TextView tvSignUp;
    private TextInputLayout tipUsername, tipPassword;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initViews();
        initData();
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_signup);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        tipUsername = findViewById(R.id.tip_username);
        tipPassword = findViewById(R.id.tip_password);
    }

    private void initData() {
        AlertDialog(LoginUserActivity.this);
        tvSignUp.setTypeface(Typeface.DEFAULT_BOLD);
// Đặt kích thước chữ (nếu cần thiết)
        tvSignUp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
// Đặt màu chữ (nếu cần thiết)
        tvSignUp.setTextColor(ContextCompat.getColor(this, android.R.color.white));
// Đặt hiệu ứng gạch chân cho chữ
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        AnimationTextInputLayout(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim().toLowerCase();
                String password = edtPassword.getText().toString().trim();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginUserActivity.this, "Không được bỏ trống các dòng thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(username, password);
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
            }
        });
    }

    private void loginUser(String username, String password) {
        dialog.show();
        UserLogin user = new UserLogin(username, password);
        InterfaceUserLogin apiService = RetrofitClientUser.getRetrofitInstance().create(InterfaceUserLogin.class);
        Call<ApiResponse> call = apiService.loginUser(user.getUsername(), user.getPassword());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getSuccess() == 1) {
                        dialog.dismiss();
                        // Đăng nhập thành công, thực hiện hành động sau khi đăng nhập thành công
                        startActivity(new Intent(LoginUserActivity.this, MainActivity.class));
                        Toast.makeText(LoginUserActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        // Đăng nhập thất bại, hiển thị thông báo lỗi tương ứng
                        Toast.makeText(LoginUserActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    // Xử lý khi nhận được phản hồi không thành công từ API
                    Toast.makeText(LoginUserActivity.this, "Lỗi khi gửi yêu cầu đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Xử lý khi gọi API bị lỗi
                dialog.dismiss();
                Toast.makeText(LoginUserActivity.this, "Lỗi kết nối đến máy chủ!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnimationTextInputLayout(boolean a) {
        tipUsername.setHintAnimationEnabled(a);
        tipPassword.setHintAnimationEnabled(a);
        tipUsername.setHintEnabled(a);
        tipPassword.setHintEnabled(a);
    }

    private void AlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_progress_dialog, null);
        // Set the custom view to the AlertDialog
        builder.setView(view);
        // Create the AlertDialog
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        // Set the AlertDialog not cancelable with back button or touch outside
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
}