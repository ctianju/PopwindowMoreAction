package com.meta.myapplication.entity

// 订单的操作按钮
class OrderActionBtnEntity {
    var color: String? = null
    var type: Int = -1 //
    var name: String? = null //

    constructor(type: Int, name: String?,color: String? ) {
        this.type = type
        this.name = name
        this.color = color
    }
}