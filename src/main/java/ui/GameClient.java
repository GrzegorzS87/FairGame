package ui;
import util.CONST;
import gs.GameServer;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameClient {

    private static final StyledDocument doc = new DefaultStyledDocument();
    private static final View view = new View(doc);
    private static final View console = new View();
    private static final View generalInfoBox = new View();
    private final MyFrame frame = new MyFrame();
    private Animation animation;
    private boolean animationIsSet = false;
    private int animationStep = 0;
    private int animationDuration = 0;
    private STATE state = STATE.IDLE;
    private final GameServer server;

    // player should be in separate class, I know
    private int balance = 90000;

    public static final TextInput input = new TextInput();
    public static final int WINDOW_WIDTH = 790;
    public static final int WINDOW_HEIGHT = 630;

    public GameClient(GameServer server){

        this.server = server;

        JFrame window = new JFrame("Code Rain  (by Grzegorz Soból)");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        window.setLayout( new BorderLayout() );
        window.getContentPane().getInsets().set( 10, 10, 10, 10 );

        view.setPreferredSize(new Dimension(WINDOW_WIDTH, 485));
        view.setBorder(BorderFactory.createLineBorder(Painter.getUiBorder()));

        console.setPreferredSize( new Dimension(WINDOW_WIDTH, 60) );
        console.setBorder(BorderFactory.createLineBorder(Painter.getUiBorder()));
        console.setForeground(Painter.getTextColor());
        console.setFont(MyFonts.courier(60));

        generalInfoBox.setPreferredSize(  new Dimension(WINDOW_WIDTH, 20) );
        generalInfoBox.setBorder(BorderFactory.createLineBorder(Painter.getUiBorder()));
        generalInfoBox.setFont(MyFonts.courier(18));
        generalInfoBox.setForeground(Painter.getTextColor());

        input.setPreferredSize( new Dimension(WINDOW_WIDTH, 5) );
        input.setForeground(Painter.getTextColor());
        input.addKeyListener( new KeyAdapter() {  //its console, it has to be ugly somewhere, and it is late
            @Override
            public void keyPressed( KeyEvent e ) {

                if( state == STATE.IDLE ){  //STATE.IDLE waiting for user input

                    if( e.getKeyCode() == KeyEvent.VK_ENTER ){  // spin
                        flushAnimation();
                        playRound();
                        printGeneralInfo();

                    } else if (e.getKeyCode()==KeyEvent.VK_SHIFT){   // switch engine
                        server.swapEngine();
                        console.setText("running engine " + server.getCurrentEngine());
                        printGeneralInfo();

                    } else if (e.getKeyCode()==KeyEvent.VK_R){
                        changeRainToGame();
                    }
                }
            }});

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add( generalInfoBox, BorderLayout.CENTER);
        bottom.add( input,BorderLayout.PAGE_END );

        window.add( view, BorderLayout.PAGE_START );
        window.add( console,BorderLayout.CENTER );
        window.add(bottom,BorderLayout.PAGE_END );
        window.setLocationRelativeTo( null );
        window.setVisible( true );

        view.setText(frame.getText());
    }

    private void playRound() {
        double win = server.round(CONST.BET);   //bet should be 1, some rounding up issues yet to be solved
        balance-= CONST.BET;                     //don't care if player has funds
        balance+=win;
        console.setText(" you win: " + win);    // for win to be visible after symbols appear
                                                // I would have to stick it in some wierd place, lerytbe
        if(win == 0 ) {
            setAnimation(new Animation(1));
        }else if (win<1000) {
            setAnimation(new Animation(2));
        }else{
            setAnimation(new Animation(3));
        }
    }

    private void changeRainToGame() {
        if (animationIsSet){
            flushAnimation();
            print();
            console.setText("yup");
            setGeneralInfoBoxRainOnly();
        }else{
            setAnimation(new Animation(1));
            print();
            printGeneralInfo();
        }
    }

    private void printGeneralInfo() {
        generalInfoBox.setText("BALANCE: " + balance + "  | BET: " + CONST.BET + " | ENTER to spin | mode "
                + server.getCurrentEngine() + ", SHIFT to switch");
    }

    public void setAnimation(Animation animation){
        state = STATE.ANIMATION_ONGOING;
        this.animation = animation;
        this.animationDuration = animation.getDuration();
        this.animationStep = 0;
        this.animationIsSet = true;
        for(int column = 0; column < this.animation.getWidth(); column++){
            frame.setSymbolBrightness(0,this.animation.getCornerColumn() + column, 1);
        }
    }

    public void print() {
        updateAnimationStep();
        frameDelay();
        frame.step();
        updateViewsStyleDocument();
    }

    public void print(int steps) {
        for (int i = 0; i < steps; i++){
            updateAnimationStep();
            frameDelay();
            frame.step();
            updateViewsStyleDocument();
        }
    }

    private void frameDelay() {
        try {
            Thread.sleep(CONST.FRAME_DELAY);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateAnimationStep() {
        if (!animationIsSet) return;

        if (animationStep < animationDuration) { // while animation is ongoing I don't want user input to interrupt
            animationStep++;
        } else {
            state = STATE.IDLE;
        }
    }

    private void updateViewsStyleDocument() {
        StyledDocument newDoc = view.getStyledDocument();
        for (int row = 0; row < CONST.ROWS; row++) {
            for (int column = 0; column < CONST.COLUMNS; column++) {
                updateDocumentPoint(row,column,newDoc);
            }
        }
    }

    private void updateDocumentPoint(int row, int column, StyledDocument newDoc) {
        int charCount = ((row * CONST.COLUMNS) + column + row);
        if (animationIsSet && (animationStep >= row)) { //&& (animationStep >= row) makes image appear after first rain drop pass

            if (animation.isUsedByAnimation(row, column)) {
                newDoc.setCharacterAttributes(charCount, 1, frame.getBrightness(-1), true);
            } else {
                newDoc.setCharacterAttributes(charCount, 1, frame.getBrightness(row, column), true);
            }
        } else
            newDoc.setCharacterAttributes(charCount, 1, frame.getBrightness(row, column), true);
    }

    private void flushAnimation() {
        for (int row = 0; row < CONST.ROWS; row++) {
            for (int column = 0; column < CONST.COLUMNS; column++) {
                if (animation.isUsedByAnimation(row, column)) {
                    frame.setSymbolBrightness(row, column, 1);
                }
            }
        }
        animationIsSet = false;
        print();
    }

    private void playIntro() {
        state = STATE.INTRO;
        console.setFont(MyFonts.courier(16));
        console.setText("\n code rain, much hype");

        print(70); // delay

        setAnimation(new Animation()); //ygg logo
        print(50);
        flushAnimation();

        console.setText("\n will print 2d binary array, each alien is in different array");
        print(10); // delay

        setAnimation(new Animation(1)); // aliens
        print(70);
        flushAnimation();

        //only rain now
        setGeneralInfoBoxRainOnly();
        console.setText("");
        console.setFont(MyFonts.courier(60));

        state = STATE.IDLE;
    }

    private void setGeneralInfoBoxRainOnly(){
        generalInfoBox.setText("R to switch to game");
    }

    public void run() {
        playIntro();
        while(true) print();
    }

}


