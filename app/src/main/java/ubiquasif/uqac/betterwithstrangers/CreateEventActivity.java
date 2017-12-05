package ubiquasif.uqac.betterwithstrangers;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ubiquasif.uqac.betterwithstrangers.Fragments.AutoCompleteFragment;
import ubiquasif.uqac.betterwithstrangers.Models.Event;
import ubiquasif.uqac.betterwithstrangers.Models.User;

public class CreateEventActivity
        extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private CoordinatorLayout layout;

    private TextView dateView;
    private TextView timeView;
    private EditText nameEdit;
    private EditText locationEdit;
    private Switch privateSwitch;
    private NachoTextView tagsView;
    private AutoCompleteFragment autoCompleteFragment;

    private Calendar pickedDateTime;
    private FirebaseFirestore database;

    private String[] suggestions = new String[]{"Salsa", "Chill", "Cinema", "Film", "Etudes", "Android", "Programmation", "Beerpong", "Karaoke", "Wine&Cheese", "Detente", "RavePAAAAARTY", "Lords of the ring",
            "Truth or dare", "Concert", "Barathon", "PoolParty", "Mousse", "Strangers", "Meetic", "Speed dating", "Beer", "Netflix and chill", "Hockey", "LAN",
            "Tuning", "Birthday", "Bob", "Halloween", "Christmas", "Pijama Party", "Nouvel an", "Déguisé", "Food", "Vegan", "Veillée", "No alcohol", "Harry Potter",
            "Star Wars", "Star Trek", "Spooky", "Scatophile", "Urinophilie", "Batman", "Churros", "Rock", "Rap", "Tecktonik", "Pop", "Classique", "Electro", "Raggae",
            "French kiss", "Baguette", "Poutine", "Nachos", "Tortilla", "SuitUp", "Geek", "Street", "Meeting", "Random"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        layout = findViewById(R.id.create_event_layout);

        pickedDateTime = new GregorianCalendar();
        dateView = findViewById(R.id.event_date);
        timeView = findViewById(R.id.event_time);
        updateDateView();
        updateTimeView();

        tagsView = findViewById(R.id.event_tags);
        tagsView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {                                       // si perte de focus
                    tagsView.chipifyAllUnterminatedTokens();            // "chip" tous les tokens
                    List<String> comparator = new ArrayList<String>();
                    for(final Chip chip : tagsView.getAllChips())             // parcourt tous les tokens pour vérifier s'ils sont bien dans les suggestions
                    {
                        boolean isLegit1 = false;
                        boolean isLegit2 = true;
                        for(String sample : suggestions)
                        {
                            if(chip.getText() == sample)            // s'il correspond au moins une fois, alors il est validé
                            {
                                Log.d("Nachos", sample + " " + chip.getText());
                                for(String comp : comparator)
                                {
                                    if(comp == chip.getText().toString())
                                        isLegit2 = false;
                                }
                                isLegit1 = true;
                                break;
                            }
                        }
                        if (!isLegit1 || !isLegit2)                               // sinon il est effacé
                            tagsView.getChipTokenizer().deleteChip(chip, tagsView.getEditableText());
                        else {                                      // validé et ajouté à Firebase
                            comparator.add(chip.getText().toString());
                            final DocumentReference docRef = database.collection("users")
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            Task<DocumentSnapshot> future = docRef.get();

                            future.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User userTemp = documentSnapshot.toObject(User.class);

                                    userTemp.addPreference(chip.getText().toString());

                                    docRef.update("preferences", userTemp.getPreferences())
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("OnFailure", e.getMessage());
                                                }
                                            })
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("OnSuccess", "succes");
                                                }
                                            });
                                }
                            });
                        }
                    }
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        tagsView.setAdapter(adapter);

        nameEdit = findViewById(R.id.event_name);
        locationEdit = findViewById(R.id.event_location);

        autoCompleteFragment = (AutoCompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        privateSwitch = findViewById(R.id.event_private);

        database = FirebaseFirestore.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_event:
                saveEvent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        pickedDateTime.set(year, month, dayOfMonth);
        updateDateView();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        pickedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        pickedDateTime.set(Calendar.MINUTE, minute);
        pickedDateTime.set(Calendar.SECOND, 0);
        updateTimeView();
    }

    public void onDateClicked(View v) {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                this,
                pickedDateTime.get(Calendar.YEAR),
                pickedDateTime.get(Calendar.MONTH),
                pickedDateTime.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    public void onTimeClicked(View v) {
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                this,
                pickedDateTime.get(Calendar.HOUR_OF_DAY),
                pickedDateTime.get(Calendar.MINUTE),
                true
        );

        dialog.show();
    }

    private void saveEvent() {
        tagsView.chipifyAllUnterminatedTokens();

        String placeName = autoCompleteFragment.getPlaceName();
        GeoPoint location = autoCompleteFragment.getPoint();

        Event event = new Event(
                FirebaseAuth.getInstance().getUid(),
                nameEdit.getText().toString(),
                privateSwitch.isChecked(),
                tagsView.getChipValues(),
                pickedDateTime.getTime(),
                (placeName != null) ? placeName : "",
                location
        );

        Snackbar.make(layout, R.string.event_pending, Snackbar.LENGTH_SHORT).show();

        database.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(layout, R.string.event_failure, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void updateDateView() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        String date = dateFormat.format(pickedDateTime.getTime());
        dateView.setText(date);
    }

    private void updateTimeView() {
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        String time = timeFormat.format(pickedDateTime.getTime());
        timeView.setText(time);
    }
}
