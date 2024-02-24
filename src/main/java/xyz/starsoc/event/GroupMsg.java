package xyz.starsoc.event;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import xyz.starsoc.MartianDice;
import xyz.starsoc.core.GameOperation;
import xyz.starsoc.core.impl.GameOperationImpl;
import xyz.starsoc.file.Config;
import xyz.starsoc.file.Message;

import java.util.Set;

public class GroupMsg extends SimpleListenerHost {

    private final Config config = Config.INSTANCE;

    private final Set<Long> permissionList = config.getPermissions();
    private final Set<Long> groupList = config.getEnableGroup();
    
    private final GameOperationImpl operation = GameOperation.INSTANCE;

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) {
        Group group = event.getGroup();
        Member sender = event.getSender();
        long id = sender.getId();
        long groupId = group.getId();
        if (!groupList.contains(groupId)){
            return;
        }
        String plain = event.getMessage().get(PlainText.Key).contentToString();
        if (!(plain.startsWith("!火星骰") || plain.startsWith("！火星骰"))){
            return;
        }

        String[] commands = plain.split(" ");
        switch (commands[1]){
            case "help":
                group.sendMessage(Message.INSTANCE.getHelp());
                return;
            case "create":
                group.sendMessage(operation.createGame(id));
                return;
            case "join":
                group.sendMessage(operation.joinGame(id));
                return;
            case "leave":
                group.sendMessage(operation.leaveGame(id));
                return;
            case "info":
                group.sendMessage(operation.infoGame(groupId));
                return;
        }

        if (!permissionList.contains(id)){
            return;
        }

        switch (commands[1]){
            case "stop":
                group.sendMessage(operation.stopGame(groupId));
                return;
            case "start":
                group.sendMessage(operation.startGame(groupId));
                return;
            case "addPer":
                return;
            case "reload":
                MartianDice.INSTANCE.reload();
                return;
            default:
                group.sendMessage(Message.INSTANCE.getHelp());
        }

    }
}
