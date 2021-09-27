/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

public class FuerzaBruta {
    
    ArrayList<Pieza> piezasArmadas;
    ArrayList<Pieza> piezasDesordenadas;
 
    public FuerzaBruta(ArrayList<Pieza> piezasArmadas, ArrayList<Pieza> piezasDesordenadas) {
        this.piezasArmadas = piezasArmadas;
        this.piezasDesordenadas = piezasDesordenadas;
    }

    
    public static void comparar(ArrayList<Pieza> piezasDesordenadas, ArrayList<Pieza> piezasOrdenadas){
        ArrayList<Pieza> piezasFuerzaBruta = new ArrayList(); //Arreglo donde se resolverá el rompecabezas
        ArrayList<Pieza> clonDesordenadas = (ArrayList<Pieza>) piezasDesordenadas.clone(); //copia de las piezas desordenadas
        int posicion = 0; //Posición en el arreglo de piezasFuerzaBruta
        int asignaciones = 0, comparaciones = 0; //Contador de asignaciones y comparaciones.
        asignaciones += 3;
        
        while(posicion != piezasDesordenadas.size()){ //Mientras no estén todas las piezas en el arreglo, no se sale del bucle
            comparaciones ++;
            int aleatorio = (int)(Math.random()*clonDesordenadas.size()); //Se elige una pieza de manera aleatoria
            asignaciones ++;
            if(clonDesordenadas.get(aleatorio) == piezasOrdenadas.get(posicion)){ 
                comparaciones ++;
                piezasFuerzaBruta.add(clonDesordenadas.get(aleatorio)); //Si la pieza elegida es la correcta en el arreglo de piezas ordenadas, se añade al arreglo solución.
                clonDesordenadas.remove(aleatorio); //Se elimina la pieza elegida para que no se elija nuevamente.
                posicion++; //Se aumenta la posición del arreglo.
            }
            
        }
        System.out.println("---------------Método de Fuerza Bruta---------------");
        System.out.println("Rompecabezas Armado: \n" + piezasFuerzaBruta);
        System.out.println("Asignaciones: " + asignaciones);
        System.out.println("Comparaciones: " + comparaciones);
    }
        
    public FuerzaBruta() {
        
    }
    
}
