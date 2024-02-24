package xyz.starsoc.core.impl;

import xyz.starsoc.pojo.GameCommand;

public interface GameOperationImpl {

    void startGame();

    String createGame(long payerId);

    String joinGame(long id);

    String leaveGame(long id);

    String infoGame(long groupId);

    String stopGame(long groupId);

    String startGame(long groupId);

    boolean runCommand(GameCommand take);

    void saveData();
}
