import gs.BalanceTest;
import gs.GameServer;
import ui.GameClient;

public class Main {
    public static void main(String[] args) {

        //testRTP();
        play();
    }

    private static void play(){ // mode with "GUI" <- xD I said it, it was to be for rain code so... it is what it is
        GameServer gameServer = new GameServer();
        GameClient ui = new GameClient(gameServer);
        ui.run();
    }

    private static void testRTP(){
       //1000 milions, ~bilion maintaining RTP: 0.960
        BalanceTest.test(1000, 0.01, true); //enum would be better
        BalanceTest.test(1000, 0.5, true);
        BalanceTest.test(1000, 12, true);
        BalanceTest.test(1000, 0.22, false);
        BalanceTest.test(1000, 13, false);
        BalanceTest.test(1000, 0.01, false);
    }

}




