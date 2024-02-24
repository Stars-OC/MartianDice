package xyz.starsoc.pojo;

public class User {

    private long userId;

    private Dices lockDices;

    private Dices resultDices;

    private int score = 0;

    public User(long id) {
        this.userId = id;
        this.lockDices = new Dices();
        this.resultDices = new Dices();
    }

    public User() {
    }

    public Dices getResultDices() {
        return resultDices;
    }

    public void setResultDices(Dices resultDices) {
        this.resultDices = resultDices;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Dices getLockDices() {
        return lockDices;
    }

    public void setLockDices(Dices lockDices) {
        this.lockDices = lockDices;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
