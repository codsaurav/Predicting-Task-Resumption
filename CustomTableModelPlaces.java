/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author satyam
 */
public class CustomTableModelPlaces extends AbstractTableModel {

   private String[] columns = {"Places","Time"};
    private ArrayList<Places> data;
    
    public CustomTableModelPlaces (ArrayList<Places> a) {
        super();
        this.data = a;
    }
    
    

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return  data.get(rowIndex).getName();
            case 1:
                return data.get(rowIndex).getDate();
        }
        return null;
    }
    
    public String getColumnName (int col) {
        return columns[col];
    }
    
    public Class<?> getColumnClass (int col) {
        return getValueAt(0,col).getClass();
    }
    
    
    
    public boolean isCellEditable (int row, int col) {
        return false;
    }
    
}
