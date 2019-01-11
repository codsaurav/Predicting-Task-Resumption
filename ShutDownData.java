/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

/**
 *
 * @author satyam
 */
public class ShutDownData {
    
    public SQLDatabaseHelper sqlDatabaseHelper;
    File file;
    
    public void doTask () {
        try {
            file = new File ("output.data");
            
            if (!file.exists())
                file.createNewFile();
            
            sqlDatabaseHelper = SQLDatabaseHelper.getInstance();
            sqlDatabaseHelper.initialize();
            ObjectInputStream ois = new ObjectInputStream (new FileInputStream (file));
            KeyValueClass keyValueClass = (KeyValueClass) ois.readObject();
            for (Action a : keyValueClass.arrayList) {
                sqlDatabaseHelper.putData(a.getProgram(),a.getTitle(),a.getStartDate(),keyValueClass.newDate, a.getFile(), a.getExecutable(), true);
            }
            sqlDatabaseHelper.closeConnection();
            ois.close();
            new PrintWriter(file).close();
        } catch (EOFException ex) {
            //Do Nothing. Chill out...
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (ClassNotFoundException ex) {
            //Nothing to do here. Chill out.
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
