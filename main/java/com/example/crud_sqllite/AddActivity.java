package com.example.crud_sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.crud_sqllite.databinding.ActivityAddBinding;
import com.example.crud_sqllite.databinding.ActivityMainBinding;

public class AddActivity extends AppCompatActivity {
//EditText title_input,author_input,pages_input;
//Button add;
    ActivityAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(AddActivity.this);
                myDatabaseHelper.insertBook(binding.titleInput.getText().toString().trim(),
                        binding.authorInput.getText().toString().trim(), Integer.parseInt(binding.pagesInput.getText().toString().trim()));
            }
        });
    }
}