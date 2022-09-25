package com.example.demo.controller

import com.example.demo.model.Time
import com.example.demo.model.TimeModel
import com.example.demo.model.EmployeeClock
import javafx.util.Duration
import tornadofx.Controller
import tornadofx.getProperty
import tornadofx.mutateOnChange
import tornadofx.runLater
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask
import tornadofx.Rest
import java.time.format.DateTimeFormatter
import javax.json.JsonObject
class TimeController : Controller() {

    val api: Rest by inject()

    val timeModel = TimeModel()

    var dateTimeFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
    var time = Time(LocalDateTime.now().format(dateTimeFormatter).toString())
    var _employeeClock:EmployeeClock= EmployeeClock()

    init {
        refreshView();
    }

    fun refreshView(){
        val refreshRunnable = {

            var time = Time(LocalDateTime.now().format(dateTimeFormatter).toString())

            timeModel.itemProperty.set(time)
            timeModel.itemProperty.get().currentTime

            println(timeModel.currentTime.value)
        }
        val executor = Executors.newScheduledThreadPool(1)
        executor.scheduleAtFixedRate(refreshRunnable, 0, 12, TimeUnit.SECONDS)
    }

    fun isCheckCall(isCheckOut:Boolean){
        _employeeClock.currentTime = time.currentTime
        _employeeClock.isCheckOut=isCheckOut

        getResponse(insertEmployeeClock(_employeeClock))
    }

    fun getResponse(response:JsonObject){
        val lastCheck = if(response.getBoolean("isCheckOut")) "Last CheckOut:\n ${response.getString("currentTime")}" else
            "Last CheckIn:\n ${response.getString("currentTime")}"
        _employeeClock.currentTime=lastCheck
        _employeeClock.isCheckOut= response.getBoolean("isCheckOut")

    }

    fun insertEmployeeClock(employeeClock: EmployeeClock) = api.post("${api.baseURI}", employeeClock) {
                request -> request.addHeader("Content-Type", "application/json; charset=utf-8")
    }.one().getJsonObject("empCheck")

}