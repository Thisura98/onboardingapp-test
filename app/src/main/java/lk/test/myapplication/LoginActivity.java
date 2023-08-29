package lk.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lk.test.myapplication.model.LoginResponse;
import lk.test.myapplication.network.LoginRequestBody;
import lk.test.myapplication.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> login());
    }

    private void login()
    {
        LoginRequestBody requestBody = new LoginRequestBody(etUserName.getText().toString(), etPassword.getText().toString());

        NetworkManager.getInstance().getLoginService()
            .login(requestBody)
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    handleLoginSuccessful(response);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    handleLoginFailed();
                }
            });
    }

    private void handleLoginSuccessful(Response<LoginResponse> response)
    {
        if (response.body().success)
        {
            Toast.makeText(this,"Login successful!",Toast.LENGTH_LONG).show();
            // TODO: Navigate to task list
        }
        else
        {
            handleLoginFailed();
        }
    }

    private void handleLoginFailed()
    {
        Toast.makeText(this,"Login failed due to network error",Toast.LENGTH_LONG).show();
    }
}