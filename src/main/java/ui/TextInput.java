package ui;

import javax.swing.*;
import java.awt.*;

public class TextInput extends JTextField {

//    JTextField function = new JTextField(8);

    public TextInput(){
        super(8);
        setForeground(Color.BLACK);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder( Painter.getUiBorder() ));
        setCaretColor(Color.black);

    }

}
