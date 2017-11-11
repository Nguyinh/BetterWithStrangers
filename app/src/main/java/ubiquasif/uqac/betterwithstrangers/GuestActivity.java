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
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Iterator;

import ubiquasif.uqac.betterwithstrangers.Fragments.NotificationFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.ProfilFragment;
import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class GuestActivity extends AppCompatActivity
    implements ProfilFragment.OnFragmentInteractionListener, NotificationFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;

    private BottomNavigationView navigationView;

    private Fragment profilFragment;
    private Fragment notificationFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mTextMessage.setVisibility(View.VISIBLE);

            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.GONE);

            switch (item.getItemId()) {
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);

                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, notificationFragment).commit();
                    return true;
                case R.id.navigation_switch:
                    Intent intent = new Intent(GuestActivity.this, HostActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profil:
                    mTextMessage.setVisibility(View.GONE);
                    signOutButton.setVisibility(View.VISIBLE);
                    mTextMessage.setText(R.string.title_profil);

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

        // Set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_guest);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);

        profilFragment = ProfilFragment.newInstance(null,null);

        notificationFragment = NotificationFragment.newInstance(null,null);
    }

    public void onSignOutClick(View v) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(GuestActivity.this, FirstConnectionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.println(Log.DEBUG,"debug", "Fragment interaction detected");
    }
}
