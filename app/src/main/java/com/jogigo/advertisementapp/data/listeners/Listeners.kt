package com.jogigo.advertisementapp.data.listeners

interface BaseListener {
    fun onError(error: String)
}
interface BasicListener: BaseListener {
    fun onOk()
}
interface IntListener: BaseListener {
    fun onOk(num: Int)
}
interface StringListener: BaseListener {
    fun onOk(value: String)
}
interface BooleanListener: BaseListener {
    fun onOk(value: Boolean)
}

