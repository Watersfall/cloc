package com.watersfall.clocturn;

import java.util.ArrayList;

public class TurnRunner
{
    private ArrayList<Turn> list;

    public TurnRunner()
    {
        list = new ArrayList<>();

        list.add(new TurnEconomy(0));
    }
    
    public void run()
    {
        list.forEach((turn) ->
        {
            new Thread(() ->
            {
                try 
                {
                    Thread.sleep(turn.offset * 1000);
                    turn.doTurn();
                }
                catch (InterruptedException ex) 
                {
                    ex.printStackTrace();
                }
            }).start();
        });
    }
}
