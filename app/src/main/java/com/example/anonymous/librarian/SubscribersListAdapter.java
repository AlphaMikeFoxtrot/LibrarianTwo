package com.example.anonymous.librarian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ANONYMOUS on 04-Nov-17.
 */

public class SubscribersListAdapter extends RecyclerView.Adapter<SubscribersListAdapter.SubscriberListAdapterViewHolder> {

    Context context;
    ArrayList<SubscribersListItem> subscribers = new ArrayList<>();

    private final String SUBSCRIBER_ID = "Subscriber Id : ";
    private final String SUBSCRIBER_PHONE = "Phone No. : ";
    private final String SUBSCRIBER_EMAIL = "Email Id : ";

    public SubscribersListAdapter(Context context, ArrayList<SubscribersListItem> subscribers) {
        this.context = context;
        this.subscribers = subscribers;
    }

    @Override
    public SubscriberListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listItemView = LayoutInflater.from(context).inflate(R.layout.subscribers_list_custom_card_view, parent, false);

        return new SubscriberListAdapterViewHolder(listItemView, this.context, this.subscribers);

    }

    @Override
    public void onBindViewHolder(SubscriberListAdapterViewHolder holder, int position) {

        SubscribersListItem currentSubscriber = subscribers.get(position);

        holder.subName.setText(currentSubscriber.getmSubName());
        holder.subId.setText(SUBSCRIBER_ID + currentSubscriber.getmSubId());
        holder.subPhone.setText(SUBSCRIBER_PHONE + currentSubscriber.getmSubPhone());
        holder.subEmail.setText(SUBSCRIBER_EMAIL + currentSubscriber.getmSubEmail());

    }

    @Override
    public int getItemCount() {
        return subscribers.size();
    }

    public class SubscriberListAdapterViewHolder extends RecyclerView.ViewHolder{

        Context context;
        ArrayList<SubscribersListItem> subscribers;

        TextView subEmail;
        TextView subPhone;
        TextView subId;
        TextView subName;

        public SubscriberListAdapterViewHolder(View itemView, Context context, ArrayList<SubscribersListItem> subscribers) {
            super(itemView);

            this.context = context;
            this.subscribers = subscribers;

            subName = itemView.findViewById(R.id.subscriber_name);
            subId = itemView.findViewById(R.id.subscriber_id);
            subPhone = itemView.findViewById(R.id.subscriber_phone);
            subEmail = itemView.findViewById(R.id.subscriber_email);

        }
    }

    public void setFilter(ArrayList<SubscribersListItem> newList){

        subscribers = new ArrayList<SubscribersListItem>();
        subscribers.addAll(newList);
        notifyDataSetChanged();

    }

}
