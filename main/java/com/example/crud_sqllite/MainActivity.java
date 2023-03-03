package com.example.crud_sqllite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.crud_sqllite.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
   // ImageView empty_imageview;
   // TextView no_data;
    Button button;
    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    RecyclerAdapter customAdapter;
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Demo.xls");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.floatingButton);
        button=findViewById(R.id.button);
       // empty_imageview = findViewById(R.id.empty_imageview);
      //  no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new RecyclerAdapter(MainActivity.this,this, book_id, book_title, book_author,
                book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readData();
        if(cursor.getCount() == 0){
           Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }

        }
    }

    public  void saveData() throws IOException {
        SQLiteDatabase database = openOrCreateDatabase("BookLibrary.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM my_library";
        Cursor cursor = database.rawQuery(query, null);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("LibraryData");

        int rowNum = 0;
        while (cursor.moveToNext()) {
            HSSFRow row = sheet.createRow(rowNum);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                HSSFCell cell = ((HSSFRow) row).createCell(i);
                cell.setCellValue(cursor.getString(i));
            }
            rowNum++;
        }
        cursor.close();
        database.close();

        //File file = new File(getFilesDir(), "file_name.xls");
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();


    }
    }
