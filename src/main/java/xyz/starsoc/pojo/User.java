package xyz.starsoc.pojo;

import java.util.Comparator;

public class User implements Comparator<User> {

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Dices getLockDices() {
        return lockDices;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compare(User o1, User o2) {
        return o1.getScore() - o2.getScore();
    }
}
