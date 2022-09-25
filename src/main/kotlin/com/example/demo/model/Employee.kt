package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import javax.json.JsonObject

class Employee:JsonModel {

    var employeeID by property<String>()
    fun employeeIDProperty()=getProperty(Employee::employeeID)

    var employeeName by property<String>()
    fun employeeNameProperty()=getProperty(Employee::employeeName)

    var employeeEmail by property<String>()
    fun employeeEmailProperty()=getProperty(Employee::employeeEmail)

    val employeeClockList = FXCollections.observableArrayList<EmployeeClock>()


    override fun updateModel(json: JsonObject){
        with(json){
            employeeID= string("employeeID")
            employeeName = string("employeeName")
            employeeEmail = string("employeeEmail")
            employeeClockList
                .setAll(getJsonArray("employeeClockList").toModel())
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json){
            add("employeeID",employeeID)
            add("employeeName",employeeName)
            add("employeeEmail",employeeEmail)
            add("employeeClockList",employeeClockList.toJSON())

        }
    }


}


