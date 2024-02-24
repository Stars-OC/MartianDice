package xyz.starsoc.core;

import xyz.starsoc.core.impl.GameOperationImpl;
import xyz.starsoc.core.impl.GameRunnerImpl;
import xyz.starsoc.file.Config;
import xyz.starsoc.pojo.GameCommand;
import xyz.starsoc.pojo.User;

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
    public void create() {
        gameData.getPlayerList().clear();
        gameData.getCommands().clear();
    }

    @Override
    public void run() {
        init();
        create();
        ArrayBlockingQueue<GameCommand> commands = gameData.getCommands();
        try {
            while (true) {
                GameCommand take = commands.poll(30, TimeUnit.SECONDS);
                if (operation.runCommand(take)) {
                    // 如果操作成功，可以考虑返回或退出循环
                    return; // 或者使用 break
                }
            }
        } catch (InterruptedException ignored) {
        } finally {
            // 确保在退出之前执行资源清理
            stop();
            destroy();
        }
    }


    @Override
    public void stop() {
        operation.saveData();
    }

    @Override
    public void destroy() {
        gameData.getPlayerList().clear();
        gameData.getCommands().clear();
        gameData.setPlayGroupId(0);
        gameData.setPlayingPlayer(0);
        gameData.setPlayerOperation(0);
    }




}
