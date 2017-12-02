package ubiquasif.uqac.betterwithstrangers;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import ubiquasif.uqac.betterwithstrangers.Fragments.EventListFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.NotificationFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.ProfileFragment;
import ubiquasif.uqac.betterwithstrangers.Fragments.TimelineFragment;
import ubiquasif.uqac.betterwithstrangers.Helpers.Helper_NavigationBottomBar;

public class MainActivity extends AppCompatActivity
        implements ProfileFragment.OnFragmentInteractionListener,
        NotificationFragment.OnFragmentInteractionListener,
        TimelineFragment.OnFragmentInteractionListener {

    private Fragment eventListFragment;
    private Fragment profileFragment;
    private Fragment notificationFragment;
    private Fragment timelineFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_party:
                    setTitle(R.string.title_party);
                    switchFragment(eventListFragment);
                    return true;
                case R.id.navigation_timeline:
                    setTitle(R.string.title_timeline);
                    switchFragment(timelineFragment);
                    return true;
                case R.id.navigation_notifications:
                    setTitle(R.string.title_notifications);
                    switchFragment(notificationFragment);
                    return true;
                case R.id.navigation_profile:
                    setTitle(R.string.title_profile);
                    switchFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fix navigation bar strange behavior
        Helper_NavigationBottomBar.disableShiftMode(navigation);

        // TODO Check if factory methods / arguments are really necessary here
        eventListFragment = EventListFragment.newInstance();
        notificationFragment = NotificationFragment.newInstance(null, null);
        timelineFragment = TimelineFragment.newInstance(null, null);
        profileFragment = ProfileFragment.newInstance();

        setTitle(R.string.title_party);
        switchFragment(eventListFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.println(Log.DEBUG, "debug", "Fragment interaction detected");
    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
    }

}
