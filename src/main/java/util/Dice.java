package util;

import java.util.Random;

public class Dice {
    private static final Random random = new Random();
    private static final String characters = "アァカサタナハマヤャラワガザダバパイィキシチニヒミリヰギジヂビピウゥクスツヌフムユュルグズブヅプエェケセテネヘメレヱゲゼデベペオォコソトノホモヨョロヲゴゾドボポヴッン"; //
    private static final int lenght = characters.length();

    public static int nextInt(int min, int max){
        return random.nextInt( min, max);
    }

    public static int nextInt( int bound ){
        return random.nextInt( bound );
    }

    public static double nextDouble(){return random.nextDouble();};

    public static int chance() {
        return random.nextInt(0,100);
    }

    public static char randomChar(){
        return characters.charAt( Dice.nextInt(lenght) );
    }
}
