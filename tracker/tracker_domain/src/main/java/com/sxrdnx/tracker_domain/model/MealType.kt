package com.sxrdnx.tracker_domain.model

sealed class MealType (val name : String){
    object BreackFast:MealType("breakfast")
    object Lunch: MealType("lunch")
    object Dinner:MealType("dinner")
    object Snack: MealType("snack")


    companion object{
        fun fromString(name :String): MealType{
            return when(name){
                "breakfast"->BreackFast
                "lunch"->Lunch
                "dinner"->Dinner
                "snack"-> Snack
                else-> BreackFast
            }
        }
    }

}