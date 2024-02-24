package xyz.starsoc.file

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object Message : AutoSavePluginConfig("message"){
    val help by value("====help====")
    val successCreate by value("创建游戏成功 当前队列为 1/%maxPlayers%")
    val successJoin by value("加入游戏成功 当前队列为 %nowPlayers%/%maxPlayers%")
    val failJoin by value("加入游戏失败 你已经加入了/当前游戏已满")
    val successLeave by value("离开游戏成功 当前队列为 %nowPlayers%/%maxPlayers%")
    val failLeave by value("离开游戏失败 你没有加入游戏")
    val alreadyStart by value("操作失败，游戏已经开始了")
    val successStart by value("开始游戏成功 当前队列为 %nowPlayers%/%maxPlayers%")
    val willStart by value("当前游戏可以输入!md start 开始游戏")
    val failStart by value("开始失败，游戏人数不满%minPlayers%/或者游戏还没创建")
    val successKick by value("玩家 %player% 操作超时， 系统将其踢出游戏")
    val noPermission by value("你没有权限执行该操作")
}