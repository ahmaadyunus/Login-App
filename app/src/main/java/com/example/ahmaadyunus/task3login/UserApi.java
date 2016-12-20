package com.example.ahmaadyunus.task3login;

/**
 * Created by ahmaadyunus on 19/12/16.
 */

import retrofit2.http.GET;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.POST;
        import retrofit2.http.PUT;
        import retrofit2.http.Path;


public interface UserApi {

    @GET("https://private-7bb04d-signandlogin.apiary-mock.com/users")
    Call<Users> getUsers();

    @GET("https://private-7bb04d-signandlogin.apiary-mock.com/users/{id}")
    Call<User> getUser(@Path("id") String user_id);

    @PUT("https://private-7bb04d-signandlogin.apiary-mock.com/users/{id}")
    Call<User> updateUser(@Path("id") int user_id, @Body User user);

    @POST("https://private-7bb04d-signandlogin.apiary-mock.com/users")
    Call<User> saveUser(@Body User user);

    @DELETE("https://private-7bb04d-signandlogin.apiary-mock.com/users")
    Call<User> deleteUser(@Path("id") String user_id);

}
