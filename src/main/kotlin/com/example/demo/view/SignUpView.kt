package com.example.demo.view

import com.example.demo.controller.EmployeeController
import com.example.demo.controller.TimeController
import com.example.demo.model.Employee
import com.example.demo.model.EmployeeModel
//import com.example.demo.view.EmployeeView
import tornadofx.*

class SignUpView : View("EmployeeClock") {
    private val employeeModel : EmployeeModel by inject()
    private val employeeController : EmployeeController by inject()


    override val root =form {

        fieldset ( "Login in"){
            field("Employee Email:"){

                textfield(employeeModel.employeeEmail){
                    promptText="abc@company.com"
                }.required()
                button("Login"){
                    action {
                        employeeController.login(employeeModel.employeeEmail)

                    }
                }
            }

        }

        fieldset ( "Employee Registration"){

            button("Sign Up"){
                action {
                    employeeController.navToRegistrationView()
                }
            }

        }
    }
}
