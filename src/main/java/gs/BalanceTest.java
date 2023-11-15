package gs;

public class BalanceTest {
    /*
        BalancingTest.test(1000000000, 1, true);
        BalancingTest.test(1000000000, 1, false);

        on a 1 bilion bets

        GS A
        RTP: 0.9600506808604168   max win: 1000.98   min win: 0.1

        GS B
        RTP: 0.9603907712899751   max win: 1000.99   min win: 0.1

         */

    private static Engine game;

    public static void test(int milionsOfRounds, double bet, boolean engineA){

        if(engineA)
            game = new EngineA();
        else
            game = new EngineB();

        double rtp = 0.0;
        for(int i=0;i<milionsOfRounds;i++)
            rtp += milionRoundsRTP(bet);

        rtp = rtp/milionsOfRounds;

        System.out.println("RTP: " + rtp);
    }

    private static double milionRoundsRTP(double bet){
        double sum = 0;
        int count = 1000000;

        for(int i=0;i<count;i++){
            sum += game.round(bet);
        }

        return ((double)sum / (count * bet));
    }

}