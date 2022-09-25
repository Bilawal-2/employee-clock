package com.example.demo.controller

import com.example.demo.utils.Utilites
import com.example.demo.model.Employee
import com.example.demo.model.EmployeeClock
import com.example.demo.model.EmployeeModel
import com.example.demo.view.EmployeeView
import com.example.demo.view.RegistrationView
import com.example.demo.view.SignUpView
import javafx.beans.property.StringProperty
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.alert
import tornadofx.*
import java.util.function.Predicate
import javax.json.JsonObject


class EmployeeController : Controller()  {
    val api: Rest by inject()
    lateinit var employee:Employee


    fun signup(employeeModel: EmployeeModel) {
        println("${employeeModel.employeeName.value}:${employeeModel.employeeEmail.value}")
        employee = Employee();
        employee.employeeName = employeeModel.employeeName.value
        employee.employeeEmail = employeeModel.employeeEmail.value

        if(insertEmployee(employee).getBoolean("hasUser"))
        {
            Utilites.Companion.alertInfo("Register","Employee is already exist.")
        }else{
            Utilites.Companion.alertInfo("Register",
                "Employee has been registered successfully.").button {
                find(RegistrationView::class).replaceWith(SignUpView::class,sizeToScene = true,centerOnScreen = true)
            }


        }

    }

    fun login(employeeEmail: StringProperty) {
        println("employeeEmail: ${employeeEmail.value}")
        var employeeClock:EmployeeClock = EmployeeClock()
        employeeClock.employeeID = employeeEmail.value

        var response:JsonObject?= if(loginCall(employeeClock)!=null) loginCall(employeeClock) else null

        if(response!=null){

            employeeClock.isCheckOut = response.getBoolean("isCheckOut")
            employeeClock.currentTime = response.getString("currentTime")
            val lastCheck = if(employeeClock.isCheckOut) "Last CheckOut ${employeeClock.currentTime}" else
                "Last CheckIn ${employeeClock.currentTime}"
            employeeClock.currentTime = lastCheck
            navigator(isValidUser(response,employeeClock.employeeID),employeeClock)


        } else{
            Utilites.Companion.alertInfo("Alert","User Not found")
        }
    }

    private fun insertEmployee(employee: Employee) = api.post("${api.baseURI}/registration", employee) { request ->
            request.addHeader("Content-Type", "application/json; charset=utf-8")

    }.one().getJsonObject("employee")

    private fun loginCall(employeeClock: EmployeeClock) = api.post("${api.baseURI}/login",employeeClock){ request ->
        request.addHeader("Content-Type", "application/json; charset=utf-8")

    }.one().getJsonObject("employee")

    private fun isValidUser(userObject:JsonObject ,employeeEmail:String):Boolean{
        var employeeID= userObject.getString("employeeID")
        return employeeID.equals(employeeEmail)
    }

    private fun navigator(isNavigate:Boolean, employeeClock: EmployeeClock){
        if(isNavigate) {

            val employeeViewNav = find<EmployeeView>(mapOf(EmployeeView::employeeClock to employeeClock))

            find(SignUpView::class).replaceWith(employeeViewNav, sizeToScene = true, centerOnScreen = true)
            find(EmployeeView::class).onNavigateForward()
        } else {
            Utilites.Companion.alertInfo("Invalid Email","Please enter valid email.")
        }
    }
    fun navToRegistrationView() = navigator()
    private fun navigator(){
        find(SignUpView::class).replaceWith(RegistrationView::class,sizeToScene = true,centerOnScreen = true)
    }
}