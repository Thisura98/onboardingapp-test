package lk.test.myapplication.managers;

import java.util.function.Consumer;

import lk.test.myapplication.models.login.LoginRequestBody;
import lk.test.myapplication.models.login.LoginResponse;
import lk.test.myapplication.models.login.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginManager {

    private static LoginManager singleton;

    private LoginService loginService;

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

        if (!NetworkManager.getInstance().checkNetworkAvailability())
            return;

        LoginRequestBody body = new LoginRequestBody(username, password);
        loginService.login(body)
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    onSuccess.accept(response.body());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    onError.accept("An error while trying to connect to the internet");
                }
            });
    }
}
