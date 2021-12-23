package com.meta.myapplication.entity

// 订单实体
class OrderEntity {
    var name:String?=null
    var price:String?=null
    var buttons: List<OrderActionBtnEntity>? = null // 订单的操作按钮
    var ext_buttons: List<OrderActionBtnEntity>? = null //更多的操作按钮，存在就得显示更多

    constructor(
        name: String?,
        price: String?,
        buttons: List<OrderActionBtnEntity>?,
        ext_buttons: List<OrderActionBtnEntity>?
    ) {
        this.name = name
        this.price = price
        this.buttons = buttons
        this.ext_buttons = ext_buttons
    }
}