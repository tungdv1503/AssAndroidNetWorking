package com.ph25579.assignment.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
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
import com.ph25579.assignment.R;
import com.ph25579.assignment.api.ApiResponse;
import com.ph25579.assignment.api.InterfaceInsertUser;
import com.ph25579.assignment.api.RetrofitClientUser;
import com.ph25579.assignment.model.User;
import com.ph25579.assignment.utils.EmailValidator;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {
    private Button btnSignUp;
    private EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;
    private TextView tvLogin;
    private TextInputLayout tipUsername, tipEmail, tipPassword, tipConfirmPassword;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        initViews();
        initData();
    }

    private void initViews() {
        btnSignUp = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login);
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_username);
        tipEmail = findViewById(R.id.tip_email);
        tipUsername = findViewById(R.id.tip_username);
        tipPassword = findViewById(R.id.tip_password);
        tipConfirmPassword = findViewById(R.id.tip_comfirm_password);

    }

    private void initData() {
        AlertDialog(RegisterUserActivity.this);
        tvLogin.setTypeface(Typeface.DEFAULT_BOLD);
// Đặt kích thước chữ (nếu cần thiết)
        tvLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
// Đặt màu chữ (nếu cần thiết)
        tvLogin.setTextColor(ContextCompat.getColor(this, android.R.color.white));
// Đặt hiệu ứng gạch chân cho chữ
        tvLogin.setPaintFlags(tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        AnimationTextInputLayout(false);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim().toLowerCase();
                String email = edtEmail.getText().toString().trim().toLowerCase();
                String password = edtPassword.getText().toString().trim();
                String comfirmpassword = edtConfirmPassword.getText().toString().trim();
                if (username.equals("") || email.equals("") || password.equals("") || comfirmpassword.equals("")) {
                    Toast.makeText(RegisterUserActivity.this, "Không được bỏ trống các dòng thông tin", Toast.LENGTH_SHORT).show();
                } else if (EmailValidator.isValidEmail(email) == false) {
                    Toast.makeText(RegisterUserActivity.this, "email không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (password.equals(comfirmpassword)) {
                    Toast.makeText(RegisterUserActivity.this, "Password phải trùng với confirmpassword", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(RegisterUserActivity.this, "Password phải tối thiểu là 6 kí tự", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username, password, email);
                    checkUser(user);
                }
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterUserActivity.this, LoginUserActivity.class));
            }
        });
    }

    private void registerUser(User user) {
        dialog.show();
        InterfaceInsertUser apiService = RetrofitClientUser.getRetrofitInstance().create(InterfaceInsertUser.class);
        Call<ApiResponse> call = apiService.insertUser(user.getUsername(), user.getPassword(), user.getEmail());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(RegisterUserActivity.this, "true" + response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(RegisterUserActivity.this, "false" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(RegisterUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void checkUser(User user) {
        dialog.show();
        InterfaceInsertUser apiService = RetrofitClientUser.getRetrofitInstance().create(InterfaceInsertUser.class);
        Call<ApiResponse> checkCall = apiService.checkUsernameExists(user.getUsername());
        checkCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.exists()) {
                        dialog.dismiss();
                        // Username đã tồn tại, hiển thị thông báo tương ứng
                        Toast.makeText(RegisterUserActivity.this, "Username đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        // Username không tồn tại, thực hiện truy vấn INSERT
                        registerUser(user);
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(RegisterUserActivity.this, "Lỗi kiểm tra username!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(RegisterUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnimationTextInputLayout(boolean a) {
        tipUsername.setHintAnimationEnabled(a);
        tipEmail.setHintAnimationEnabled(a);
        tipPassword.setHintAnimationEnabled(a);
        tipConfirmPassword.setHintAnimationEnabled(a);
        tipUsername.setHintEnabled(a);
        tipEmail.setHintEnabled(a);
        tipPassword.setHintEnabled(a);
        tipConfirmPassword.setHintEnabled(a);
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