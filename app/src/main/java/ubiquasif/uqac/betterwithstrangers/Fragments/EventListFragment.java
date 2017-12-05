package ubiquasif.uqac.betterwithstrangers.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Locale;

import ubiquasif.uqac.betterwithstrangers.CreateEventActivity;
import ubiquasif.uqac.betterwithstrangers.Helpers.EventHolder;
import ubiquasif.uqac.betterwithstrangers.Models.Event;
import ubiquasif.uqac.betterwithstrangers.R;


public class EventListFragment extends Fragment implements EventHolder.OnItemViewClickedListener {

    private OnFragmentInteractionListener listener;
    private RecyclerView recyclerView;
    private EventAdapter adapter;

    private boolean isToggled = false;

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

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.event_recycler);

        FloatingActionButton fab = view.findViewById(R.id.map_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onMapButtonClicked();
                }
            }
        });

        Query query = FirebaseFirestore.getInstance()
                .collection("events")
                .orderBy("timestamp")
                .limit(20);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        adapter = new EventAdapter(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_event:
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur un élément de la liste (une soirée)
     *
     * @param itemView La vue en question
     * @param model    L'événement (soirée) en question, pour accéder aux détails
     */
    @Override
    public void onItemViewClicked(View itemView, Event model) {
        if (model != null) {
            String s = String.format(Locale.CANADA_FRENCH, "%.2f/5 (%d votes)", model.getConsensus(), model.getNumberOfRatings());
            if (!isToggled) {
                itemView.setBackgroundColor(0xffc4ffbf);
                isToggled = true;
            }
            else
            {
                itemView.setBackgroundColor(0x00000000);
                isToggled = false;
            }
            Snackbar.make(itemView, s, Snackbar.LENGTH_SHORT).show();
        }
    }


    /**
     * Classe associant les Events (soirées) de la base de données à une vue dans la liste
     * Voir le layout item_event et la classe Helpers.EventHolder
     */
    public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventHolder> {
        EventHolder.OnItemViewClickedListener listener;

        EventAdapter(FirestoreRecyclerOptions<Event> options, EventHolder.OnItemViewClickedListener listener) {
            super(options);
            this.listener = listener;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_event, parent, false);

            return new EventHolder(view, listener);
        }

        @Override
        protected void onBindViewHolder(EventHolder holder, int position, Event model) {
            holder.bind(model);
        }
    }


    public interface OnFragmentInteractionListener {
        void onMapButtonClicked();
    }
}
