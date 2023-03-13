package com.example.tastybites;

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;



public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.ViewHolder> implements SelectListener {
    private List<Repo> retrievedResponses;
    private Context context;
    private SelectListener listenerinterface;




    public RecyclerViewHomeAdapter(List<Repo> jsonResponses, FragmentActivity activity) {
        this.retrievedResponses = jsonResponses;
        this.listenerinterface = listenerinterface;
        this.context = context;


    }




    @NonNull
    @Override
    public RecyclerViewHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_view_home, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHomeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setName_field(retrievedResponses.get(position).getName());
        holder.setPrice_field(retrievedResponses.get(position).getPrice());
        Glide.with(holder.itemView.getContext()).load(retrievedResponses.get(position).getImage()).into(holder.image_field);
        // set onclick listener using the listener in viewholder

//        holder.image_field.setOnClickListener(holder.listener);
//        holder.image_field.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                int postion = holder.getAdapterPosition();
//                Intent intent = new Intent(context,RestaurantDetail.class);
//                context.startActivity(intent);
//
//
//            }
//        });









    }

    @Override
    public int getItemCount() {
        return  retrievedResponses.size();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(context,RestaurantDetail.class);
        context.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_field;
        private TextView name_field;
        private TextView price_field;
        private ImageView image_field;
        private CardView card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            id_field = itemView.findViewById(R.id.id_field);
            name_field = itemView.findViewById(R.id.name_field_home);
            price_field = itemView.findViewById(R.id.price_field_home);
            image_field = itemView.findViewById(R.id.image_field_home);
            card_view = itemView.findViewById(R.id.card_view);

            image_field.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listenerinterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listenerinterface.onItemClicked(position);
                        }
                    }
                }
            });
        }

        public void setName_field(String name) {
            name_field.setText(name);
        }

        public void setPrice_field(String price) {
            price_field.setText(price);
        }

        public void setImage_field(String image) {
            Glide.with(itemView.getContext()).load(image).into(image_field);
        }





    }


}
