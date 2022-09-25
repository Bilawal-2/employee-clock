package com.example.demo.view

import com.example.demo.controller.EmployeeController
import com.example.demo.model.EmployeeModel
import tornadofx.*

class RegistrationView : View("Employee Clock") {
    private val employeeModel : EmployeeModel by inject()
    private val employeeController : EmployeeController by inject()

    override val root = form {

        fieldset ( "Registration"){

            field("Full Name:"){

                textfield(employeeModel.employeeName){
                    promptText="Employee Name"
                }.required()

            }

            field("Employee Email:"){

                textfield(employeeModel.employeeEmail){
                    promptText="abc@company.com"
                }.required()

            }

            button("Signup"){
                action {
                    employeeController.signup(employeeModel)

                }
            }
        }


    }

}
