package ubiquasif.uqac.betterwithstrangers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import ubiquasif.uqac.betterwithstrangers.Fragments.NotificationFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.ProfileFragment;
import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class GuestActivity extends AppCompatActivity
        implements ProfileFragment.OnFragmentInteractionListener, NotificationFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView navigationView;

    private Fragment profilFragment;
    private Fragment notificationFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, notificationFragment).commit();
                    return true;
                case R.id.navigation_switch:
                    Intent intent = new Intent(GuestActivity.this, HostActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profil:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, profilFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guest);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);

        profilFragment = ProfileFragment.newInstance(null, null);

        notificationFragment = NotificationFragment.newInstance(null, null);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.println(Log.DEBUG, "debug", "Fragment interaction detected");
    }
}
