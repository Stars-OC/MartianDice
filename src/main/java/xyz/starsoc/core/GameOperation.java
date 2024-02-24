package xyz.starsoc.core;


import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import xyz.starsoc.MartianDice;
import xyz.starsoc.core.impl.GameOperationImpl;
import xyz.starsoc.file.Config;
import xyz.starsoc.file.Message;
import xyz.starsoc.pojo.Dices;
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
        long userId = take.getUserId();
        if (take.getCommand().equals("info")){
            getUserInfo(userId);
            return false;
        }

        if (userId != gameData.getPlayingPlayer()) {
            sendMessage(message.getFailCommand(), userId);
            return false;
        }
        String[] commands = take.getCommand().split(" ");
        switch (commands[0]){
            case "roll":
                return roll(userId);

            case "lock":
                if (commands.length != 2) {
                    sendMessage("参数不足", userId);
                    break;
                }
                lock(userId,commands[1]);
                break;
            case "total":
                getUserTotal(userId);
                return true;
        }
        return false;
    }

    private void getUserTotal(long userId) {
        User user = playerList.get(userId);
        Dices resultDices = user.getResultDices();
        if (resultDices.getSize() != 0){
            sendMessage(message.getFailTotal());
            return;
        }
        Dices lockDices = user.getLockDices();
        int diceFour = lockDices.getDiceFour();
        int diceSix = lockDices.getDiceSix();
        int diceOne = lockDices.getDiceOne();
        int diceTwo = lockDices.getDiceTwo();
        int diceThree = lockDices.getDiceThree();

        int score = 0;
        if (diceOne > 0 && diceTwo > 0 &&  diceThree > 0) score += 3;
        score = score + diceThree * 5 + diceOne + diceTwo;
        if (diceFour < diceSix) score = 0;

        user.setScore(score);

        sendMessage(message.getSuccessTotal()
                .replace("%diceOne%", String.valueOf(diceOne))
                .replace("%diceTwo%", String.valueOf(diceTwo))
                .replace("%diceThree%", String.valueOf(diceThree))
                .replace("%diceFour%", String.valueOf(diceFour))
                .replace("%diceSix%", String.valueOf(diceSix))
                .replace("%lockSize%", String.valueOf(lockDices.getSize())
                .replace("%score%",user.getScore() + ""))
                , userId);
        if (user.getScore() > 25){
            gameData.setWillEnd(true);
            sendMessage(message.getWillEnd());
        }
    }

    private void getUserInfo(long userId) {
        User user = playerList.get(userId);
    }

    private void lock(long userId, String command) {
        User user = playerList.get(userId);
        if (user.getResultDices().getDiceSix() != 0){
            sendMessage(message.getFailLock().replace("%dice%", "6"), userId);
            return;
        }
        int dice;
        int result = 0;
        try{
            Dices lockDices = user.getLockDices();
            dice = Integer.parseInt(command);
            Dices resultDices = user.getResultDices();

            switch (dice){
                case 1:
                    result = resultDices.getDiceOne();
                    if (lockDices.getDiceOne() != 0){
                        sendMessage(message.getFailLock().replace("%dice%", dice+""), userId);
                        return;
                    }
                    lockDices.setDiceOne(result);
                    break;
                case 2:
                    result = resultDices.getDiceTwo();
                    if (lockDices.getDiceTwo() != 0){
                        sendMessage(message.getFailLock().replace("%dice%", dice+""), userId);
                        return;
                    }
                    lockDices.setDiceTwo(result);
                    break;
                case 3:
                    result = resultDices.getDiceThree();
                    if (lockDices.getDiceThree() != 0){
                        sendMessage(message.getFailLock().replace("%dice%", dice+""), userId);
                        return;
                    }
                    lockDices.setDiceThree(result);
                    break;
                case 4:
                case 5:
                    result = resultDices.getDiceFour();
                    lockDices.setDiceFour(result);
                    break;
                case 6:
                    result = resultDices.getDiceSix();
                    lockDices.setDiceSix(result);
                    break;
                default:
                    sendMessage("参数错误", userId);
            }
        }catch (Exception e){
            sendMessage("参数错误", userId);
            return;
        }
        sendMessage(message.getSuccessLock()
                        .replace("%dice%", String.valueOf(dice))
                        .replace("%diceSize%", String.valueOf(result))
                ,userId);
    }

    private boolean roll(long userId) {
        User user = playerList.get(userId);
        Dices lockDices = user.getLockDices();
        int lockSize = lockDices.getSize();
        int resultSize = 13 - lockSize;
        if (resultSize == 0) {
            sendMessage(message.getFailRoll(), userId);
            getUserTotal(userId);
            return true;
        }
        Dices resultDices = user.getResultDices();
        for (int i = 0; i < resultSize; i++){
            int dice = (int) (Math.random() * 6) + 1;
            switch (dice){
                case 1:
                    resultDices.setDiceOne(dice);
                    break;
                case 2:
                    resultDices.setDiceTwo(dice);
                    break;
                case 3:
                    resultDices.setDiceThree(dice);
                    break;
                case 4:
                case 5:
                    resultDices.setDiceFour(dice);
                    break;
                case 6:
                    resultDices.setDiceSix(dice);
                    lockDices.setDiceSix(dice);
            }
        }
        sendMessage(message.getSuccessRoll()
                .replace("%diceOne%", String.valueOf(resultDices.getDiceOne()))
                .replace("%diceTwo%", String.valueOf(resultDices.getDiceTwo()))
                .replace("%diceThree%", String.valueOf(resultDices.getDiceThree()))
                .replace("%diceFour%", String.valueOf(resultDices.getDiceFour()))
                .replace("%diceSix%", String.valueOf(resultDices.getDiceSix()))
                .replace("%lockSize%", String.valueOf(lockDices.getSize()))
                ,userId);
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

    private void sendMessage(MessageChain msg){
        Group group = getGroup();
        if (group == null) return;
        group.sendMessage(msg);
    }

    private void sendMessage(String msg, long userId){
        At at = new At(userId);
        MessageChain msgChain = at.plus(msg);
        sendMessage(msgChain);
    }

    private void sendMessage(String msg){
        Group group = getGroup();
        if (group == null) return;
        group.sendMessage(msg);
    }

    @Override
    public void kickPlayer(long userId) {

        playerList.remove(userId);
        gameData.getPlayOrder().remove(userId);
        sendMessage(message.getSuccessKick().replace("%player%", String.valueOf(userId)));
    }

    @Override
    public void guideGame(long userId) {
        User user = playerList.get(userId);
        Dices resultDices = user.getResultDices();
        if (resultDices.getSize() == 0)  sendMessage(message.getFirstGuide(), userId);
        if (resultDices.getDiceSix() == 0) sendMessage(message.getLockGuide(), userId);
        else sendMessage(message.getNoLockGuide(), userId);
    }

    @Override
    public void endGame() {

    }
}
