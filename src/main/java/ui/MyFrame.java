package ui;

import util.CONST;
import util.Dice;
import javax.swing.text.SimpleAttributeSet;

public class MyFrame {

    /*
    Holds symbols and brightness value for each symbol in 2d arrays. Keeps text formatted as on screen.
    I'm not doing string builder, this works fine and looks clear, but in case of any issues this might be the place to look.
     */

    private static final int ROWS = CONST.ROWS;
    private static final int COLUMNS = CONST.COLUMNS;

    private final int [][] symbolBrightness = new int[ROWS][COLUMNS];
    private final char [][] symbol = new char[ROWS][COLUMNS];

    public MyFrame(){   //initialize with brightness 0, black font on black background
        for(int row = 0; row< ROWS; row++)
            for(int column = 0; column< COLUMNS; column++){
                symbolBrightness[row][column] = 0;
                symbol[row][column] = Dice.randomChar();
            }
    }

    public void setSymbolBrightness(int row, int column, int brightness){
        symbolBrightness[row][column] = brightness;
    }

    public String getText(){
        String text = "";
        for(int row = 0; row< ROWS; row++){
            for(int column = 0; column< COLUMNS; column++)
                text += symbol[row][column];

            text += "\n";
        }
        return text;
    }

    public SimpleAttributeSet getBrightness(int row, int column){
        if(column== COLUMNS)
            return Painter.color(0);
        else
            return Painter.color(symbolBrightness[row][column]);
    }

    public SimpleAttributeSet getBrightness(int brightness){
        return Painter.color(brightness);
    }

    public void step(){
        lowerBrightness();
        markProgress();
        createDrops();
    }

    private void lowerBrightness() {
        for(int row = 0; row< ROWS; row++)
            for(int column = 0; column< COLUMNS; column++){
                if(symbolBrightness[row][column]!=0 )
                    symbolBrightness[row][column]++;
                if(symbolBrightness[row][column] > CONST.DROP_LIVE)
                    symbolBrightness[row][column] = 0;
            }
    }

    private void markProgress() {
        for(int row = 0; row< ROWS; row++)
            for(int column = 0; column< COLUMNS; column++)

                if(symbolBrightness[row][column] == 2 && row+1 < ROWS)
                    symbolBrightness[row+1][column] = 1;
    }

    private void createDrops() {
        final int row = 0;
        for (int column = 0; column< COLUMNS; column++){
            if(spawn()){
                symbolBrightness[row][column] = 1;
            }
        }
    }

    private boolean spawn() {
        int dropSpawnChance = CONST.DROP_SPAWN_CHANCE;
        return Dice.chance()< dropSpawnChance;
    }

}