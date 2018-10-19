package example.com.primaryschoolconnectedsystemapp.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import example.com.primaryschoolconnectedsystemapp.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init() {
        findViewById(R.id.container).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startMainActivity();
                    }
                }
        );

        TextView start_app = (TextView) findViewById(R.id.start_app);
        start_app.setText(getResources().getString(R.string.app_name));

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.hide();
        }

        getWindow().getDecorView().setFitsSystemWindows(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void startMainActivity() {
        Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(i);
        // close this activity
        finish();
    }
}
