package com.example.demo.app

import com.example.demo.view.SignUpView
import tornadofx.App
import tornadofx.Rest

class MyApp: App(SignUpView::class, Styles::class){
    val api: Rest by inject()

    init{
//        api.baseURI="http://localhost:3000/employees"
        api.baseURI="https://demo.com/employees"
    }
}