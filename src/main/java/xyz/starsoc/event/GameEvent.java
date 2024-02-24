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

        MessageChain messages = event.getMessage();
        At at = (At) messages.get(At.Key);
        if (at == null || at.getTarget() != event.getBot().getId()) return;
        String plaintext = messages.get(PlainText.Key).contentToString();
        // 交给operation 处理
        commands.put(new GameCommand(id, plaintext));
    }
}
