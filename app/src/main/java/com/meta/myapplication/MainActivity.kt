package com.meta.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.meta.myapplication.adapter.MainRvAdapter
import com.meta.myapplication.entity.OrderActionBtnEntity
import com.meta.myapplication.entity.OrderEntity
import com.meta.myapplication.utils.SpacingDecoration
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    private val normalButtonsList = mutableListOf<OrderActionBtnEntity>()
    private val normalButtonsList2 = mutableListOf<OrderActionBtnEntity>()
    private val normalButtonsList3 = mutableListOf<OrderActionBtnEntity>()

    private val moreButtonsList = mutableListOf<OrderActionBtnEntity>()
    private val moreButtonsList2 = mutableListOf<OrderActionBtnEntity>()
    private val moreButtonsList3 = mutableListOf<OrderActionBtnEntity>()

    private val dataList = mutableListOf<OrderEntity>()

    companion object {
        const val DELETE_TYPE = 1 // 删除按钮类型
        const val GO_SHOPPING_CART_TYPE = 2 // 再次购买按钮
        const val MAKE_AN_INVOICE_TYPE = 3   // 去咨询
        const val MAKE_AN_SHARE_MORE_TYPE = 4  // 去咨询
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()

        val adapter = MainRvAdapter(dataList, this)

        mainRvList.layoutManager = LinearLayoutManager(this)
        val spacingDecoration = SpacingDecoration(this, 10)
        spacingDecoration.setOutSpacing(this, 15, 12, 15, 12)
        mainRvList.addItemDecoration(spacingDecoration)

        mainRvList.adapter = adapter
    }

    // 初始化数据源
    private fun initData() {
        // 普通按钮
        normalButtonsList.add(OrderActionBtnEntity(DELETE_TYPE, "支付", "#999999"))
        normalButtonsList.add(OrderActionBtnEntity(DELETE_TYPE, "在线咨询", "#999999"))
        normalButtonsList.add(OrderActionBtnEntity(DELETE_TYPE, "分享", "#999999"))
        normalButtonsList.add(OrderActionBtnEntity(MAKE_AN_SHARE_MORE_TYPE, "分享更多", "#999999"))
        // 普通按钮2
        normalButtonsList2.add(OrderActionBtnEntity(DELETE_TYPE, "支付", "#999999"))
        normalButtonsList2.add(OrderActionBtnEntity(DELETE_TYPE, "在线咨询", "#999999"))
        // 普通按钮3
        normalButtonsList3.add(OrderActionBtnEntity(DELETE_TYPE, "支付", "#999999"))
        normalButtonsList3.add(OrderActionBtnEntity(DELETE_TYPE, "分享", "#999999"))
        // 更多按钮
        moreButtonsList.add(OrderActionBtnEntity(DELETE_TYPE, "删除订单", "#999999"))
        moreButtonsList.add(OrderActionBtnEntity(GO_SHOPPING_CART_TYPE, "加入购物车", "#999999"))
        moreButtonsList.add(OrderActionBtnEntity(MAKE_AN_INVOICE_TYPE, "申请开票", "#999999"))
        // 更多按钮2
        moreButtonsList2.add(OrderActionBtnEntity(DELETE_TYPE, "删除订单", "#999999"))
        moreButtonsList2.add(OrderActionBtnEntity(MAKE_AN_INVOICE_TYPE, "申请开票", "#999999"))
        // 更多按钮3
        moreButtonsList3.add(OrderActionBtnEntity(DELETE_TYPE, "删除订单", "#999999"))

        dataList.add(OrderEntity("商品----1", "￥ 100.0 元", normalButtonsList, moreButtonsList))
        dataList.add(OrderEntity("商品----2", "￥ 10.0元", normalButtonsList2, moreButtonsList2))
        dataList.add(OrderEntity("商品----3", "￥11.0元", normalButtonsList3, moreButtonsList3))
        dataList.add(OrderEntity("商品----4", "￥ 1000.0元", normalButtonsList, moreButtonsList))
        dataList.add(OrderEntity("商品----5", "￥ 1000.0元", normalButtonsList, moreButtonsList))
        dataList.add(OrderEntity("商品----6", "￥ 1000.0元", normalButtonsList, moreButtonsList))
        dataList.add(OrderEntity("商品----7", "￥ 1000.0元", normalButtonsList, moreButtonsList))
    }

}