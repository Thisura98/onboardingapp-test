package lk.test.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import lk.test.myapplication.managers.DatabaseManager;
import lk.test.myapplication.managers.LoginManager;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvAppVersion;
    private TextView tvUsername;
    private LoginManager loginManager;
    private DatabaseManager databaseManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_about);
    }
}
