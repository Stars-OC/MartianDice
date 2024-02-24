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

public class GameRunner implements GameRunnerImpl{

    private static final GameData gameData = GameData.INSTANCE;

    private static final GameOperationImpl operation = GameOperation.INSTANCE;



    @Override
    public void init(){
        System.out.println("初始化");
    }

    @Override
    public void start() {
        System.out.println("开始");
        // 获取执行顺序
        gameData.getPlayerList().forEach((id, user) -> {
            gameData.getPlayOrder().add(id);
        });


    }

    @Override
    public void run() {

        init();
        start();
        ArrayBlockingQueue<GameCommand> commands = gameData.getCommands();
        ArrayList<Long> playOrder = gameData.getPlayOrder();

        // 设置第一个执行顺序
        int order = 0;

        while (true) {
            System.out.println("run 保活 执行" + order);
            long userId = playOrder.get(order);
            gameData.setPlayingPlayer(userId);
            operation.guideGame(userId);
            try {
                GameCommand take = commands.poll(60, TimeUnit.SECONDS);
                if (take == null){
                    // 玩家操作超时
                    order++;
                    operation.kickPlayer(userId);
                    continue;
                }
                if (take.getCommand().equals("stop") && userId == 0){
                    // 管理员停止游戏
                    break;
                }
                if (operation.runCommand(take)) {
                    // 结算
                    order++;
                }

            } catch (InterruptedException ignore) {}
            if (order >= playOrder.size()) {
                // 游戏结束
                break;
            }
        }

        stop();
        destroy();

    }


    @Override
    public void stop() {
        operation.endGame();
        operation.saveData();
    }

    @Override
    public void destroy() {
        gameData.getPlayerList().clear();
        gameData.getCommands().clear();
        gameData.getPlayOrder().clear();
        gameData.setPlayGroupId(0);
        gameData.setPlayingPlayer(0);
        gameData.setPlayBotId(0);
    }




}
