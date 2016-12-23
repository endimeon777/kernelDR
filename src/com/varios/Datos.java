/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.varios;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MacArthur
 */
public class Datos extends AbstractTableModel {

    Object[][] data;

    public Datos(Object[][] data) {
        if (data == null) {
            throw new UnsupportedOperationException("No existen datos......");
        }
        this.data = data;
    }

    public Datos(double[][] data) {
        if (data == null) {
            throw new UnsupportedOperationException("No existen datos......");
        }
        this.data = new Object[data.length][data[0].length];

    }

    @Override
    public int getRowCount() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return data.length;
    }

    @Override
    public int getColumnCount() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.      
        return data[rowIndex][columnIndex];
    }
}
