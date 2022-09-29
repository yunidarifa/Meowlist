package com.yundev.meowlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yundev.meowlist.R;
import com.yundev.meowlist.model.Cat;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private Context context;
    private List<Cat> cats;
    private OnItemClickCallback onItemClickCallback;

    public CatAdapter(Context context, List<Cat> cats, OnItemClickCallback onItemClickCallback) {
        this.context = context;
        this.cats = cats;
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public CatAdapter.CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Cat cat = cats.get(position);
        Picasso.get()
                .load(cat.getUrl())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(cat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public void getAllDatas(List<Cat> cats) {
        this.cats = cats;
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_list);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Cat data);
    }
}
