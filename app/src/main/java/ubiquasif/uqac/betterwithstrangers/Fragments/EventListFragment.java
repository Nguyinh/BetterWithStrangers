package ubiquasif.uqac.betterwithstrangers.Fragments;


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
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ubiquasif.uqac.betterwithstrangers.CreateEventActivity;
import ubiquasif.uqac.betterwithstrangers.Helpers.EventHolder;
import ubiquasif.uqac.betterwithstrangers.Models.Event;
import ubiquasif.uqac.betterwithstrangers.R;


public class EventListFragment extends Fragment implements EventHolder.OnItemViewClickedListener {

    private RecyclerView recyclerView;

    private EventAdapter adapter;

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

        recyclerView = view.findViewById(R.id.event_recycler);

        FloatingActionButton fab = view.findViewById(R.id.create_event_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(intent);
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

    /**
     * Méthode appelée lorsque l'utilisateur clique sur un élément de la liste (une soirée)
     *
     * @param itemView La vue en question
     */
    @Override
    public void onItemViewClicked(View itemView) {
        Snackbar.make(itemView, "Ouvrir un dialogue pour les détails", Snackbar.LENGTH_SHORT).show();
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
}
