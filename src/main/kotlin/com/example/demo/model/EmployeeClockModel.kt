package com.example.demo.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.StringProperty
import tornadofx.*

class EmployeeClockModel : ItemViewModel<EmployeeClock>(EmployeeClock()){


    val employeeID: StringProperty = bind {item?.employeeIDProperty()}
    val isCheckOut: BooleanProperty = bind {item?.isCheckOutProperty()}
    val currentTime: StringProperty = bind {item?.currentTimeProperty()}




}