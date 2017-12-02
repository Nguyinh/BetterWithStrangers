package ubiquasif.uqac.betterwithstrangers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.squareup.picasso.Picasso;

import ubiquasif.uqac.betterwithstrangers.FirstConnectionActivity;
import ubiquasif.uqac.betterwithstrangers.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private NachoTextView tags;

    private String[] suggestions = new String[]{"Tortilla Chips", "Melted Cheese", "Salsa", "Guacamole", "Mexico", "Jalapeno"};

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

        Button modifyButton = view.findViewById(R.id.modify_button);
        modifyButton.setOnClickListener(this);

        TextView nameView = view.findViewById(R.id.display_name);
        ImageView photoView = view.findViewById(R.id.profile_photo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            if (name != null && name.length() > 0)
                nameView.setText(name);

            Uri photoUrl = user.getPhotoUrl();
            if (photoUrl != null)
                Picasso.with(getContext()).load(photoUrl).into(photoView);
        }

        tags = view.findViewById(R.id.preferences);
        tags.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {                                       // si perte de focus
                    tags.chipifyAllUnterminatedTokens();            // "chip" tous les tokens
                    for(Chip chip : tags.getAllChips())             // parcourt tous les tokens pour vérifier s'ils sont bien dans les suggestions
                    {
                        boolean isLegit = false;
                        for(String sample : suggestions)
                        {
                            if(chip.getText() == sample)            // s'il correspond au moins une fois, alors il est validé
                            {
                                Log.d("Nachos", sample + " " + chip.getText());
                                isLegit = true;
                                break;
                            }
                        }
                        if (!isLegit)                               // sinon il est effacé
                            tags.getChipTokenizer().deleteChip(chip, tags.getEditableText());
                    }
                }
            }
        });

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
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
