package ubiquasif.uqac.betterwithstrangers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class GuestActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_switch:
                    Intent intent = new Intent(GuestActivity.this, HostActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profil:
                    mTextMessage.setText(R.string.title_profil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title bar (not mandatory)
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);
    }

}
