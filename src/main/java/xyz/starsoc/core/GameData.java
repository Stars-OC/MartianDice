package xyz.starsoc.core;

import xyz.starsoc.file.Config;
import xyz.starsoc.pojo.GameCommand;
import xyz.starsoc.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class GameData {

    public static final GameData INSTANCE = new GameData();
    private static final Config config = Config.INSTANCE;

    /** 玩家列表 -> 主要存储数据的地方 */
    private final HashMap<Long, User> playerList = new HashMap<>();

    /** 玩家操作顺序 */
    private final ArrayList<Long> playOrder = new ArrayList<>();

    /** 指令队列 */
    private final ArrayBlockingQueue<GameCommand> commands = new ArrayBlockingQueue<>(100);

    private long playBotId;

    private long playGroupId;

    private long playingPlayer;

    private boolean willEnd = false;

    public HashMap<Long, User> getPlayerList() {
        return playerList;
    }

    public ArrayList<Long> getPlayOrder() {
        return playOrder;
    }

    public ArrayBlockingQueue<GameCommand> getCommands() {
        return commands;
    }

    public long getPlayBotId() {
        return playBotId;
    }

    public void setPlayBotId(long playBotId) {
        this.playBotId = playBotId;
    }

    public long getPlayGroupId() {
        return playGroupId;
    }

    public void setPlayGroupId(long playGroupId) {
        this.playGroupId = playGroupId;
    }

    public long getPlayingPlayer() {
        return playingPlayer;
    }

    public void setPlayingPlayer(long playingPlayer) {
        this.playingPlayer = playingPlayer;
    }

    public int getMaxPlayers() {
        return config.getMaxPlayers();
    }

    public int getMinPlayers() {
        return config.getMinPlayers();
    }



    public boolean isWillEnd() {
        return willEnd;
    }

    public void setWillEnd(boolean willEnd) {
        this.willEnd = willEnd;
    }
}
