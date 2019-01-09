package com.watersfall.clocturn;

import java.util.ArrayList;

public class TurnRunner
{
    private ArrayList<Turn> list;

    public TurnRunner()
    {
        list = new ArrayList<Turn>();

        list.add(new TurnEconomy(0));
    }
    
    public void run()
    {
        for(Turn turn : list)
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
        }
    }
}
