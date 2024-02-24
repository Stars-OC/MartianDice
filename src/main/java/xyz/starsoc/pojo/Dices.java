package xyz.starsoc.pojo;

public class Dices {
    private int DiceOne;
    private int DiceTwo;
    private int DiceThree;
    private int DiceFour;
    private int DiceSix;
    private int size;


    public int getDiceOne() {
        return DiceOne;
    }

    public void setDiceOne(int diceOne) {
        DiceOne += diceOne;
        size += diceOne;
    }

    public int getDiceTwo() {
        return DiceTwo;
    }

    public void setDiceTwo(int diceTwo) {
        DiceTwo += diceTwo;
        size += diceTwo;
    }

    public int getDiceThree() {
        return DiceThree;
    }

    public void setDiceThree(int diceThree) {
        DiceThree += diceThree;
        size += diceThree;
    }

    public int getDiceFour() {
        return DiceFour;
    }

    public void setDiceFour(int diceFour) {
        DiceFour += diceFour;
        size += diceFour;
    }

    public int getDiceSix() {
        return DiceSix;
    }

    public void setDiceSix(int diceSix) {
        DiceSix += diceSix;
        size += diceSix;
    }

    public int getSize() {
        return size;
    }


}
