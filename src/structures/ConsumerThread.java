/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafxapplication4.CarsDashboard;

/**
 *
 * @author user
 */
public class ConsumerThread extends Thread {

    @Override
    public void run() 
    {
        try 
            {CarsDashboard.runConsumer();}
        catch (InterruptedException ex) 
            {System.err.println("ConsumerThread: can't run consumer in Cars Dashboard, error is "+ex);}
    }
    
    


}
