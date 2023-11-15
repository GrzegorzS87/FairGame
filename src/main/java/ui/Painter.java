package ui;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class Painter {

    private static Color black = new Color(0,0,0);
    private static Color override = new Color(142,255,142);
    private static Color created = new Color(102,255,102);
    private static Color step1 = new Color(0,204,0);
    private static Color step2 = new Color(0,153,0);
    private static Color step3 = new Color(0,102,0);
    private static Color step4 = new Color(0,51,0);

    public static SimpleAttributeSet color(int brightness){
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, getColor( brightness ));
        return set;
    }

    private static Color getColor(int brightness){
        return switch (brightness) {
            case -1 -> override;
            case 0  -> black;
            case 1  -> created;
            case 2  -> step1;
            case 3  -> step2;
            case 4  -> step3;
            default -> step4;
        };
    }

    public static Color getTextColor(){
        return step1;
    }
    public static Color getUiBorder(){
        return step4;
    }
}
