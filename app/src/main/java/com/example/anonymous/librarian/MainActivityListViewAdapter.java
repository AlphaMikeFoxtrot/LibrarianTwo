package com.example.anonymous.librarian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ANONYMOUS on 04-Nov-17.
 */

public class MainActivityListViewAdapter extends ArrayAdapter<MainActivityListViewItem> {

    public MainActivityListViewAdapter(@NonNull Context context, ArrayList<MainActivityListViewItem> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_for_main_activity, parent, false);

        }

        MainActivityListViewItem currentItem = getItem(position);

        TextView buttonName = listItemView.findViewById(R.id.button_name);
        buttonName.setText(currentItem.getmButtonName());

        ImageView buttonImage = listItemView.findViewById(R.id.button_image);
        buttonImage.setImageResource(currentItem.getmImageResourceId());

        return listItemView;
    }
}
