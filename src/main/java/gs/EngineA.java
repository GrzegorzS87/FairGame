package gs;
import util.Dice;

public class EngineA implements Engine {

    //best set of parameters for RTP 0.96 for bet 1
    //dont want it to be local for convenience
    private final int hitRate = 60000; // 60000 / 1000000 = 60%
    private final int basicWin = 86720;
    private final int bigWin = 99900;
    private final int epicWin = 99990;
    // ----*-----

    @Override
    public double round(double bet) { //min bet is 0.1

        double win;

        int roll = Dice.nextInt(0, 100000);

        if (roll < (100000 - hitRate)) {
            win = 0;
        } else if (roll < basicWin) {
            win = basicWinAmmount(bet);
        } else if (roll < bigWin) {
            win = basicWinAmmount(bet) + (bet * Dice.nextInt(0, 9));
        } else if (roll < epicWin) {
            win = basicWinAmmount(bet) + (bet * Dice.nextInt(10, 99));
        } else {
            win = basicWinAmmount(bet) + (bet * Dice.nextInt(100, 999));
        }

        return win;
    }

    private double basicWinAmmount(double bet) {
        bet = bet*1000.0;
        int minWin = (int) (0.1 * bet);
        double win = ((Dice.nextInt(1, 11) * minWin ) / 1000.0);
        return win;  // 10-110%
    }
}

//UI game idea?
//     _______________________________________________
//    |   _________________________________________   | \
//    |  | C:\>_                                   |  |  \
//    |  |    *-----*-----*-----*-----*-----*      |  |   \__
//    |  |    | EXP | ADD | MOV | POP | LEA |      |  |      |
//    |  |    | 001 | 010 | 100 | 101 | 111 |x10.0 |  |      |
//    |  |    |-----|-----|-----|-----|-----|      |  |      |
//    |  |    | ADD | SUB | INC | IMU | DIV |      |  |      |
//    |  |    | %dl | %ah | %bh | %ch | %al |x1.0  |  |      |
//    |  |    |-----|-----|-----|-----|-----|      |  |      |
//    |  |    | XOR | NEG | SHL | SHR | JMP |      |  |      |
//    |  |    | 404 | 501 | XXX | WAR | ERR |x0.1  |  |      |
//    |  |    *-----*-----*-----*-----*-----*      |  |    /
//    |  |_________________________________________|  |  /
//    |_______________________________________________|/
//      BALANCE: 100     BET: 1    press ENTER to spin