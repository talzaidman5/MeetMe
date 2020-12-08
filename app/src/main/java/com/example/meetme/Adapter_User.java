package com.example.meetme;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Adapter_User extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int  FEMALE= 0;
    private final int MALE  = 1;

    private Context context;
    private ArrayList<User> articles;
    public Adapter_User(Context context, ArrayList<User> articles) {
        this.context = context;
        this.articles = articles;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FEMALE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_female, parent, false);
            return new ViewHolder_Female(view);
        } else if (viewType == MALE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_male, parent, false);
            return new ViewHolder_Male(view);
        }


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_female, parent, false);
        ViewHolder_Female myViewHolderNormal = new ViewHolder_Female(view);
        return myViewHolderNormal;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = getItem(position);

        if (holder instanceof ViewHolder_Female) {
            ViewHolder_Female mHolder = (ViewHolder_Female) holder;
            mHolder.article_LBL_title.setText(user.getName());
            mHolder.article_LBL_subTitle.setText(user.getAge()+"");


//            if (user.getContent().equals("")) {
//                mHolder.article_LBL_title.setTextColor(Color.RED);
//            } else {
//                mHolder.article_LBL_title.setTextColor(Color.BLACK);
//            }

            Glide
                    .with(context)
                    .load(user.getMainImage())
                    .centerCrop()
                    .into(mHolder.article_IMG_back);
        } else if (holder instanceof ViewHolder_Male) {
            ViewHolder_Male mHolder = (ViewHolder_Male) holder;
            mHolder.article_LBL_title.setText(user.getName());
            mHolder.article_LBL_subTitle.setText(user.getAge());


//            if (user.getContent().equals("")) {
//                mHolder.article_LBL_title.setTextColor(Color.RED);
//            } else {
//                mHolder.article_LBL_title.setTextColor(Color.BLACK);
//            }

            Glide
                    .with(context)
                    .load(user.getMainImage())
                    .centerCrop()
                    .into(mHolder.article_IMG_back);

           // mHolder.article_BTN_click.setText(user.getButton());
        }

    }

    private User getItem(int position) {
        return articles.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getPersonGender() == User.Gender.MALE) {
            return MALE;
        } else if (getItem(position).getPersonGender() == User.Gender.FEMALE) {
            return FEMALE;
        }

        return FEMALE;
    }

    static class ViewHolder_Female extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView article_IMG_back;
        public TextView article_LBL_title;
        public TextView article_LBL_subTitle;

        public ViewHolder_Female(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            article_IMG_back = itemView.findViewById(R.id.article_IMG_back);
//            article_LBL_title = itemView.findViewById(R.id.article_LBL_title);
//            article_LBL_subTitle = itemView.findViewById(R.id.article_LBL_subTitle);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

    static class ViewHolder_Male extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView article_IMG_back;
        public TextView article_LBL_title;
        public TextView article_LBL_subTitle;
        public MaterialButton article_BTN_click;

        public ViewHolder_Male(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            article_IMG_back = itemView.findViewById(R.id.article_IMG_back);
            article_LBL_title = itemView.findViewById(R.id.article_LBL_title);
            article_LBL_subTitle = itemView.findViewById(R.id.article_LBL_subTitle);
            article_BTN_click = itemView.findViewById(R.id.article_BTN_click);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }
}
