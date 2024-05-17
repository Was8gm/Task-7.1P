package com.example.task71p;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    private RadioGroup selectionType;
    private EditText editName, editPhone, editDescription, editDate, editLocation;
    private Button btnSubmit;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        selectionType = findViewById(R.id.selection_type);
        editName = findViewById(R.id.edit_Name);
        editPhone = findViewById(R.id.edit_Phone);
        editDescription = findViewById(R.id.edit_Description);
        editDate = findViewById(R.id.edit_Date);
        editLocation = findViewById(R.id.edit_Location);
        btnSubmit = findViewById(R.id.btnSubmit);

        databaseHelper = new DatabaseHelper(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = selectionType.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String type = selectedRadioButton.getText().toString();
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String description = editDescription.getText().toString();
                String date = editDate.getText().toString();
                String location = editLocation.getText().toString();

                if (databaseHelper.addItem(type, name, phone, description, date, location)) {
                    Toast.makeText(CreateAdvertActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}