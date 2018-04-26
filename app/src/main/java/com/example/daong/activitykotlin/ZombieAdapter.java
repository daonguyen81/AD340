package com.example.daong.activitykotlin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

public class ZombieAdapter extends RecyclerView.Adapter<ZombieAdapter.ViewHolder> {

    private Context context;
    private List<Zombie> list;

    public ZombieAdapter(Context context, List<Zombie> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MovieDetail.class);
                i.putExtra("zombie_title", list.get(viewHolder.getAdapterPosition()).getTitle());
                i.putExtra("zombie_year", list.get(viewHolder.getAdapterPosition()).getYear());
                i.putExtra("zombie_director", list.get(viewHolder.getAdapterPosition()).getDirector());
                i.putExtra("zombie_image", list.get(viewHolder.getAdapterPosition()).getImage_url());
                i.putExtra("zombie_description", list.get(viewHolder.getAdapterPosition()).getDescription());
                context.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Zombie zombie = list.get(position);
        holder.textTitle.setText(zombie.getTitle());
        holder.textYear.setText(String.valueOf(zombie.getYear()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textYear;
        RelativeLayout view_container;

        public ViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            textTitle = itemView.findViewById(R.id.main_title);
            textYear = itemView.findViewById(R.id.main_year);
        }
    }
}