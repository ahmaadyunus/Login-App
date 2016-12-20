package com.example.ahmaadyunus.task3login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText tv_email, tv_password;
    Button btn_login;

    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl("https://private-7bb04d-signandlogin.apiary-mock.com/users/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build();
    UserApi user_api = retrofit.create(UserApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_email = (EditText)findViewById(R.id.email_ET);
        tv_password = (EditText)findViewById(R.id.pass_ET);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_email.getText().toString().trim().length() == 0) {
                    tv_email.setError(getString(R.string.error_email));
                } else if (tv_password.getText().toString().trim().length() == 0) {
                    tv_password.setError(getString(R.string.error_pass));
                } else {
                    login();
                }
            }
        });
    }


    public void login(){
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                boolean login = false;
                String email = tv_email.getText().toString();
                String password = tv_password.getText().toString();

                for(Users.UserItem user : response.body().getUsers()) {
                    String getEmail = user.getEmail();
                    String getPassword = user.getPassword();
                    if (email.equals(getEmail) && password.equals(getPassword)) {
                        login = true;
                    }
                }

                if (login == true) {
                    HomeActivity.prefs.edit().putBoolean("auth", false).commit();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, getString(R.string.error_login), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast toast = Toast.makeText(MainActivity.this, getString(R.string.error_connect_api), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}

