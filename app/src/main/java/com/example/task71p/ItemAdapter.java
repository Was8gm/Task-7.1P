package com.example.task71p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list, parent, false);
        }

        Item item = getItem(position);

        TextView itemType = convertView.findViewById(R.id.item_type);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemDescription = convertView.findViewById(R.id.item_description);

        itemType.setText(item.getType());
        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());

        return convertView;
    }
}