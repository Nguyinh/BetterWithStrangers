package ubiquasif.uqac.betterwithstrangers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;


import ubiquasif.uqac.betterwithstrangers.Fragments.CreateEventFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.DashboardFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.NotificationFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.ProfileFragment;
import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class HostActivity extends AppCompatActivity
        implements CreateEventFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        NotificationFragment.OnFragmentInteractionListener,
        DashboardFragment.OnFragmentInteractionListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Fragment createEventFragment;
    private Fragment profilFragment;
    private Fragment notificationFragment;
    private Fragment dashboardFragment;

    /**
     * To receive a callback when the user sets the date.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        ((CreateEventFragment)createEventFragment).updateDate(year, month, day);
    }

    /**
     * To receive a callback when the user sets the time.
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ((CreateEventFragment)createEventFragment).updateTime(hourOfDay, minute);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_host:
                    setTitle("Créer un événement");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, createEventFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("Dashboard");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, dashboardFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    setTitle("Notifications");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, notificationFragment).commit();
                    return true;
                case R.id.navigation_switch:
                    Intent intent = new Intent(HostActivity.this, GuestActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profil:
                    setTitle("Profil");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, profilFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);

        profilFragment = ProfileFragment.newInstance(null, null);
        notificationFragment = NotificationFragment.newInstance(null, null);
        dashboardFragment = DashboardFragment.newInstance(null, null);

        createEventFragment = CreateEventFragment.newInstance();

        // Affichage de la création de soirée directement après le switch
        setTitle("Créer un événement");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, createEventFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.println(Log.DEBUG, "debug", "Fragment interaction detected");
    }

}
