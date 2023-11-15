package ui;

import util.CONST;

public class Animation {

    private int cornerRow;      // placement offset
    private final int cornerColumn;   // placement offset
    private final int height;
    private final int width;
    private int duration;

    private final int[][] animationData;


    public Animation(){ // used to create YGG logo only
        animationData = AnimationBinary.YGG_LOGO.getData();
        height = AnimationBinary.YGG_LOGO.getHeight();
        width = AnimationBinary.YGG_LOGO.getWidth();
        duration = AnimationBinary.YGG_LOGO.getDuration();
        cornerRow = 1;
        cornerColumn = 18;
    }

    public Animation(int occurance){ // used for symbol combinations
        animationData = treeRollsBinary(occurance);
        height = CONST.DATA_HEIGHT;
        width = CONST.DATA_WIDTH;
        duration = CONST.SYMBOL_DURATION;
        cornerRow = 8;
        cornerColumn =5;
    }

    private static int[][] treeRollsBinary(int occurance){
        //I do not care for creating some reasonable payout system correlated with symbol values
        //For now its enought if win gives some symbol combination and loss gives 3 different

        int[][] newBinary = new int[10][54];
        int[][] symbol1;
        int[][] symbol2;
        int[][] symbol3;

        if(occurance == 3){
            symbol1 = AnimationBinary.random().getData();
            symbol2 = symbol1;
            symbol3 = symbol1;
        } else if (occurance == 2) {
            AnimationBinary mainSymbol = AnimationBinary.random();
            symbol1 = mainSymbol.getData();
            symbol2 = symbol1;
            symbol3 = AnimationBinary.randomWithout(mainSymbol).getData();
        } else {
            AnimationBinary mainSymbol = AnimationBinary.random();
            AnimationBinary secondarySymbol = AnimationBinary.randomWithout(mainSymbol);
            symbol1 = mainSymbol.getData();
            symbol2 = secondarySymbol.getData();
            symbol3 = AnimationBinary.randomWithout(mainSymbol, secondarySymbol).getData();
        }

        for(int row=0;row<10;row++) {
            for (int column = 0; column < 16; column++) {
                newBinary[row][column] = symbol1[row][column];
            }
            for (int column = 16; column < 19; column++) {
                newBinary[row][column] = 0;
            }
            for(int column=19;column<35;column++){
                newBinary[row][column] = symbol2[row][column-19];
            }
            for(int column=35;column<38;column++){
                newBinary[row][column] = 0;
            }
            for(int column=38;column<54;column++){
                newBinary[row][column] = symbol3[row][column-38];
            }
        }

        return newBinary;
    }

    public boolean isUsedByAnimation(int row, int column){
        if( row >= cornerRow && column >= cornerColumn ){
            int logoRow = row - cornerRow;
            int logoColumn = column - cornerColumn;
            if(logoRow < height && logoColumn < width)
                return animationData[logoRow][logoColumn] == 1;
        }

        return false;
    }

    public int getWidth(){
        return width;
    }

    public int getCornerColumn(){
        return cornerColumn;
    }

    public int getDuration() {
        return duration;
    }
}
