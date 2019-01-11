/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author satyam
 */
public class KeyValueClass implements Serializable {
    
    /**
     *
     */
    
    protected ArrayList<Action> arrayList;
    protected Date newDate;
    
    public KeyValueClass (ArrayList<Action> a, Date d) {
        this.arrayList = a;
        this.newDate = d;
    }
    
    
}
