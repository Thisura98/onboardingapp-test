package lk.test.myapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import lk.test.myapplication.managers.DatabaseManager;
import lk.test.myapplication.managers.LoginManager;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvAppVersion;
    private TextView tvUsername;
    private Button btnLogout;
    private LoginManager loginManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = findViewById(R.id.toolbar);
        tvAppVersion = findViewById(R.id.tvAppVersion);
        tvUsername = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginManager = LoginManager.getInstance();
        databaseManager = DatabaseManager.getInstance();

        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void loadData(){
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            tvAppVersion.setText(String.format("Application version: %s", pInfo.versionName));
            tvUsername.setText(String.format("Username: %s", loginManager.getLoggedInUsername()));
            btnLogout.setOnClickListener(this::confirmLogout);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmLogout(View view){
        new AlertDialog.Builder(this)
            .setTitle("Logout confirmation")
            .setMessage("Are you sure you want to logout?")
            .setNegativeButton("NO", null)
            .setPositiveButton("YES", (d, i) -> performLogout())
            .show();
    }

    private void performLogout(){
        databaseManager.clearDatabase(
            () -> {
                loginManager.logout();
                restartApp();
            },
            this::showLogoutError
        );
    }

    private void showLogoutError(String error){
        new AlertDialog.Builder(this)
            .setTitle("Logout Error")
            .setMessage(error)
            .setPositiveButton("OK", null)
            .show();
    }

    private void restartApp(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
