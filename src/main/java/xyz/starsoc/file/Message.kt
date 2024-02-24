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
    val alreadyCreate by value("创建失败，游戏已经创建了 你可以输入!md join加入游戏")
    val alreadyStart by value("操作失败，游戏已经开始了")
    val successStart by value("开始游戏成功 当前队列为 %nowPlayers%/%maxPlayers%")
    val willStart by value("当前游戏可以输入!md start 开始游戏")
    val failStart by value("开始失败，游戏人数不满%minPlayers%/或者游戏还没创建")
    val successKick by value("玩家 %player% 操作超时， 系统将其踢出游戏")
    val failCommand by value("当前不是你的回合，无法执行该操作")

    val successRoll by value("你成功将骰子扔出，结果是: \n 人: %diceOne%  牛: %diceTwo%  鸡: %diceThree% \n 激光: %diceFour% 坦克: %diceSix%\n%diceSix% 坦克将被锁定，当前锁定数量 %lockSize%")
    val failRoll by value("你已经把骰子都扔了，不能再扔了，已帮你跳过回合")
    val successLock by value("你成功将%dice%锁定，锁定的数量为 %diceSize%")
    val failLock by value("你已经锁定过%dice%了，不能再锁定了\n" +
            "‘1’是人面，‘2’是牛面，‘3’是鸡面，‘4’是激光面，‘6’是坦克面")
    val successTotal by value("你结束了你的回合，你的分数为 %score%，结果为\n" +
            " 人: %diceOne%  牛: %diceTwo%  鸡: %diceThree% \n" +
            " 激光: %diceFour% 坦克: %diceSix%\n" +
            " 你总共锁定了 %lockSize% 骰子")
    val failTotal by value("你上回合投的骰子还没有进行操作，不能进行结束你的回合")

    val willEnd by value("已有玩家超过 25 分，游戏在此大轮轮完后进行结束游戏")

    val firstGuide by value("轮到你的回合，请@我 发送 roll 投掷骰子")
    val lockGuide by value("轮到你的回合， 你投到了坦克面 导致骰子锁定了\n" +
            "请@我 发送 roll 再次投掷骰子\n" +
            "或者发送 total 计算分数(会自动将你剩余的骰子进行计算)\n" +
            "‘1’是人面，‘2’是牛面，‘3’是鸡面，‘4’是激光面，‘6’是坦克面")
    val noLockGuide by value("轮到你的回合，请@我 发送 lock 骰子面(1,2,3,4,6) 锁定骰子\n你也可以发送 roll 再次投掷骰子\n或者发送 total 计算分数(会自动将你剩余的骰子进行计算)" +
            "\n‘1’是人面，‘2’是牛面，‘3’是鸡面，‘4’是激光面，‘6’是坦克面")

    val successEnd by value("游戏结束，游戏结果如下\n" + "%result%")
    val noPermission by value("你没有权限执行该操作")
}