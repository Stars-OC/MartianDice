package xyz.starsoc.core;

import xyz.starsoc.core.impl.GameOperationImpl;
import xyz.starsoc.core.impl.GameRunnerImpl;
import xyz.starsoc.file.Config;
import xyz.starsoc.pojo.GameCommand;
import xyz.starsoc.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class GameRunner implements GameRunnerImpl , Runnable{

    private static final GameData gameData = GameData.INSTANCE;

    private static final GameOperationImpl operation = GameOperation.INSTANCE;

    private static final Config config = Config.INSTANCE;

    @Override
    public void init(){
        gameData.setMinPlayers(config.getMinPlayers());
        gameData.setMaxPlayers(config.getMaxPlayers());
    }

    @Override
    public void start() {
        // 获取执行顺序
        gameData.getPlayerList().forEach((id, user) -> {
            gameData.getPlayOrder().add(user);
        });


    }

    @Override
    public void run() {

        init();
        start();
        ArrayBlockingQueue<GameCommand> commands = gameData.getCommands();
        ArrayList<User> playOrder = gameData.getPlayOrder();

        // 设置第一个执行顺序
        int order = 0;
        while (true) {
            User user = playOrder.get(order);
            gameData.setPlayingPlayer(user.getUserId());
            try {
                GameCommand take = commands.poll(30, TimeUnit.SECONDS);
                if (take == null){
                    // 玩家操作超时
                    order++;
                    operation.kickPlayer(user.getUserId());
                    continue;
                }
                if (take.getCommand().equals("stop") && user.getUserId() == 0){
                    break;
                }
                if (operation.runCommand(take)) {
                    // 结算
                    order++;
                }
            } catch (InterruptedException ignore) {}

        }

        stop();
        destroy();

    }


    @Override
    public void stop() {
        operation.saveData();
    }

    @Override
    public void destroy() {
        gameData.getPlayerList().clear();
        gameData.getCommands().clear();
        gameData.getPlayOrder().clear();
        gameData.setPlayGroupId(0);
        gameData.setPlayingPlayer(0);
        gameData.setPlayerOperation(0);
    }




}
