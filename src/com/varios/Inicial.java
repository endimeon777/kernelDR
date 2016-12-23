/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.varios;

import Jama.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MacArthur
 */
public class Inicial {

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        // KMDS();

        Datos datos;
        Object[][] X;
        KMDS kmds;
        
        // Leo los datos desde un archivo .csv
        //X = Matriz.LeerCsvObject("D:/MatrizDatos.csv", 5, 3, false, false);
        
         X = Matriz.LeerCsvObject("D:/Iris.csv", 150, 4, true,true);

        datos = new Datos(X);
        //Matriz.Imprimir((Double[][])datos.data, 10, 3);;
        kmds = new KMDS(datos);
       
        // kmds.KernelMDS();
        System.out.println("Matriz KERNEL");
        Matriz.Imprimir(kmds.getKernelMDS());
//        System.out.println("Matriz Vectores");
//        Matriz.Imprimir(kmds.EigVectores());
//        System.out.println("Matriz Valores");
//        Matriz.Imprimir(kmds.EigValores());

    }

}
