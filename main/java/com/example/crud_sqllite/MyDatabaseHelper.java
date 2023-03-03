package com.example.crud_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String DATABASE_NAME="BookLibrary.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="my_library";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TITLE="book_title";
    private static final String COLUMN_AUTHOR="book_author";
    private static final String COLUMN_PAGES="book_pages";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query= "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER); ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    void insertBook(String title,String author,int pages){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_AUTHOR,author);
        cv.put(COLUMN_PAGES,pages);

        long result=db.insert(TABLE_NAME,null,cv);
        if (result==-1){
            Toast.makeText(mContext.getApplicationContext(), "Failed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext.getApplicationContext(),"Added successfully",Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readData(){
        String query=" SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if (db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id,String title,String author,String pages){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_AUTHOR,author);
        cv.put(COLUMN_PAGES,pages);

       long result= db.update(TABLE_NAME,cv,"_id=?",new String[] {row_id});
       if (result==-1){
           Toast.makeText(mContext,"Failed to update",Toast.LENGTH_LONG).show();
       }else {
           Toast.makeText(mContext.getApplicationContext(), "Successfully updated",Toast.LENGTH_SHORT).show();
       }
    }

    void deleteOnRow(String row_id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        long result=sqLiteDatabase.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if (result==-1){
            Toast.makeText(mContext,"Failed to delete",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(mContext,"Successfully deleted",Toast.LENGTH_SHORT).show();
        }
    }
}
