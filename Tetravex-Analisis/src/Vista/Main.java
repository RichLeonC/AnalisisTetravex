

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
public static ArrayList<Pieza> llenarPuzzle(int orden){
        Random aleatorio = new Random();
        int limite = 9;
        int numero;
        Pieza piezaActual;
        ArrayList<Pieza> piezasArmadas = new ArrayList();
        for(int i = 1;i<=orden;i++){ //Recorre la cantidad total de las piezas
           piezaActual = new Pieza();
           piezaActual.setNumPieza(i);
            for(int j=0;j<4;j++){ //Recorre cada posicion de una pieza
                if(piezasArmadas.isEmpty()){ //Si no hay piezas,agrega todas sus posiciones de manera aleatoria
                   numero = aleatorio.nextInt(limite+1); 
                   piezaActual.getPosiciones()[j] = numero;
                    
                }
                else{ //Caso que ya hayan piezas agregadas
                   if(i<=Math.sqrt(orden)){  //Preguntamos si seguimos en la fila y es la primera
                     numero = aleatorio.nextInt(limite+1); 
                      piezaActual.getPosiciones()[j] = numero; //Generamos todas sus posiciones aleatorias exepto la oeste
                      if(j==3){ //Si ya estamos en la posicion oeste
                          Pieza piezaPasada = piezasArmadas.get(i-2); 
                          numero = piezaPasada.getEste(); //Obtenemos la posicion este de la pieza pasada
                          piezaActual.getPosiciones()[3] = numero; //Le metemos la posicion este en la oeste de la actual para que hagan match
                      }                  
                       
                      
                       
                   }
                   
                   else{ //Significa que no estamos en la primera fila
                       if(isPrimera((int) Math.sqrt(orden), i)){ // Si estamos en la primera posicion de una nueva fila
                           if(j == 0){  //Si ya estamos en el norte
                               Pieza piezaPasada = piezasArmadas.get(i-(int)Math.sqrt(orden)-1); //piezasA.get(0)
                              
                               numero = piezaPasada.getSur();
                               piezaActual.getPosiciones()[0] = numero; //Le metemos la posicion sur de la pieza de arriba en la norte de la actual para el match
                            
                           }
                           else{
                                numero = aleatorio.nextInt(limite+1);  //Llenamos sus otras posiciones de numeros random
                                piezaActual.getPosiciones()[j] = numero;
                           }
                         
                       }
                       
                       else{ //En caso que sean piezas apartir de la segunda fila excluyendo las primeras piezas de cada fila
                           
                           switch (j) {
                               case 0: //Le seteamos la posicion norte el valor de la posicion sur de la pieza de arriba
                                   {
                                       Pieza piezaPasada = piezasArmadas.get(i-(int)Math.sqrt(orden)-1);
                                       numero = piezaPasada.getSur();
                                       piezaActual.getPosiciones()[0] = numero;
                                       break;
                                   }
                               case 3: //Seteamos el oeste, el valor este de la pieza de la izquierda
                                   {
                                       Pieza piezaPasada = piezasArmadas.get(i-2);
                                       numero = piezaPasada.getEste();
                                       piezaActual.getPosiciones()[3] = numero;
                                       break;
                                   }
                               default:
                                   numero = aleatorio.nextInt(limite+1);  //Llenamos sus otras posiciones de numeros random
                                   piezaActual.getPosiciones()[j] = numero;
                                   break;
                           }
                       }
                   }
                }
            }
            
            piezasArmadas.add(piezaActual);
        }
        return piezasArmadas;
    }

       //Funcion que se encarga de desordenar el arrayList de piezasArmadas 
    public static HashMap<Integer, ArrayList> desordenar(int piezas,int poblaciones){
        Random aleatorio = new Random();
        int numero;
        int numPieza = 1;
        ArrayList<Pieza> piezasA;
        ArrayList<Pieza> piezasD = new ArrayList();
        HashMap<Integer, ArrayList> poblacionesH = new HashMap();
        for(int i = 0;i<poblaciones;i++){
            piezasA = llenarPuzzle(piezas);
             while(!piezasA.isEmpty()){
                 numero = aleatorio.nextInt(piezasA.size());
                 piezasA.get(numero).setNumPieza(numPieza);
                piezasD.add(piezasA.get(numero));
                piezasA.remove(piezasA.get(numero));
                numPieza++;
            }
             poblacionesH.put(i, piezasD);
             piezasD = new ArrayList();
             numPieza=1;
        }
        System.out.println("SizeP: "+poblacionesH.get(0).size());
        return poblacionesH;
    }

    public static void PrintShuffleCrossover(HashMap<Integer, ArrayList> poblaciones1, HashMap<Integer, ArrayList> poblaciones2, HashMap<Integer, ArrayList> poblaciones3){
        AlgoritmoGenetico genetico = new AlgoritmoGenetico();
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Cruce Shuffle");
        System.out.println("Rompecabezas 3X3");
        
        HashMap<Integer, ArrayList> crucesExitosos = genetico.ShuffleCrossover(poblaciones1, 30, 50, 3);
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("\n\n\n\n\n");
        System.out.println("Rompecabezas 5X5");
        
        crucesExitosos = genetico.ShuffleCrossover(poblaciones2, 60, 60, 5);
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("\n\n\n\n\n");
        System.out.println("Rompecabezas 7X7");
        
        crucesExitosos = genetico.ShuffleCrossover(poblaciones3, 90, 70, 7); 
    }
 
    public static void geneticoCruceAnd(HashMap<Integer, ArrayList> poblaciones1,HashMap<Integer, ArrayList> poblaciones2,
        HashMap<Integer, ArrayList> poblaciones3, AlgoritmoGenetico genetico){
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Cruce por operador logico AND");
        System.out.println("3X3");
        HashMap<Integer, ArrayList> generacionesAnd3x3;
         generacionesAnd3x3 = genetico.cruceAnd(genetico.funcionFitness(poblaciones1, 3),poblaciones1, 50);   
         System.out.println("5X5");
         HashMap<Integer, ArrayList> generacionesAnd5x5;
         generacionesAnd5x5 = genetico.cruceAnd(genetico.funcionFitness(poblaciones2, 5),poblaciones2, 60);  
         
        System.out.println("7X7");
        HashMap<Integer, ArrayList> generacionesAnd7x7;
        generacionesAnd7x7 = genetico.cruceAnd(genetico.funcionFitness(poblaciones3, 7),poblaciones3, 70);  
        // System.out.println("Inicial: "+genetico.funcionFitness(poblaciones3, 3));
         System.out.println("Generaciones: "+genetico.funcionFitness(generacionesAnd7x7, 7));
    }
    
    public static void cruceKpoint (HashMap<Integer, ArrayList> poblaciones1,HashMap<Integer, ArrayList> poblaciones2,
        HashMap<Integer, ArrayList> poblaciones3,  AlgoritmoGenetico genetico){
        HashMap<Integer, Integer> matches3 = genetico.ordenarHashMap(genetico.funcionFitness(poblaciones1, 3));
        HashMap<Integer, Integer> matches5 = genetico.ordenarHashMap(genetico.funcionFitness(poblaciones2, 5));
        HashMap<Integer, Integer> matches7 = genetico.ordenarHashMap(genetico.funcionFitness(poblaciones3, 7));
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Cruce Kpoint");
        HashMap<Integer, ArrayList> generacionesAnd3x3 = genetico.kPoint(matches3,poblaciones1, 50, 3);   
        System.out.println("3X3");
        System.out.println(generacionesAnd3x3);
        System.out.println("-------------------------------------------------------------------------------");
        HashMap<Integer, ArrayList> generacionesAnd5x5 = genetico.kPoint(matches5,poblaciones2, 60, 5);   
        System.out.println("5X5");
        System.out.println(generacionesAnd5x5);
        System.out.println("-------------------------------------------------------------------------------");
        HashMap<Integer, ArrayList> generacionesAnd7x7 = genetico.kPoint(matches7,poblaciones3, 70, 7);   
        System.out.println("7X7");
        System.out.println(generacionesAnd7x7);
    
    
    }

    //Funcion principal en donde se hacen la llamada de los algoritmos
    public static void principal(){

       AlgoritmoGenetico genetico = new AlgoritmoGenetico();
       HashMap<Integer, ArrayList> poblaciones1 = desordenar(9,30);
       HashMap<Integer, ArrayList> poblaciones2 = desordenar(25,60);
       HashMap<Integer, ArrayList> poblaciones3 = desordenar(49,90);

      // geneticoCruceAnd(poblaciones1,poblaciones2,poblaciones3,genetico);
       //PrintShuffleCrossover(poblaciones1, poblaciones2, poblaciones3);
       cruceKpoint(poblaciones1,poblaciones2,poblaciones3, genetico);
   

    }

    public static void main(String[] args) {

        principal();


    }

    
}
