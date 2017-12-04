package ubiquasif.uqac.betterwithstrangers.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import ubiquasif.uqac.betterwithstrangers.Helpers.NotificationHolder;
import ubiquasif.uqac.betterwithstrangers.Models.Notification;
import ubiquasif.uqac.betterwithstrangers.R;

/**
 * NotificationFragment
 */
public class NotificationFragment extends Fragment implements NotificationHolder.OnItemViewClickedListener{

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;


    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NotificationFragment.
     */
    @NonNull
    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.notification_recycler);


        /*Query query = FirebaseFirestore.getInstance()
                .collection("notifications")
                .orderBy("timestamp")
                .limit(20);*/

        Query query = FirebaseFirestore.getInstance()
                .collection("notifications")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid()) // Vérifier si notification.userId = id de l'utilisateur
                .orderBy("timestamp", Query.Direction.DESCENDING) //date plus récente en haut
                .limit(20);

        FirestoreRecyclerOptions<Notification> options = new FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification.class)
                .build();

        adapter = new NotificationAdapter(options, this);
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
     * Méthode appelée lorsque l'utilisateur clique sur un élément de la liste
     *
     * @param itemView La vue en question
     * @param model    notification en question, pour accéder aux détails
     */
    @Override
    public void onItemViewClicked(View itemView, Notification model) {
        if (model != null) {

            Snackbar.make(itemView, "Notification lue", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Classe associant les Notifications de la base de données à une vue dans la liste
     * Voir le layout item_notification et la classe Helpers.NotificationHolder
     */
    public class NotificationAdapter extends FirestoreRecyclerAdapter<Notification, NotificationHolder> {
        NotificationHolder.OnItemViewClickedListener listener;

        NotificationAdapter(FirestoreRecyclerOptions<Notification> options, NotificationHolder.OnItemViewClickedListener listener) {
            super(options);
            this.listener = listener;
        }

        @Override
        public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_notification, parent, false);

            return new NotificationHolder(view, listener);
        }

        @Override
        protected void onBindViewHolder(NotificationHolder holder, int position, Notification model) {
            holder.bind(model);
        }
    }

}
