package ui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class View extends JTextPane {

    public View(StyledDocument doc){
        setStyledDocument(doc);
        setForeground(Color.BLACK);
        setBackground(Color.BLACK);
        setFocusable(false);
        setEditable(true);
        setVisible(true);
        setFont(Font.getFont(Font.MONOSPACED));

    }
    public View(){
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setFocusable(false);
        setEditable(true);
        setVisible(true);
        setFont(Font.getFont(Font.MONOSPACED));
    }

    public void setText(String t) {
        try {
            Document doc = getDocument();
            doc.remove(0, doc.getLength());
            if (t == null || t.isEmpty()) {
                return;
            }
            Reader r = new StringReader(t);
            EditorKit kit = getEditorKit();
            kit.read(r, getDocument(), 0);
        } catch (IOException ioe) {

        } catch (BadLocationException ble) {

        }
    }

    public void clear(){
        setText("");
    }
}
