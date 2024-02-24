package xyz.starsoc.file

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value
import xyz.starsoc.file.Config.provideDelegate

object Config : AutoSavePluginConfig("config") {
    val bot : Long by value()
    @ValueDescription("最高权限")
    val master : Long by value()
    @ValueDescription("启用本插件的群聊")
    val enableGroup : Set<Long> by value(mutableSetOf())
    @ValueDescription("管理员权限")
    val permissions : Set<Long> by value(mutableSetOf())

    val minPlayers by value(2)
    val maxPlayers by value(10)
}