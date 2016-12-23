/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.varios;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MacArthur
 */
public class Matriz {

    private static double[][] Datos;

    public static double Norma(double[] vector) {
        double resp;

        resp = 0;
        for (int i = 0; i < vector.length; i++) {
            resp += Math.pow(vector[i], 2);
        }

        return Math.sqrt(resp);
    }

    private static double[] VectorDiferencia(double[] vi, double[] vj) {
        double[] resp;

        if (vi.length != vj.length) {
            System.out.println("Error:  Diferencia de vectores NULL, dimensiones diferentes...");
            return null;
        } else {
            resp = new double[vi.length];
            for (int i = 0; i < vi.length; i++) {
                resp[i] = vi[i] - vj[i];
            }
            return resp;
        }
    }

    public static double[][] Dist(double[][] datos) {
        // N = datos.lenght
        int N;
        double[][] dist;

        N = datos.length;
        dist = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dist[i][j] = Norma(VectorDiferencia(datos[i], datos[j]));
            }

        }
        return dist;
    }

    public static double[][] Transpuesta(double[][] datos) {
        double[][] r;
        r = new double[datos[0].length][datos.length];

        for (int x = 0; x < datos.length; x++) {
            for (int y = 0; y < datos[x].length; y++) {
                r[y][x] = datos[x][y];
            }
        }
        return r;

    }

    public static double[][] Clonar(double[][] m1) {
        double[][] r;

        r = new double[m1.length][m1[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                r[i][j] = m1[i][j];
            }
        }

        return r;
    }

    public static double[][] Potencia(double[][] m1, int exp) {
        double[][] r;

        if (m1.length == m1[0].length) {
            r = Clonar(m1);

            // for inicia desde 2, potencia a la 1 queda la matriz original
            for (int i = 2; i <= exp; i++) {
                r = Multiplicar(r, m1);
            }
            return r;
        } else {
            return null;
        }
    }

    public static double[][] Multiplicar(double[][] m1, double[][] m2) {
        double[][] r;

        if (m1[0].length == m2.length) {
            r = new double[m1.length][m2[0].length];

            for (int x = 0; x < m1.length; x++) {
                for (int y = 0; y < m2[0].length; y++) {
                    r[x][y] = 0;
                    for (int i = 0; i < m1[0].length; i++) {
                        r[x][y] += m1[x][i] * m2[i][y];
                    }
                }
            }

            return r;
        } else {
            return null;
        }
    }

    public static double[][] Unos(int x) {
        return Unos(x, x);
    }

    public static double[][] Unos(int x, int y) {
        double[][] r;

        r = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                r[i][j] = 1;
            }
        }
        return r;
    }

    public static double[][] Identidad(int x) {
        return Identidad(x, x);
    }

    public static double[][] Identidad(int x, int y) {
        double[][] r;

        r = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                r[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        return r;
    }

    public static double[][] Resta(double[][] m1, double[][] m2) {
        double[][] resp;

        resp = new double[m1.length][m1[0].length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                resp[i][j] = m1[i][j] - m2[i][j];
            }

        }
        return resp;
    }

    public static double[][] ProductoEscalar(double[][] datos, double escalar) {
        double[][] resp;

        resp = new double[datos.length][datos[0].length];

        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[0].length; j++) {
                resp[i][j] = datos[i][j] * escalar;
            }
        }
        return resp;

    }

    private static double redondearDecimales(double valor, int numeroDecimales) {
        double resultado;

        resultado = valor * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = resultado / Math.pow(10, numeroDecimales);
        return resultado;
    }

    /**
     * LeerCsv permite leer un archivo .csv y cargar los datos en un arreglo
     * bidimencional tipo double
     *
     * @param nombreArchivo Nombre del archivo a leer, ingresar la ruta
     * completa. Ejemplo: "D:/MatrizDatos.csv"
     * @param fil Ingresar el número de filas a leer en el archivo .csv
     * @param col Ingresar el número de columnas a leer en el archivo .csv
     * @param cabecera true si existe cabezera o false sino existe cabezera de
     * los datos en el archivo .csv
     * @return Matriz double con los datos leidos desde el archivo .csv
     */
    public static double[][] LeerCsv(String nombreArchivo, int fil, int col, boolean cabecera) {
        double[][] datos;

        datos = new double[fil][col];
        try {
            int cont = 0;
            CsvReader matrizImport = new CsvReader(nombreArchivo);

            //Si existe cabeceras usar readHeaders()
            if (cabecera) {
                matrizImport.readHeaders();
            }

            while (matrizImport.readRecord()) {
                for (int i = 0; i < col; i++) {
                    datos[cont][i] = Double.parseDouble(matrizImport.get(i));
                }
                cont++;
            }

            matrizImport.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    /**
     * LeerCsv permite leer un archivo .csv y cargar los datos en un arreglo
     * bidimencional tipo double
     *
     * @param nombreArchivo Nombre del archivo a leer, ingresar la ruta
     * completa. Ejemplo: "D:/MatrizDatos.csv"
     * @param fil Ingresar el número de filas a leer en el archivo .csv
     * @param col Ingresar el número de columnas a leer en el archivo .csv
     * @param cabecera "true" si existe cabezera o false sino existe cabezera de
     * los datos en el archivo .csv
     * @param etiqueta "true" si existe etiquetas para cada una de las filas, en
     * la última columna del archivo .csv
     * @return Matriz double con los datos leidos desde el archivo .csv
     */
    public static Object[][] LeerCsvObject(String nombreArchivo, int fil, int col, boolean cabecera, boolean etiqueta) {
        Object[][] datos;

        if (etiqueta) {
            datos = new Object[fil][col + 1];
        } else {
            datos = new Object[fil][col];
        }
        try {
            int cont = 0;
            int i;
            CsvReader matrizImport = new CsvReader(nombreArchivo);

            //Si existe cabeceras usar readHeaders()
            if (cabecera) {
                matrizImport.readHeaders();
            }

            while (matrizImport.readRecord()) {
                for (i = 0; i < col; i++) {
                    datos[cont][i] = Double.parseDouble(matrizImport.get(i));
                }
                if (etiqueta) {
                    datos[cont][i] = matrizImport.get(i);
                }
                cont++;
            }

            matrizImport.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public static boolean EscribirCsv(String archivoSalida, double[][] datos, int numeroDecimales) throws Exception {
        return EscribirCsv(archivoSalida, datos, numeroDecimales, null);
    }

    public static boolean EscribirCsv(String archivoSalida, double[][] datos) throws Exception {
        return EscribirCsv(archivoSalida, datos, -1, null);
    }

    public static boolean EscribirCsv(String archivoSalida, double[][] datos, String[] cabeceras) throws Exception {
        return EscribirCsv(archivoSalida, datos, -1, cabeceras);
    }

    /**
     * EscribirCsv permite almacenar un arreglo bidimencional tipo double en un
     * archivo .csv
     *
     * @param archivoSalida Nombre del archivo en que se va a escribir el
     * arreglo, ingresar la ruta completa. Ejemplo: "D:/MatrizDatos.csv"
     * @param datos Arreglo bidimensional tipo double con los datos a escribir.
     * @param numeroDecimales Redondear a numeroDecimales los valores double.
     * Poner -1 para no redondeo.
     * @param cabeceras Arreglo de cabeceras de ser necesario escribir un
     * archivo .csv con cabeceras. Si no se desea cabeceras, especificar null.
     * @return true si la escritura del archivo tuvo éxito. SI fallo retorna
     * false.
     */
    public static boolean EscribirCsv(String archivoSalida, double[][] datos, int numeroDecimales, String[] cabeceras) throws Exception {
        boolean alreadyExists = new File(archivoSalida).exists();

        if ((cabeceras != null) && (datos[0].length != cabeceras.length)) {
            throw new Exception("Error : Número de cabeceras incompatible con número de columnas...");
        }
        if (alreadyExists) {
            File ArchivoEmpleados = new File(archivoSalida);
            ArchivoEmpleados.delete();
        }

        try {

            CsvWriter csvOutput = new CsvWriter(new FileWriter(archivoSalida, true), ',');

            if (cabeceras != null) {
                for (String cabecera : cabeceras) {
                    csvOutput.write(cabecera);
                }
                csvOutput.endRecord();
            }

            for (double[] dato : datos) {

                for (double d : dato) {
                    if (numeroDecimales >= 0) {
                        csvOutput.write(String.valueOf(redondearDecimales(d, numeroDecimales)));
                    } else {
                        csvOutput.write(String.valueOf(d));
                    }

                }
                csvOutput.endRecord();
            }
            csvOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * *
     * Imprimir un AbstractTableModel en consola.
     *
     * @param data AbstractTableModel a imprimir
     * @param tamaño Espacio reservado para imprimir cada dato. Formateo de
     * impresión
     * @param decimales Número de decimales de los datos que se imprimen
     */
    public static void Imprimir(AbstractTableModel data, int tamaño, int decimales) {
        int y;
        Object dato;

        for (int x = 0; x < data.getRowCount(); x++) {
            for (y = 0; y < data.getColumnCount(); y++) {
                dato = data.getValueAt(x, y);
                if (dato instanceof Double) {
                    System.out.printf("%" + tamaño + "." + decimales + "f", data.getValueAt(x, y));
                }
                if (dato instanceof String) {
                    System.out.print("  " + data.getValueAt(x, y));
                }
            }
            System.out.println("");
        }
    }

    /**
     * *
     * Imprimir un AbstractTableModel en consola.
     *
     * @param data AbstractTableModel a imprimir
     */
    public static void Imprimir(AbstractTableModel data) {
        Imprimir(data, 10, 4);
    }

    /**
     * *
     * Imprimir un arreglo tipo double[][] en consola.
     *
     * @param datos double[][] a imprimir
     * @param tamaño Espacio reservado para imprimir cada dato. Formateo de
     * impresión
     * @param decimales Número de decimales de los datos que se imprimen
     */
    public static void Imprimir(double[][] datos, int tamaño, int decimales) {
        for (int x = 0; x < datos.length; x++) {
            for (int y = 0; y < datos[x].length; y++) {
                System.out.printf("%" + tamaño + "." + decimales + "f", datos[x][y]);
            }
            System.out.println("");
        }
    }

    /**
     *
     * Imprimir un arreglo tipo double[][] en consola.
     *
     * @param datos double[][] a imprimir
     */
    public static void Imprimir(double[][] datos) {
        Imprimir(datos, 10, 4);
    }

}
