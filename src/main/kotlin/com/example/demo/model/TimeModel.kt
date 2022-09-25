package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class TimeModel() : ItemViewModel<Time>() {
    val currentTime = bind{ SimpleStringProperty(item?.currentTime?:"")}
}