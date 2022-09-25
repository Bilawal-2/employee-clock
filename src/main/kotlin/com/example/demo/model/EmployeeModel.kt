package com.example.demo.model

import com.example.demo.model.Employee
import javafx.beans.property.StringProperty
import tornadofx.ItemViewModel

class EmployeeModel : ItemViewModel<Employee>(Employee()) {


    val employeeID: StringProperty = bind {item?.employeeIDProperty()}
    val employeeName: StringProperty = bind {item?.employeeNameProperty()}
    val employeeEmail: StringProperty = bind {item?.employeeEmailProperty()}


}