package ubiquasif.uqac.betterwithstrangers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.chip.ChipInfo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ubiquasif.uqac.betterwithstrangers.FirstConnectionActivity;
import ubiquasif.uqac.betterwithstrangers.Models.Event;
import ubiquasif.uqac.betterwithstrangers.Models.User;
import ubiquasif.uqac.betterwithstrangers.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private NachoTextView tags;

    private Chip currentChip;

    private List<String> userPreferences;

    private String[] suggestions = new String[]{"Salsa", "Chill", "Cinema", "Film", "Etudes", "Android", "Programmation", "Beerpong", "Karaoke", "Wine&Cheese", "Detente", "RavePAAAAARTY", "Lords of the ring",
                                                "Truth or dare", "Concert", "Barathon", "PoolParty", "Mousse", "Strangers", "Meetic", "Speed dating", "Beer", "Netflix and chill", "Hockey", "LAN",
                                                "Tuning", "Birthday", "Bob", "Halloween", "Christmas", "Pijama Party", "Nouvel an", "Déguisé", "Food", "Vegan", "Veillée", "No alcohol", "Harry Potter",
                                                "Star Wars", "Star Trek", "Spooky", "Scatophile", "Urinophilie", "Batman", "Churros", "Rock", "Rap", "Tecktonik", "Pop", "Classique", "Electro", "Raggae",
                                                "French kiss", "Baguette", "Poutine", "Nachos", "Tortilla", "SuitUp", "Geek", "Street", "Meeting", "Random"};


    private List<String> suggestionsList;

    private FirebaseFirestore database;

    private List<String> comparator;

    TextView tv;

    RatingBar guestRB;
    RatingBar hostRB;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signOutButton = view.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        TextView nameView = view.findViewById(R.id.display_name);
        ImageView photoView = view.findViewById(R.id.profile_photo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        guestRB = view.findViewById(R.id.guest_ratingBar);
        hostRB = view.findViewById(R.id.host_ratingBar);

        guestRB.setRating(3);

        database = FirebaseFirestore.getInstance();

        if (user != null) {
            String name = user.getDisplayName();
            if (name != null && name.length() > 0)
                nameView.setText(name);

            Uri photoUrl = user.getPhotoUrl();
            if (photoUrl != null)
                Picasso.with(getContext()).load(photoUrl).into(photoView);
        }

        userPreferences = new ArrayList<>();
        suggestionsList= new ArrayList<>(Arrays.asList(suggestions));

        tags = view.findViewById(R.id.preferences);

        database.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                          @Override
                                          public void onSuccess(DocumentSnapshot documentSnapshot) {
                                              if (documentSnapshot.get("preferences") != null)
                                              {
                                                  userPreferences = (List<String>) documentSnapshot.get("preferences");
                                                  tags.setText(userPreferences);
                                              }

                                          }
                                      });

        tags.setText(userPreferences);      // ajoute les préférences dans Nachos depuis Firebase

        tags.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean focus) {
               if(!focus) {
                   tags.chipifyAllUnterminatedTokens();
                   Chip lastChip = tags.getAllChips().get(tags.getAllChips().size() - 1);
                   if (!suggestionsList.contains(lastChip.getText().toString()))
                   {
                       tags.getChipTokenizer().deleteChip(lastChip, tags.getEditableText());
                   }
                    userPreferences = tags.getChipValues();


               }


            }});

        tv = view.findViewById(R.id.minibio_TextView);

        database.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.get("minibio") != null)
                        {
                            tv.setText(documentSnapshot.get("minibio").toString());
                        }

                    }
                });

        database.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.get("guestRating") != null)
                        {
                            long guestRating = (long) documentSnapshot.get("guestRating");
                            guestRB.setRating((float) guestRating);
                        }

                    }
                });

        database.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.get("hostRating") != null)
                        {
                            long hostRating = (long) documentSnapshot.get("hostRating");
                            hostRB.setRating((float) hostRating);
                        }

                    }
                });

        /*
        tags.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {                                       // si perte de focus
                    tags.chipifyAllUnterminatedTokens();            // "chip" tous les tokens
                    for(final Chip chip : tags.getAllChips())             // parcourt tous les tokens pour vérifier s'ils sont bien dans les suggestions
                    {
                        Boolean isLegit1 = false;
                        Boolean isLegit2 = true;
                        for(String sample : suggestions)
                        {
                            Log.d("Nachos", sample + " " + chip.getText());
                            if(chip.getText().toString().equals(sample) )            // s'il correspond au moins une fois, alors il est validé
                            {

                                for(String comp : comparator)
                                {
                                    if(chip.getText().toString().equals(comp))
                                        isLegit2 = false;
                                    else
                                        comparator.add(chip.getText().toString());

                                }
                                isLegit1 = true;
                                break;
                            }
                        }


                        Log.d("isLegit1", isLegit1.toString());
                        Log.d("isLegit2", isLegit2.toString());
                        if (!isLegit1 || !isLegit2)                               // sinon il est effacé
                            tags.getChipTokenizer().deleteChip(chip, tags.getEditableText());
                        else {                                      // validé et ajouté à Firebase

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
*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, suggestions);
        tags.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_out_button) {
            AuthUI.getInstance()
                    .signOut(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getActivity(), FirstConnectionActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
        }

        else if (view.getId() == R.id.save_button) {
            Log.d("Hugo", "cliqué");

            tags.chipifyAllUnterminatedTokens();
            Chip lastChip = tags.getAllChips().get(tags.getAllChips().size() - 1);
            Log.d("lastChip", lastChip.getText().toString());
            if (!suggestionsList.contains(lastChip.getText().toString()))
            {
                tags.getChipTokenizer().deleteChip(lastChip, tags.getEditableText());
            }
            userPreferences = tags.getChipValues();

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("preferences", userPreferences);

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("minibio", tv.getText().toString());
        }
/*
        //region test
        else if (view.getId() == R.id.test_button) {
            User user = new User(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    "Florent Dupont"
            );


            //Snackbar.make(getView(), "Ajout d'un utilisateur", Snackbar.LENGTH_SHORT).show();
            Log.d("users", "sucess");

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(getView(), "Création de User complète", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("savedEvents")
                    .add(new Event(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            "Soirée posée",
                            true,
                            new ArrayList<String>() {{
                                add("tag1");
                                add("tag2");
                                add("tag42");
                            }},
                            Calendar.getInstance().getTime(),
                            "a la casa",
                            0,
                            0))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Snackbar.make(getView(), "Ajout soirée succès !", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(getView(), "ECHEC !", Snackbar.LENGTH_LONG).show();
                        }
                    });

        }
        //endregion
        */
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
