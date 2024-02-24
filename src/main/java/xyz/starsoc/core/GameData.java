package xyz.starsoc.core;

import xyz.starsoc.file.Config;
import xyz.starsoc.pojo.GameCommand;
import xyz.starsoc.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class GameData {

    public static final GameData INSTANCE = new GameData();

    private int maxPlayers;
    private int minPlayers;

    /** 玩家列表 */
    private final HashMap<Long, User> playerList = new HashMap<>();

    /** 玩家操作顺序 */
    private final ArrayList<User> playOrder = new ArrayList<>();

    /** 指令队列 */
    private final ArrayBlockingQueue<GameCommand> commands = new ArrayBlockingQueue<>(100);


    private long playGroupId;

    private long playingPlayer;

    private int playerOperation;

    public HashMap<Long, User> getPlayerList() {
        return playerList;
    }

    public ArrayList<User> getPlayOrder() {
        return playOrder;
    }

    public ArrayBlockingQueue<GameCommand> getCommands() {
        return commands;
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
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getPlayerOperation() {
        return playerOperation;
    }

    public void setPlayerOperation(int playerOperation) {
        this.playerOperation = playerOperation;
    }




}
