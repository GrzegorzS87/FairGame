package gs;

public class GameServer {
    private Engine engineA = new EngineA();
    private Engine engineB = new EngineB();
    private Engine engine = engineA;
    private String currentEngine = "A";

    public double round(int bet){
        return engine.round(bet);
    }

    public String getCurrentEngine() {
        return currentEngine;
    }

    public void swapEngine(){
        if(engine == engineA){
            engine = engineB;
            currentEngine = "B";
        }else{
            engine = engineA;
            currentEngine = "A";
        }
    }
}
