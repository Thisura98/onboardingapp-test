package lk.test.myapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager singleton;
    private final String baseUrl = "https://myserver.com/api/"; // Temporary

    private LoginService loginService;

    public static NetworkManager getInstance()
    {
        if (NetworkManager.singleton == null)
            NetworkManager.singleton = new NetworkManager();

        return NetworkManager.singleton;
    }

    private NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        loginService = retrofit.create(LoginService.class);
    }

    public LoginService getLoginService()
    {
        return loginService;
    }

}
