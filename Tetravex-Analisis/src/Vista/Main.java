/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;


import Modelo.Pieza;
import java.util.ArrayList;
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

    public static void llenarPuzzle(int limite, ArrayList<Pieza> piezasArmadas, int orden){
        Random aleatorio = new Random();
        int numero;
        Pieza piezaActual;
        for(int i = 0;i<orden;i++){ //Recorre la cantidad total de las piezas
           piezaActual = new Pieza();
           piezaActual.setNumPieza(i);
            for(int j=0;j<4;j++){ //Recorre cada posicion de una pieza
                  numero = aleatorio.nextInt(limite+1); 
                  piezaActual.getPosiciones()[j] = numero;          
               
            }     
            piezasArmadas.add(piezaActual);
           
        }
    
    }

    //Funcion principal en donde se hacen la llamada de los algoritmos
    public static void principal(){
      
        
     

    }

    public static void main(String[] args) {
       
       
       
       
    }
    
}
