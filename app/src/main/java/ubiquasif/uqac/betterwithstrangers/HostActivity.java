package ubiquasif.uqac.betterwithstrangers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ubiquasif.uqac.betterwithstrangers.Fragments.CreateEventFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.NotificationFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.ProfileFragment;
import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class HostActivity extends AppCompatActivity
        implements CreateEventFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        NotificationFragment.OnFragmentInteractionListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{


    private Toolbar toolbar;

    private Fragment createEventFragment;
    private Fragment profilFragment;
    private Fragment notificationFragment;

    /**
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        ((CreateEventFragment)createEventFragment).setDateTest(calendar);
    }

    /**
     * To set time on TextView
     * @param time
     */
    private void setTime(final String time) {
        ((CreateEventFragment)createEventFragment).setTimeTest(time);

    }


    /**
     * To receive a callback when the user sets the date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    /**
     * To receive a callback when the user sets the time.
     * @param view
     * @param hourOfDay
     * @param minute
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = "Heure choisie : "+ String.valueOf(hourOfDay) + "h" + String.valueOf(minute) + "\n";
        setTime(time);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_host:
                    toolbar.setTitle("Créer un événement");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, createEventFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    toolbar.setTitle("Dashboard");
                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle("Notifications");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, notificationFragment).commit();
                    return true;
                case R.id.navigation_switch:
                    Intent intent = new Intent(HostActivity.this, GuestActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profil:
                    toolbar.setTitle("Profil");
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

        // Set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_host);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Créer un événement");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        setToolbar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);

        profilFragment = ProfileFragment.newInstance(null, null);
        notificationFragment = NotificationFragment.newInstance(null, null);
        createEventFragment = CreateEventFragment.newInstance();
        //affichage de la création de soirée directement après le switch
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, createEventFragment).commit();


    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.println(Log.DEBUG,"debug", "Fragment interaction detected");
    }

}
