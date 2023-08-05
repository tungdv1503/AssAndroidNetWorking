package com.ph25579.assignment.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ph25579.assignment.MainActivity;
import com.ph25579.assignment.R;
import com.ph25579.assignment.api.ApiResponse;
import com.ph25579.assignment.api.InterfaceInsertUser;
import com.ph25579.assignment.api.RetrofitClientUser;
import com.ph25579.assignment.model.User;
import com.ph25579.assignment.utils.EmailValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {
    private ImageView btn_back;
    private Button btnUpdate;
    private EditText edtPassword, edtConfirmPassword,edtPasswordCurrent;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initViews();
        initData();
    }

    private void initViews() {
        btn_back = findViewById(R.id.img_back);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirmpassword);
        edtPasswordCurrent = findViewById(R.id.edt_password_current);
        btnUpdate = findViewById(R.id.btn_update);
        AlertDialog(UpdatePasswordActivity.this);
    }

    private void initData() {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("bundleUser");
//
//        int idUser = bundle.getInt("userId");
//        String userPassword = bundle.getString("userPassword");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UsersAccount", Context.MODE_PRIVATE);
                int idUser = sharedPreferences.getInt("userId", 0);
                String userName = sharedPreferences.getString("userName", "");
                String userPassword = sharedPreferences.getString("userPassword", "");
                String password = edtPassword.getText().toString().trim();
                String comfirmpassword = edtConfirmPassword.getText().toString().trim();
                String currentpassword = edtPasswordCurrent.getText().toString().trim();
                if (password.equals("") || comfirmpassword.equals("")||currentpassword.equals("")) {
                    Toast.makeText(UpdatePasswordActivity.this, "Không được bỏ trống các dòng thông tin", Toast.LENGTH_SHORT).show();
                }else if(!currentpassword.equals(userPassword)){
                    Toast.makeText(UpdatePasswordActivity.this, "Password hiện tại không đúng", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(comfirmpassword)) {
                    Toast.makeText(UpdatePasswordActivity.this, "Password phải trùng với confirmpassword", Toast.LENGTH_SHORT).show();
                }  else {
                    updatePassword(idUser, password);
                }
            }
        });
    }
    private void updatePassword(int id, String password) {
        dialog.show();
        InterfaceInsertUser apiService = RetrofitClientUser.getRetrofitInstance().create(InterfaceInsertUser.class);
        Call<ApiResponse> call = apiService.updatePassword(String.valueOf(id), password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (response.isSuccessful()&&apiResponse.getSuccess()==1) {
                    dialog.dismiss();
                    SharedPreferences sharedPreferences = getSharedPreferences("UsersAccount", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userPassword", password);
                    editor.apply();
                    Toast.makeText(UpdatePasswordActivity.this, "" + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(UpdatePasswordActivity.this, "" + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(UpdatePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
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