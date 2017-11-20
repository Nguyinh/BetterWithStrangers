package ubiquasif.uqac.betterwithstrangers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.hootsuite.nachos.ChipConfiguration;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.chip.ChipSpan;
import com.hootsuite.nachos.chip.ChipSpanChipCreator;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.tokenizer.ChipTokenizer;
import com.hootsuite.nachos.tokenizer.SpanChipTokenizer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ubiquasif.uqac.betterwithstrangers.FirstConnectionActivity;
import ubiquasif.uqac.betterwithstrangers.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private NachoTextView tags;

    private String[] suggestions = new String[]{"Tortilla Chips", "Melted Cheese", "Salsa", "Guacamole", "Mexico", "Jalapeno"};
    private List<String> savedPrefs = new ArrayList<String>();

    public ProfileFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        tags.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        tags.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        tags.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    tags.chipifyAllUnterminatedTokens();
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, suggestions);
        tags.setAdapter(adapter);

        for (String word : savedPrefs) {
            Log.d("Nachos", word);
            //tags.getChipTokenizer().applyConfiguration(tags.getEditableText(), new ChipConfiguration());
        }

        //tags.setTextWithChips();
        //tags.performValidation();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onStop()
    {
        for(Chip chip : tags.getAllChips()) {
            savedPrefs.add(chip.getText().toString());
            tags.getChipTokenizer().deleteChip(chip, tags.getEditableText());
        }

        super.onStop();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
