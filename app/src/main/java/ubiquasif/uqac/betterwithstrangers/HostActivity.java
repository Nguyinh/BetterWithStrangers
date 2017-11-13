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
import android.view.View;
import android.widget.TextView;

import ubiquasif.uqac.betterwithstrangers.Fragments.CreateEventFragment;
import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class HostActivity extends AppCompatActivity
        implements CreateEventFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;

    private Fragment createEventFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_host:
                    mTextMessage.setText(R.string.title_host);
                    mTextMessage.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, createEventFragment).commit();

                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_switch:
                    Intent intent = new Intent(HostActivity.this, GuestActivity.class);
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

        setContentView(R.layout.activity_host);

        mTextMessage = findViewById(R.id.message);

        // Set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_host);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);

        createEventFragment = CreateEventFragment.newInstance(null,null);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.println(Log.DEBUG,"debug", "Fragment interaction detected");
    }

}
