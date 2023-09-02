package lk.test.myapplication.managers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.function.Consumer;

import lk.test.myapplication.retrofit.login.LoginRequestBody;
import lk.test.myapplication.retrofit.login.LoginResponse;
import lk.test.myapplication.retrofit.login.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginManager {

    private static LoginManager singleton;
    private LoginService loginService;
    private final String loginStateFile = "loginstate";
    private final String isLoggedInKey = "logged_in";
    private final String usernameKey = "username";

    public static LoginManager getInstance() {
        if (singleton == null)
            singleton = new LoginManager();

        return singleton;
    }

    private LoginManager() {
        loginService = NetworkManager.getInstance().createService(LoginService.class);
    }

    public Boolean validateCredentials(String username, String password) {
        if (username == null || username.length() == 0)
            return false;

        if (password == null || password.length() == 0)
            return false;

        return true;
    }

    public void login(
        String username,
        String password,
        Consumer<LoginResponse> onSuccess,
        Consumer<String> onError) {

        if (!NetworkManager.getInstance().isNetworkAvailable())
            return;

        LoginRequestBody body = new LoginRequestBody(username, password);
        loginService.login(body)
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().success){
                        setLoggedInState(true, username);
                        onSuccess.accept(response.body());
                    }
                    else{
                        onError.accept("Login returned unsuccessful");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    onError.accept("An error while trying to connect to the internet");
                }
            });
    }

    public void setLoggedInState(boolean isLoggedIn, String username){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isLoggedInKey, isLoggedIn);
        editor.putString(usernameKey, username);
        editor.apply();
    }

    public boolean getIsLoggedIn(){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE);
        return prefs.getBoolean(isLoggedInKey, false);
    }

    public String getLoggedInUsername(){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE);
        return prefs.getString(usernameKey, null);
    }

    public void logout(){
        setLoggedInState(false, null);
    }
}
