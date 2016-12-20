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
    Button btn_login;
    String email,pass,emailAPI,passAPI;
    EditText email_ET, pass_ET;
    ProgressBar progressBar;
    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://private-7bb04d-signandlogin.apiary-mock.com/users/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    UserApi userApi = retrofit.create(UserApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_ET = (EditText)findViewById(R.id.email_ET);
        pass_ET = (EditText)findViewById(R.id.pass_ET);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email_ET.length() == 0) {
                    Toast toast = Toast.makeText(MainActivity.this, getString(R.string.error_email), Toast.LENGTH_SHORT);
                    toast.show();
                } else if (pass_ET.length() == 0) {
                    Toast toast = Toast.makeText(MainActivity.this, getString(R.string.error_pass), Toast.LENGTH_SHORT);
                    toast.show();;
                } else {
                    login();
                }
            }
        });
    }
    public void login(){
        email = email_ET.getText().toString();
        pass = pass_ET.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-80e9a-android23.apiary-mock.com/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi user_api = retrofit.create(UserApi.class);

        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

               boolean login = false;
                        for (Users.UserItem user: response.body().getUsers()){
                            emailAPI = user.getEmail();
                            passAPI = user.getPassword();
//                                emailAPI ="a";
 //                               passAPI = "a";
                            if(email.equals(emailAPI)&& pass.equals(passAPI)){
                                login = true;
                            }
                       }
                        if(login){
                            HomeActivity.prefs.edit().putBoolean("auth", false).commit();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }else{
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