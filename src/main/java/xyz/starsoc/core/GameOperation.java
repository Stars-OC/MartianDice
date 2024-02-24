package xyz.starsoc.core;

import xyz.starsoc.core.impl.GameOperationImpl;
import xyz.starsoc.file.Message;
import xyz.starsoc.pojo.GameCommand;

public class GameOperation implements GameOperationImpl {

    public static final GameOperationImpl INSTANCE = new GameOperation();

    private static final Message message = Message.INSTANCE;

    @Override
    public void startGame() {
        GameRunner runner = new GameRunner();
        Thread thread = new Thread(runner);
        thread.start();
    }

    @Override
    public String createGame(long payerId) {
        return null;
    }

    @Override
    public String joinGame(long id) {
        return null;
    }

    @Override
    public String leaveGame(long id) {
        return null;
    }

    @Override
    public String infoGame(long groupId) {
        return null;
    }

    @Override
    public String stopGame(long groupId) {
        return null;
    }

    @Override
    public String startGame(long groupId) {
        return null;
    }

    @Override
    public boolean runCommand(GameCommand take) {
        return false;
    }

    @Override
    public void saveData() {

    }
}
