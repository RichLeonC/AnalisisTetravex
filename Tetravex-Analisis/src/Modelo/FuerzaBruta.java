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

    
    public static void comparar(ArrayList<Pieza> piezasDesordenadas, ArrayList<Pieza> piezasOrdenadas, int orden){
        ArrayList<Pieza> piezasFuerzaBruta = new ArrayList();
        ArrayList<Pieza> clonDesordenadas = (ArrayList<Pieza>) piezasDesordenadas.clone();
        int vueltas = 0;
        int posicion = 0;
        
        while(posicion != piezasDesordenadas.size()){
            int aleatorio = (int)(Math.random()*clonDesordenadas.size());
            if(clonDesordenadas.get(aleatorio) == piezasOrdenadas.get(posicion)){
                piezasFuerzaBruta.add(clonDesordenadas.get(aleatorio));
                clonDesordenadas.remove(aleatorio);
                posicion++;
            }
            vueltas++;
        }
        System.out.println("---------------Método de Fuerza Bruta---------------");
        System.out.println("Rompecabezas Armado: \n" +  piezasFuerzaBruta);
        System.out.println("Número de ciclos realizados: " + vueltas);
        
        
        
        
        /*while(piezasFuerzaBruta.size() < piezasDesordenadas.size()){
            for (int i = 0; i < clonDesordenadas.size(); i++) {
                if (piezasFuerzaBruta.get(piezasColocadas).getEste() == clonDesordenadas.get(i).getOeste()){
                    piezasFuerzaBruta.add(clonDesordenadas.get(i));
                    clonDesordenadas.remove(i);
                    piezasColocadas++;
                    System.out.println(piezasFuerzaBruta);
                }
                
            }
        }
        System.out.println(piezasFuerzaBruta);
        */
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
    
    /*public static int[] generarSecuencia(int orden){
    
        int index = 0; //Posición del arreglo
        int [] aleatorios = new int [orden]; //Arreglo de numeros aleatorios
    
        while(index < orden) {
            int propuesto = (int)(Math.random()*orden); //Se genera un numero aleatorio
            boolean repetido = false;
            while(!repetido) { //Bucle para verificar si el numero está repetido en el arreglo
                for(int i=0; i<index; i++) {
                    if(propuesto == aleatorios[i]) {
                        repetido = true;
                        break;
                    }
                }
                if(!repetido) {
                    aleatorios[index] = propuesto;
                    index++;
                }
            }
        }

        return aleatorios; //Retorna el arreglo de numeros aleatorios sin repetición
    }
    
    public static void comparar(ArrayList<Pieza> piezasArmadas, ArrayList<Pieza> piezasDesordenadas, int orden){
    ArrayList<Pieza> piezasFuerzaBruta = new ArrayList();
    ArrayList<int[]> aleatoriosRepetidos = new ArrayList();
    int[] aleatorios;
    int indice;
    System.out.println("Hola -> " + piezasArmadas);
    while (!piezasFuerzaBruta.equals(piezasArmadas)){
    piezasFuerzaBruta.clear();
    aleatorios = generarSecuencia(orden, aleatoriosRepetidos);
    aleatoriosRepetidos.add(aleatorios);
    indice = 0;
    while(indice != orden){
    piezasFuerzaBruta.add(piezasDesordenadas.get(aleatorios[indice]));
    indice ++;
    }
    System.out.println(piezasFuerzaBruta);
    }
    //System.out.println(piezasFuerzaBruta);
    }*/
    
    public FuerzaBruta() {
        
    }
    
    
    
    
}
