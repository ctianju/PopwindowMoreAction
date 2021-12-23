package com.meta.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.meta.myapplication.R;
import com.meta.myapplication.entity.OrderActionBtnEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActionSelectPopularAdapter extends RecyclerView.Adapter<ActionSelectPopularAdapter.MoreActionHolder> {
    private List<OrderActionBtnEntity> mData = new ArrayList<>();
    private Context context;
   private OnItemClickerListener onItemClickerListener;
    public ActionSelectPopularAdapter(List<OrderActionBtnEntity> mData, Context context) {
        this.mData.clear();
        this.mData.addAll(mData);
        this.context = context;
    }

    public void setOnItemClickerListener(OnItemClickerListener onItemClickerListener) {
        this.onItemClickerListener = onItemClickerListener;
    }

    @Override
    public MoreActionHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_item_action, parent, false);
        return new MoreActionHolder(itemRoot);
    }

    @Override
    public void onBindViewHolder(MoreActionHolder holder, int position) {
        OrderActionBtnEntity item = this.mData.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MoreActionHolder extends RecyclerView.ViewHolder {
        private final TextView orderActionTitle;
        public MoreActionHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            orderActionTitle = itemView.findViewById(R.id.orderActionTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickerListener.onItemClick(getAdapterPosition());
                }
            });
        }
        public void bind(OrderActionBtnEntity item) {
            orderActionTitle.setText(item.getName());
        }
    }
    public interface OnItemClickerListener{
        void onItemClick(int position);
    }
}
