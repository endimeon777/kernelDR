/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.varios;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MacArthur
 */
public class KMDS {

    private AbstractTableModel dataIn;
    private AbstractTableModel dataOut;
    private double[][] K;

    public KMDS(AbstractTableModel dataIn) throws Exception {
        this.dataIn = dataIn;
        KernelMDS();
    }

    private boolean Etiqueta() {
        if (dataIn.getValueAt(0, dataIn.getColumnCount() - 1) instanceof String) {
            return true;
        } else {
            return false;
        }
    }

    private double[][] LeerDataIn() {
        double[][] data;

        if (Etiqueta()) {
            data = new double[dataIn.getRowCount()][dataIn.getColumnCount() - 1];
        } else {
            data = new double[dataIn.getRowCount()][dataIn.getColumnCount()];
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (double) dataIn.getValueAt(i, j);
            }

        }
        return data;
    }

    private String[] LeerEtiquetas() {
        String[] etiquetas;

        if (Etiqueta()) {
            etiquetas = new String[dataIn.getRowCount()];
            for (int i = 0; i < etiquetas.length; i++) {
                etiquetas[i] = (String) dataIn.getValueAt(i, dataIn.getColumnCount() - 1);
            }
            return etiquetas;
        } else {
            return null;
        }
    }

    public void KernelMDS() throws Exception {
        int N;
        double[][] X;
        double[][] D;
        double[][] N1;
        double[][] I;
        double[][] Itmp;
        String[] etiquetas;

        X = LeerDataIn();

        N = X.length;

        D = Matriz.Potencia(Matriz.Dist(X), 2);

        N1 = Matriz.ProductoEscalar(Matriz.Unos(N, 1), Math.pow(N, -0.5));

        I = Matriz.Identidad(N);

        //Itmp = (I-N1*N1t);
        Itmp = Matriz.Resta(I, Matriz.Multiplicar(N1, Matriz.Transpuesta(N1)));

        K = Matriz.Multiplicar(Matriz.Multiplicar(Matriz.ProductoEscalar(Itmp, -0.5), D), Itmp);

        etiquetas = LeerEtiquetas();

        setDataOut(K, etiquetas);
    }

    public double[][] EigValores() {

        //Matrix Libreria JAMA
        Matrix A = Matrix.constructWithCopy(K);

        // compute the spectral decomposition
        EigenvalueDecomposition e = A.eig();

        Matrix D = e.getD();
        return D.getArray();
    }

    public double[][] EigVectores() {
        //Matrix Libreria JAMA
        Matrix A = Matrix.constructWithCopy(K);

        // compute the spectral decomposition
        EigenvalueDecomposition e = A.eig();

        Matrix V = e.getV();

        return V.getArray();
    }

    private void setDataOut(double[][] data, String[] etiquetas) {

        Object[][] datos;

        if (etiquetas != null) {
            if (data.length != etiquetas.length) {
                throw new UnsupportedOperationException("Número de filas es diferente a números de etiquetas......");
            }
            datos = new Object[data.length][data[0].length + 1]; // Crear Objecto con una columna mas, para almacenar las etiquetas.
            // Asignos las etiquetas en la ultima columna del objeto Object[][]
            for (int i = 0; i < etiquetas.length; i++) {
                datos[i][datos[i].length - 1] = etiquetas[i];
            }
        } else {
            datos = new Object[data.length][data[0].length];
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                datos[i][j] = data[i][j];
            }
        }

        dataOut = new Datos(datos);
    }

    public AbstractTableModel getKernelMDS() {
        //Este es el kernel
        return dataOut;
    }

    public void KernelMDSOld() throws Exception {
        int N;
        //double[][] X = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //double[][] X = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}, {13, 14, 15}};
        double[][] X;
        double[][] Y = {{1}, {2}, {3}};
        double[][] Xt;
        double[][] D;
        double[][] N1;
        double[][] I;
        double[][] K;
        double[][] Itmp;

        long timeFin;
        long timeIni;

//        X = Matriz.LeerCsv("D:/MatrizDatos.csv", 5, 3, false);
//        X = Matriz.LeerCsv("D:/Iris.csv", 150, 4, true);
        X = LeerDataIn();

        System.out.println("Matriz Original X\n**************");
        Matriz.Imprimir(X);

        timeIni = System.currentTimeMillis();
        N = X.length;

        Xt = Matriz.Transpuesta(X);

        D = Matriz.Potencia(Matriz.Dist(X), 2);

        N1 = Matriz.ProductoEscalar(Matriz.Unos(N, 1), Math.pow(N, -0.5));

        I = Matriz.Identidad(N);

        //Itmp = (I-N1*N1t);
        Itmp = Matriz.Resta(I, Matriz.Multiplicar(N1, Matriz.Transpuesta(N1)));

        K = Matriz.Multiplicar(Matriz.Multiplicar(Matriz.ProductoEscalar(Itmp, -0.5), D), Itmp);
        timeFin = System.currentTimeMillis();
        System.out.println("Matriz Kernel\n**************");
        System.out.println("Tiempo en milisegundos: " + (timeIni) + "-" + timeFin);

        //marilin
        Matriz.Imprimir(K);
        System.out.println("");

        //Matriz.Imprimir(Jama.
        Matrix A = Matrix.constructWithCopy(K);
        //  A = A.transpose().times(A);

        // compute the spectral decomposition
        EigenvalueDecomposition e = A.eig();
        timeIni = System.currentTimeMillis();
        Matrix D1 = e.getD();
        Matrix V = e.getV();
        timeFin = System.currentTimeMillis();
        System.out.println("Tiempo en milisegundos: " + (timeIni) + "-" + timeFin);

        String[] cabeceras = {"uno", "dos", "tres", "cuatro", "cinco"};

        System.out.println("Matriz Vectores\n**************");
        Matriz.Imprimir(V.getArray());
        Matriz.EscribirCsv("D:/MatrizVectores.csv", V.getArray(), 2);
        System.out.println("");
        System.out.println("Matriz Valores\n**************");
        Matriz.Imprimir(D1.getArray());
        Matriz.EscribirCsv("D:/MatrizValores.csv", D1.getArray());
        System.out.println("");

    }

}
