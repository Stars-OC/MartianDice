package xyz.starsoc.event;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import xyz.starsoc.core.GameData;
import xyz.starsoc.pojo.GameCommand;

import java.util.concurrent.ArrayBlockingQueue;

public class GameEvent extends SimpleListenerHost {

    private final GameData gameData = GameData.INSTANCE;

    private final ArrayBlockingQueue<GameCommand> commands = gameData.getCommands();

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) throws InterruptedException {
        Group group = event.getGroup();
        Member sender = event.getSender();
        long id = sender.getId();
        long groupId = group.getId();

        if (groupId != gameData.getPlayGroupId()) return;
        if (!gameData.getPlayerList().containsKey(id)) return;

        String plaintext = "";
        boolean at = false;
        for (SingleMessage message : event.getMessage()){
            if (message instanceof At && ((At) message).getTarget() == event.getBot().getId()) {
                at = true;
            }
            if (message instanceof PlainText && at){
                plaintext = message.contentToString();
            }
        }
        if (plaintext.equals("")) return;
        group.sendMessage(plaintext.substring(2));
        // 交给operation 处理
        commands.put(new GameCommand(id, plaintext.substring(2)));
    }
}
