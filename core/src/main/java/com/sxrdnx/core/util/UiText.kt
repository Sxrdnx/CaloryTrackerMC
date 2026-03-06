package com.sxrdnx.core.util

import android.content.Context

sealed class UiText {
    data class DynamicString(val text: String):UiText ()

    data class StringResource( val resId: Int): UiText()

    fun asString(contex: Context):String{
        return when(this){
            is DynamicString -> text
            is StringResource -> contex.getString(resId)
        }
    }
}