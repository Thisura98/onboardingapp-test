package lk.test.myapplication.network;

import lk.test.myapplication.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface LoginService {
    @POST("login")
    Call<LoginResponse> login(
        @Body LoginRequestBody request);
}
