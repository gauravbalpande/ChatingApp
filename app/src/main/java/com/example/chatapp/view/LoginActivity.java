package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityLoginBinding;
import com.example.chatapp.viewModel.MyViewModel;

public class LoginActivity extends AppCompatActivity {
    MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);
        ActivityLoginBinding activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setVModel(myViewModel);
    }
}
