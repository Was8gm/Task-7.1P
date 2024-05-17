package com.example.task71p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListItemsActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private ItemAdapter adapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        listView = findViewById(R.id.items);
        databaseHelper = new DatabaseHelper(this);

        setupListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshItemList();
    }

    private void setupListView() {
        items = databaseHelper.getAllItems();
        adapter = new ItemAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = items.get(position);
                Intent intent = new Intent(ListItemsActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_ID", item.getId());
                startActivity(intent);
            }
        });
    }

    private void refreshItemList() {
        items.clear();
        items.addAll(databaseHelper.getAllItems());
        adapter.notifyDataSetChanged();
    }
}