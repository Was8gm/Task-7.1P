package com.example.task71p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView itemType, itemName, itemPhone, itemDescription, itemDate, itemLocation;
    private Button btnRemove;
    private DatabaseHelper databaseHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemType = findViewById(R.id.item_type);
        itemName = findViewById(R.id.item_name);
        itemPhone = findViewById(R.id.item_phone);
        itemDescription = findViewById(R.id.item_description);
        itemDate = findViewById(R.id.item_date);
        itemLocation = findViewById(R.id.item_location);
        btnRemove = findViewById(R.id.btnRemove);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);
        Item item = databaseHelper.getItem(itemId);

        if (item != null) {
            itemType.setText("Type: " + item.getType());
            itemName.setText("Name: " + item.getName());
            itemPhone.setText("Phone: " + item.getPhone());
            itemDescription.setText("Description: " + item.getDescription());
            itemDate.setText("Date: " + item.getDate());
            itemLocation.setText("Location: " + item.getLocation());
        }

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteItem(itemId);
                finish();
            }
        });
    }
}
