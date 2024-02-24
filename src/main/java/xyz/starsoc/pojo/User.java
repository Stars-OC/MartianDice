package xyz.starsoc.pojo;

public class User {
    private long userId;
    private Dices dices;

    public User(long id) {
        this.userId = id;
        this.dices = new Dices();
    }

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Dices getDices() {
        return dices;
    }

    public void setDices(Dices dices) {
        this.dices = dices;
    }
}
