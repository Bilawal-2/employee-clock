package com.example.demo.model

import tornadofx.*
import javax.json.JsonObject
import tornadofx.Rest

class EmployeeClock : JsonModel {


    var employeeID by property<String>()
    fun employeeIDProperty()=getProperty(EmployeeClock::employeeID)

    var isCheckOut by property<Boolean>()
    fun isCheckOutProperty()=getProperty(EmployeeClock::isCheckOut)

    var currentTime by property<String>()
    fun currentTimeProperty() = getProperty(EmployeeClock::currentTime)

    override fun toString()  = employeeID

    override fun updateModel(json: JsonObject){
        with(json){
            employeeID= string("employeeID")
            isCheckOut = boolean("employeeIn")
            currentTime = string("currentTime")
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json){
            add("employeeID",employeeID)
            add("isCheckOut",isCheckOut)
            add("currentTime",currentTime)

        }
    }

}