package xyz.starsoc.pojo;

public class GameCommand {
    private long userId;
    private String command;

    public GameCommand() {
    }

    public GameCommand(long userId, String command) {
        this.userId = userId;
        this.command = command;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
