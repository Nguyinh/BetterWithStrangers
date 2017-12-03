package ubiquasif.uqac.betterwithstrangers.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import ubiquasif.uqac.betterwithstrangers.Models.Notification;
import ubiquasif.uqac.betterwithstrangers.R;

/**
 * Created by Amandine on 02/12/2017.
 */

public class NotificationHolder extends RecyclerView.ViewHolder {

    private Notification model;

    private TextView contentView;
    private TextView dateView;

    public NotificationHolder(View itemView, final OnItemViewClickedListener listener) {
        super(itemView);

        contentView = itemView.findViewById(R.id.item_notification_content);
        dateView = itemView.findViewById(R.id.item_notification_date);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemViewClicked(view, model);
            }
        });
    }

    public void bind(Notification model) {
        this.model = model;

        contentView.setText(model.getContent());

        Date timestamp = model.getTimestamp();
        String date = DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                .format(timestamp);

        dateView.setText(date);
    }


    public interface OnItemViewClickedListener {
        void onItemViewClicked(View itemView, Notification model);
    }
}
