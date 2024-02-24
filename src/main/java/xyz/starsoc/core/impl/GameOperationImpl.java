package xyz.starsoc.core.impl;

import xyz.starsoc.pojo.GameCommand;

public interface GameOperationImpl {

    String startGame();

    void gameStart();

    String createGame(long groupId, long id);

    String joinGame(long id);

    String leaveGame(long id);

    String infoGame(long groupId);

    void stopGame();


    boolean runCommand(GameCommand take);

    void saveData();

    void kickPlayer(long userId);
}
