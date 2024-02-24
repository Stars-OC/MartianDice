package xyz.starsoc.core;


import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import xyz.starsoc.MartianDice;
import xyz.starsoc.core.impl.GameOperationImpl;
import xyz.starsoc.file.Config;
import xyz.starsoc.file.Message;
import xyz.starsoc.pojo.GameCommand;
import xyz.starsoc.pojo.User;

import java.util.HashMap;

public class GameOperation implements GameOperationImpl {

    public static final GameOperationImpl INSTANCE = new GameOperation();

    private static final GameData gameData = GameData.INSTANCE;

    private static final HashMap<Long, User> playerList = gameData.getPlayerList();
    private static final Message message = Message.INSTANCE;

    @Override
    public void gameStart() {
        GameRunner runner = new GameRunner();
        Thread thread = new Thread(runner);
        thread.start();
    }

    @Override
    public String createGame(long groupId, long id) {
        if (!gameData.getPlayOrder().isEmpty()) return message.getAlreadyStart();
        playerList.clear();
        gameData.getCommands().clear();
        gameData.getPlayOrder().clear();
        gameData.getPlayerList().put(id, new User(id));
        gameData.setPlayGroupId(groupId);
        return message.getSuccessCreate().replace("%maxPlayers%", String.valueOf(gameData.getMaxPlayers()));
    }

    @Override
    public String joinGame(long id) {
        if (!gameData.getPlayOrder().isEmpty()) return message.getAlreadyStart();
        if (playerList.containsKey(id) || playerList.size()>=gameData.getMaxPlayers()) return message.getFailJoin();

        playerList.put(id, new User(id));
        String msg = playerList.size()>=gameData.getMinPlayers()?message.getSuccessJoin():message.getSuccessJoin() + "\n" + message.getWillStart();
        return msg
                .replace("%maxPlayers%", String.valueOf(gameData.getMaxPlayers()))
                .replace("%nowPlayers%", String.valueOf(playerList.size()));
    }

    @Override
    public String leaveGame(long id) {
        if (!gameData.getPlayOrder().isEmpty()) return message.getAlreadyStart();
        if (!playerList.containsKey(id)) return message.getFailLeave();
        playerList.remove(id);
        return message.getSuccessLeave()
                .replace("%maxPlayers%", String.valueOf(gameData.getMaxPlayers()))
                .replace("%nowPlayers%", String.valueOf(playerList.size()));
    }

    @Override
    public String infoGame(long groupId) {
        return null;
    }

    @Override
    public void stopGame() {
        sendMessage("当前游戏被强制停止");
    }

    @Override
    public String startGame() {
        if (!gameData.getPlayOrder().isEmpty()) return message.getAlreadyStart();
        if (gameData.getMinPlayers() > playerList.size()) return message.getFailStart().replace("%minPlayers%", String.valueOf(gameData.getMinPlayers()));
        gameStart();
        return message.getSuccessStart()
                .replace("%maxPlayers%", String.valueOf(gameData.getMaxPlayers()))
                .replace("%nowPlayers%", String.valueOf(playerList.size()));
    }

    @Override
    public boolean runCommand(GameCommand take) {
        return false;
    }

    @Override
    public void saveData() {

    }

    private static Bot getBot() {
        return Bot.getInstanceOrNull(Config.INSTANCE.getBot());
    }

    private Group getGroup() {
        Bot bot = getBot();
        if (bot == null) return null;
        return bot.getGroup(gameData.getPlayGroupId());
    }

    private void sendMessage(String msg){
        Group group = getGroup();
        if (group == null) return;
        group.sendMessage(msg);
    }

    @Override
    public void kickPlayer(long userId) {

        playerList.remove(userId);
        gameData.getPlayOrder().forEach(user -> {
            if (user.getUserId() == userId) gameData.getPlayOrder().remove(user);
        });
        sendMessage(message.getSuccessKick().replace("%player%", String.valueOf(userId)));
    }
}
