package com.ph25579.assignment.mainFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ph25579.assignment.MainActivity;
import com.ph25579.assignment.R;
import com.ph25579.assignment.activity.UpdatePasswordActivity;


public class AcountAdminFragment extends Fragment {
    private TextView tvUsername;
    private TextView tvRoute;
    private RelativeLayout btnUpdatePassword;
    public AcountAdminFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_acount_admin, container, false);
        initViews(v);
        initData();
        return v;
    }
    private void initViews(View v){
        tvUsername = v.findViewById(R.id.tv_username);
        tvRoute = v.findViewById(R.id.tv_route);
        btnUpdatePassword = v.findViewById(R.id.rl_choosetime_updatepassword);
    }
    private void initData(){
        MainActivity activity = (MainActivity) getActivity();
        tvUsername.setText(activity.userName);
        tvRoute.setText(activity.routeUser);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userId",activity.idUser);
                bundle.putString("userPassword",activity.userPassword);
                intent.putExtra("bundleUser",bundle);
                getActivity().startActivity(intent);
            }
        });
    }
}