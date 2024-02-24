package xyz.starsoc.file

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import xyz.starsoc.pojo.UserData

object Data : AutoSavePluginConfig("data") {
    val userInfo : Map<Long, UserData> by value(mutableMapOf())
}