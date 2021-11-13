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
//Fecha ultima modificacion: 01 de Octubre


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

    //Funcion principal en donde se hacen la llamada de los algoritmos
    public static void principal(){

      AlgoritmoGenetico genetico = new AlgoritmoGenetico();
       HashMap<Integer, ArrayList> poblacion = llenarPuzzle(9,30);
        
        //HashMap<Integer,Integer> matches = genetico.funcionFitness(poblacion, 3); ;
       // System.out.println(poblacion);
        //System.out.println("-----------------------------------------------");
        //System.out.println(matches);
        
        genetico.padreBinario(poblacion.get(0));
        ArrayList<Pieza> padre = poblacion.get(0);
        System.out.println(padre.get(0).getBin());
     

    }

    public static void main(String[] args) {
        principal();


    }

    
}
