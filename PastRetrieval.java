/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

/**
 *
 * @author satyam
 */
public class PastRetrieval {
    private String program;
    private String title;
    private String start;
    private String end;
    private String executable;
    private String file;
    
    public PastRetrieval (String p, String t, String s, String e, String ex, String f) {
        this.program = p;
        this.title = t;
        this.start = s;
        this.end = e;
        this.executable = ex;
        this.file = f;
        
    }
    
    public String getProgram () {
        return this.program;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public String getStart () {
        return this.start;
    }
    
    public String getEnd () {
        return this.end;
    }
    
    public String getFile() {
        return this.file;
    }
    
    public String getExecutable () {
        return this.executable;
    }
}
