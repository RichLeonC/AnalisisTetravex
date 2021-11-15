/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;


import Modelo.AlgoritmoGenetico;
import Modelo.Pieza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


//Melissa Alguera Castillo
//Adrian Herrera Segura AKA Atloide
//Richard Leon Chinchilla

//Fecha inicio: 12 de Setiembre
//Fecha ultima modificacion: 13 de Noviembre


public class Main {
    
    public static boolean isPrimera(int numero,int posicion){ //Verifica si la pieza pasada era multiplo
        return (posicion-1)%numero == 0;
    }

    public static HashMap<Integer, ArrayList> llenarPuzzle(int orden, int poblacion){
        ArrayList<Pieza> piezasArmadas = new ArrayList();
        HashMap<Integer, ArrayList> dictRompecabezas = new HashMap();
        int contador = 0;
        Random aleatorio = new Random();
        int numero;
        Pieza piezaActual;
        
        while(contador != poblacion){
            for(int i = 0;i<orden;i++){ //Recorre la cantidad total de las piezas
                piezaActual = new Pieza();
                piezaActual.setNumPieza(i);
                    for(int j=0;j<4;j++){ //Recorre cada posicion de una pieza
                        numero = aleatorio.nextInt(9+1); 
                        piezaActual.getPosiciones()[j] = numero;                         
                    }     
                piezasArmadas.add(piezaActual);
           
            }
            dictRompecabezas.put(contador, piezasArmadas);
            piezasArmadas = new ArrayList();
            contador++;
        }
            
        return dictRompecabezas;
    }
    
    public static void geneticoCruceAnd(HashMap<Integer, ArrayList> poblacion,AlgoritmoGenetico genetico){
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Cruce por operador logico AND");
         System.out.println("3X3");
         HashMap<Integer, ArrayList> generacionesAnd3x3;
         generacionesAnd3x3 = genetico.cruceAnd(genetico.funcionFitness(poblacion, 3),poblacion, 50);   
         System.out.println("5X5");
         poblacion = llenarPuzzle(25,60);
         HashMap<Integer, ArrayList> generacionesAnd5x5;
         generacionesAnd5x5 = genetico.cruceAnd(genetico.funcionFitness(poblacion, 5),poblacion, 60);  
        
        System.out.println("7X7");
        poblacion = llenarPuzzle(49,90);
        HashMap<Integer, ArrayList> generacionesAnd7x7;
        generacionesAnd7x7 = genetico.cruceAnd(genetico.funcionFitness(poblacion, 7),poblacion, 70);  
        // System.out.println("Inicial: "+genetico.funcionFitness(poblacion, 3));
       // System.out.println("Generaciones: "+genetico.funcionFitness(generacionesAnd, 3));
    }

    //Funcion principal en donde se hacen la llamada de los algoritmos
    public static void principal(){

      AlgoritmoGenetico genetico = new AlgoritmoGenetico();
       HashMap<Integer, ArrayList> poblacion = llenarPuzzle(9,30);
         
        //HashMap<Integer,Integer> matches = genetico.funcionFitness(poblacion, 3); ;
       // System.out.println(poblacion);
        //System.out.println("-----------------------------------------------");
        //System.out.println(matches);
        geneticoCruceAnd(poblacion, genetico);
   

    }

    public static void main(String[] args) {
        
        AlgoritmoGenetico genetico = new AlgoritmoGenetico();
        HashMap<Integer, ArrayList> poblacion = llenarPuzzle(9,30);
        HashMap<Integer, ArrayList> crucesExitosos = genetico.ShuffleCrossover(poblacion, 30, 50);
        
       
        principal();


    }

    
}
