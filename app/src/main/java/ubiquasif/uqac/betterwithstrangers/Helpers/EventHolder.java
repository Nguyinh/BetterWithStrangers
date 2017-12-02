package ubiquasif.uqac.betterwithstrangers.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import ubiquasif.uqac.betterwithstrangers.Models.Event;
import ubiquasif.uqac.betterwithstrangers.R;


public class EventHolder extends RecyclerView.ViewHolder {

    private Event model;

    private TextView nameView;
    private TextView dateView;
    private TextView locationView;
    private RatingBar ratingBar;

    public EventHolder(View itemView, final OnItemViewClickedListener listener) {
        super(itemView);

        nameView = itemView.findViewById(R.id.item_event_name);
        dateView = itemView.findViewById(R.id.item_event_date);
        locationView = itemView.findViewById(R.id.item_event_location);
        ratingBar = itemView.findViewById(R.id.item_event_consensus);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemViewClicked(view, model);
            }
        });
    }

    public void bind(Event model) {
        this.model = model;

        nameView.setText(model.getName());
        locationView.setText(model.getPlaceName());
        ratingBar.setRating((float) model.getConsensus());

        Date timestamp = model.getTimestamp();
        String date = DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                .format(timestamp);

        dateView.setText(date);
    }


    public interface OnItemViewClickedListener {
        void onItemViewClicked(View itemView, Event model);
    }
}
