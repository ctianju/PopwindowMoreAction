package com.meta.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.myapplication.MainActivity;
import com.meta.myapplication.R;
import com.meta.myapplication.entity.OrderActionBtnEntity;
import com.meta.myapplication.entity.OrderEntity;
import com.meta.myapplication.utils.DensityUtil;
import com.meta.myapplication.view.ActionMorePopWindow;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.MoreActionHolder> {
    private List<OrderEntity> mData = new ArrayList<>();

    public MainRvAdapter(List<OrderEntity> mData, Context context) {
        this.mData.clear();
        this.mData.addAll(mData);
    }


    @Override
    public MoreActionHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new MoreActionHolder(itemRoot);
    }

    @Override
    public void onBindViewHolder(MoreActionHolder holder, int position) {
        OrderEntity item = this.mData.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MoreActionHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final TextView tv_moreBtn;
        private final LinearLayout normalBtnlay;

        public MoreActionHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.orderName);
            price = itemView.findViewById(R.id.orderPrice);
            tv_moreBtn = itemView.findViewById(R.id.tv_moreBtn);
            normalBtnlay = itemView.findViewById(R.id.normalButtons);
        }

        public void bind(OrderEntity item) {
            name.setText(item.getName());
            price.setText(item.getPrice());
            // ===============更多 中按钮===========================
            if (item.getExt_buttons() != null && item.getButtons().size() > 0) { // 存在就显示
                tv_moreBtn.setVisibility(View.VISIBLE);

                ActionMorePopWindow orderMorePopWindow = new ActionMorePopWindow(
                        itemView.getContext(),
                        item.getExt_buttons()
                );
                orderMorePopWindow.setOnItemMyListener(new ActionMorePopWindow.OnPopWindowItemListener() {
                    @Override
                    public void OnItemListener(int position, OrderActionBtnEntity orderActionEntity) {
                        performClicker(itemView.getContext(),orderActionEntity, orderActionEntity.getType());
                    }
                });
                tv_moreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderMorePopWindow.showPopupWindow(tv_moreBtn);
                    }
                });

            } else {
                tv_moreBtn.setVisibility(View.GONE);
            }

            // =============== 普通按钮==============
            normalBtnlay.removeAllViews();
            if (item.getButtons() != null && item.getButtons().size() > 0) { // 存在普通按钮就加入linearLayout
                normalBtnlay.setVisibility(View.VISIBLE); // 显示
                for (int i = 0; i < item.getButtons().size(); i++) {
                    TextView normalBtn = createBtn(itemView.getContext(), item.getButtons().get(i));
                    normalBtnlay.addView(normalBtn);
                    int finalI = i;
                    normalBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            performClicker(itemView.getContext(),item.getButtons().get(finalI), (Integer) normalBtn.getTag());
                        }
                    });
                }
            } else { // 不显示
                normalBtnlay.setVisibility(View.GONE);
            }
        }

        /**
         * @param context
         * @param buttonEntity
         * @return 动态创建普通类型的按钮
         */
        private TextView createBtn(Context context, OrderActionBtnEntity buttonEntity) {
            TextView tv = new TextView(context);
            tv.setText(buttonEntity.getName());
            tv.setTag(buttonEntity.getType());
            tv.setBackground(context.getResources().getDrawable(R.drawable.order_btn_normal_bg));
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(DensityUtil.INSTANCE.dp2px(75f), DensityUtil.INSTANCE.dp2px(30f));
            layoutParams.setMargins(DensityUtil.INSTANCE.dp2px(10f), 0, 0, 0);//4个参数按顺序分别是左上右下
            tv.setLayoutParams(layoutParams);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(10, 5, 10, 5);
            // 设置按钮的文字颜色和边框高亮颜色，不存在就是默认的灰色
            GradientDrawable gradientDrawable = (GradientDrawable) tv.getBackground();
            gradientDrawable.setStroke(1, Color.parseColor(buttonEntity.getColor()));//设置边框的宽度和颜色
            tv.setTextColor(Color.parseColor(buttonEntity.getColor()));
            return tv;
        }
    }

    private void performClicker(Context context, OrderActionBtnEntity entity, int type) {
        switch (type) {
            case MainActivity
                    .DELETE_TYPE:
                Toast.makeText(context, "点击了按钮:" + entity.getName(), Toast.LENGTH_SHORT).show();
            case MainActivity
                    .GO_SHOPPING_CART_TYPE:
                Toast.makeText(context, "点击了按钮:" + entity.getName(), Toast.LENGTH_SHORT).show();
            case MainActivity
                    .MAKE_AN_INVOICE_TYPE:
                Toast.makeText(context, "点击了按钮:" + entity.getName(), Toast.LENGTH_SHORT).show();

        }
    }

}
