package com.example.demo.utils

import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import tornadofx.alert

class Utilites {
    companion object{

        fun alertInfo( title: String, description: String): Alert {
           return alert(
                Alert.AlertType.INFORMATION,title,description,ButtonType.OK
            )
        }
    }
}