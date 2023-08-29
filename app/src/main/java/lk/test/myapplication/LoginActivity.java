package lk.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lk.test.myapplication.managers.ContextManager;
import lk.test.myapplication.managers.LoginManager;
import lk.test.myapplication.models.login.LoginResponse;
import lk.test.myapplication.models.login.LoginRequestBody;
import lk.test.myapplication.managers.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginManager loginManager;
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        loginManager = LoginManager.getInstance();

        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> login());
    }

    private void login(){
        loginManager.login(
            etUserName.getText().toString(),
            etPassword.getText().toString(),
            response -> handleLoginSuccessful(response),
            error -> handleLoginFailed(error));
    }

    private void handleLoginSuccessful(LoginResponse response) {
        if (response.success){
            Toast.makeText(this,"Login successful!",Toast.LENGTH_LONG).show();
            // TODO: Navigate to task list
        }
        else{
            handleLoginFailed("Login returned unsuccessful!");
        }
    }

    private void handleLoginFailed(String error){
        Toast.makeText(this,"Login failed due to network error",Toast.LENGTH_LONG).show();
    }
}