/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author satyam
 */
public class TaskResumption {

    /**
     * @param args the command line arguments
     * 
     * 
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        SuggestionBox suggestionBox = new SuggestionBox();
        suggestionBox.go();
        
        ShutDownData shutDownData = new ShutDownData();
        shutDownData.doTask();
        
        
        Thread actionThread = new Thread (new ActionRunnable());
        actionThread.setPriority (Thread.MIN_PRIORITY);
        actionThread.start();
    }
    
}
