package ubiquasif.uqac.betterwithstrangers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ubiquasif.uqac.betterwithstrangers.CreateEventActivity;
import ubiquasif.uqac.betterwithstrangers.R;


public class EventListFragment extends Fragment {

    private CoordinatorLayout layout;

    public EventListFragment() {
    }

    @NonNull
    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.event_list_layout);

        FloatingActionButton fab = view.findViewById(R.id.create_event_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }
}
