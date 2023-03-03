package com.example.crud_sqllite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.crud_sqllite.databinding.ActivityAddBinding;
import com.example.crud_sqllite.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {
ActivityUpdateBinding binding;
String id, title,author,pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //first we call this
        getIntentData();

        ActionBar ab=getSupportActionBar();
        if (ab!=null){
            ab.setTitle(title);
        }
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //then only we can update this
                MyDatabaseHelper mydb=new MyDatabaseHelper(UpdateActivity.this);
                title=binding.titleInput2.getText().toString().trim();
                author=binding.authorInput2.getText().toString().trim();
               pages=binding.pagesInput2.getText().toString().trim();
                mydb.updateData(id,title,author,pages);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDailog();
            }
        });


    }

    void getIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("title")&&getIntent().hasExtra("author")&& getIntent().hasExtra("pages")){
           //getting data from intent
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            author=getIntent().getStringExtra("author");
           pages=getIntent().getStringExtra("pages");
            //setting data from intent

            binding.titleInput2.setText(title);
            binding.authorInput2.setText(author);
            binding.pagesInput2.setText(pages);
        }else {
            Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDailog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete" + title +"?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(UpdateActivity.this);
                myDatabaseHelper.deleteOnRow(id);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }
}