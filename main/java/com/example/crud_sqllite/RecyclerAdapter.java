package com.example.crud_sqllite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

   private Context mContext;
   Activity activity;
    private  ArrayList mBook_id,mBook_title,mBook_author,mBook_pages;
   // LayoutInflater layoutInflater;
   // int position;
   public RecyclerAdapter(Activity activity,Context context,ArrayList book_id,ArrayList book_title,ArrayList book_author,ArrayList book_pages){
       this.activity=activity;
       this.mContext=context;
        this.mBook_id=book_id;
        this.mBook_title=book_title;
        this.mBook_author=book_author;
        this.mBook_pages=book_pages;
        //layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.recyclerview_row,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.book_id_text.setText(String.valueOf(mBook_id.get(position)));
            holder.book_title_text.setText(String.valueOf(mBook_title.get(position)));
            holder.book_author_text.setText(String.valueOf(mBook_author.get(position)));
                /*try{
                    holder.book_pages_text.setText(mBook_pages.get(position));
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }*/
        holder.book_pages_text.setText(String.valueOf("Pages"+mBook_pages.get(position)));
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent=new Intent(mContext,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(mBook_id.get(position)));
                    intent.putExtra("title",String.valueOf(mBook_title.get(position)));
                    intent.putExtra("author",String.valueOf(mBook_author.get(position)));

                        intent.putExtra("pages",String.valueOf(mBook_pages.get(position)));


                activity.startActivityForResult(intent,1);

                }
            });
    }

    @Override
    public int getItemCount() {
        return mBook_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView book_id_text,book_title_text,book_author_text,book_pages_text;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_text=itemView.findViewById(R.id.book_id);
            book_title_text=itemView.findViewById(R.id.book_title);
            book_author_text=itemView.findViewById(R.id.book_author);
            book_pages_text=itemView.findViewById(R.id.book_pages);
            layout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
