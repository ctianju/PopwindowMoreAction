package com.meta.myapplication.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.meta.myapplication.adapter.ActionSelectPopularAdapter;
import com.meta.myapplication.entity.OrderActionBtnEntity;
import com.meta.myapplication.R;
import com.meta.myapplication.utils.DensityUtil;
import com.meta.myapplication.utils.SpacingDecoration;

import java.util.ArrayList;
import java.util.List;

public class ActionMorePopWindow extends PopupWindow {
    private Context context;
    private View conentView;
    private RecyclerView listView;
    private ActionSelectPopularAdapter selectAdapter;
    List<OrderActionBtnEntity> typeSelectlist = new ArrayList();
    int[] location = new int[2];
    private OnPopWindowItemListener onItemListener;
    private LinearLayout llParent;
    public interface OnPopWindowItemListener {
         void OnItemListener(int position, OrderActionBtnEntity orderActionEntity);
    }

    public void setOnItemMyListener(OnPopWindowItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public ActionMorePopWindow(Context context) {
        this.context = context;
        initView();
    }

    public ActionMorePopWindow(Context context, List<OrderActionBtnEntity> typeSelectlist) {
        this.context = context;
        this.typeSelectlist = typeSelectlist;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conentView = inflater.inflate(R.layout.popwindow, null);
        // 设置PopupWindow的View
        this.setContentView(conentView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.listView = conentView.findViewById(R.id.lv_list);
        //设置适配器
        this.selectAdapter = new ActionSelectPopularAdapter(typeSelectlist, context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        llParent = conentView.findViewById(R.id.llParent);
        SpacingDecoration itemDecoration = new SpacingDecoration(context, 1);
        this.listView.addItemDecoration(itemDecoration);
        this.listView.setAdapter(selectAdapter);
        this.selectAdapter.setOnItemClickerListener(new ActionSelectPopularAdapter.OnItemClickerListener() {
            @Override
            public void onItemClick(int position) {
                if (isShowing()) {
                    dismiss();
                }
                onItemListener.OnItemListener(position, typeSelectlist.get(position));
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    //设置数据
    public void setDataSource(List<OrderActionBtnEntity> typeSelectlist) {
        this.typeSelectlist = typeSelectlist;
        this.selectAdapter.notifyDataSetChanged();
    }

    public void showPopupWindow(View v) {
        v.getLocationOnScreen(location); //获取控件的位置坐标
        //获取自身的长宽高
        conentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if (location[1] > DensityUtil.INSTANCE.getScreenHeight(v.getContext()) / 2 + 100) {
            //若是控件的y轴位置大于屏幕高度的一半，向上弹出，
            llParent.setBackground(v.getContext().getDrawable(R.drawable.order_doctor_more_up_bg));
            //显示指定控件的上方
            // 偏移距离根据UI的慢慢调整
            this.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - DensityUtil.INSTANCE.dp2px(10), location[1] - listView.getMeasuredHeight()-DensityUtil.INSTANCE.dp2px(17));
        } else {
            llParent.setBackground(v.getContext().getDrawable(R.drawable.order_doctor_more_down_bg));
            //显示指定控件的下方
            // 偏移距离根据UI的慢慢调整
            this.showAsDropDown(v, 0- DensityUtil.INSTANCE.dp2px(10), 0);
        }
    }

}
