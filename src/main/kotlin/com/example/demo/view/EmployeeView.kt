package com.example.demo.view


import com.example.demo.controller.*
import com.example.demo.model.EmployeeClockModel
import com.example.demo.model.Time
import com.example.demo.model.Employee
import com.example.demo.model.EmployeeClock
import tornadofx.*
import javafx.scene.control.Button

class EmployeeView : View("Employee Clock") {

    private val employeeClockModel : EmployeeClockModel by inject()
    private val timeController:TimeController by inject()
    val employeeClock: EmployeeClock by param()
    init {
        val employeeClock = params["employeeClock"] as? EmployeeClock
        if (employeeClock != null) {
            println("Bundle:$employeeClock")
            timeController._employeeClock = employeeClock;

            employeeClockModel.isCheckOut.bind((timeController._employeeClock.isCheckOutProperty()))
            employeeClockModel.currentTime.bind(timeController._employeeClock.currentTimeProperty())

        }
    }

    override val root = form {

        fieldset("Current Time"){
            println("current time: ${timeController.timeModel.currentTime.value}")
            val label= label("Start")
            runAsync {
                 label.textProperty().bind(timeController.timeModel.currentTime)

            }


        }
        fieldset ( "Employee Detail"){
            field("Employee email: "){
                textfield(employeeClock.employeeID){

                    isEditable=false;
                }
            }


        }

        fieldset ("Attendance"){

            field(employeeClock.currentTime).labelProperty.bind(employeeClockModel.currentTime)

            field("Employee In:"){

                button("Check In"){
                    action {
                        attendanceEntry(false,this)
                    }
                }.disableProperty().bind(!(employeeClockModel.isCheckOut))
            }
            field("Employee Out:"){
                println(employeeClockModel.isCheckOut)
                button("Check Out"){
                    action {
                        attendanceEntry(true,this)
                    }
                }.disableProperty().bind(employeeClockModel.isCheckOut)
            }
        }
    }
    private fun attendanceEntry(isCheck:Boolean,btn:Button){
        println(timeController._employeeClock)
        timeController.isCheckCall(isCheck)

    }
}