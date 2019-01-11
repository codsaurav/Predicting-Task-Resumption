/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.util.Date;

/**
 *
 * @author satyam
 */
public class Places {
    
    private String name;
    private String date;
    private Date dateFormat;
    private String location;
    
    public Places (String n, String d, Date dd) {
        this.name = n;
        this.date = d;
        this.dateFormat = dd;
    }
    
    public Places (String n, String d, Date dd, String l) {
        this.name = n;
        this.date = d;
        this.dateFormat = dd;
        this.location = l;
    }
    
    public String getName () {
        return this.name;
    }
    
    public void setName (String n) {
        this.name = n;
    }
    
    public Date getDateFormat () {
        return this.dateFormat;
    }
    
    public void setDateFormat (Date dd) {
        this.dateFormat = dd;
    }
    
    public String getDate () {
        return this.date;
    }
    
    public void setDate (String d) {
        this.date = d;
    }
    
    
    public void setLocation (String l) {
        this.location = l;
    }
    
    public String getLocation () {
        return this.location;
    }
}
