/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.AvanceRapido;
import Modelo.FuerzaBruta;
import Modelo.Pieza;
import java.util.ArrayList;
import java.util.Random;




public class Main {
    
    
   
    
    public static boolean isPrimera(int numero,int posicion){ //Verifica si la pieza pasada era multiplo
        return (posicion-1)%numero == 0;
    }

    public static void llenarPuzzle(int limite, ArrayList<Pieza> piezasArmadas, int orden){
        Random aleatorio = new Random();
        int numero;
        Pieza piezaActual;
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
                       
                       else{
                           
                           switch (j) {
                               case 0:
                                   {
                                       Pieza piezaPasada = piezasArmadas.get(i-(int)Math.sqrt(orden)-1);
                                       numero = piezaPasada.getSur();
                                       piezaActual.getPosiciones()[0] = numero;
                                       break;
                                   }
                               case 3:
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
            //System.out.println(piezaActual);
           
        }
    
    }
    
    public static void desordenar(ArrayList<Pieza> piezasD, ArrayList<Pieza> piezasA){
        Random aleatorio = new Random();
        int numero;
        int numPieza = 1;
        //System.out.println(piezasA.size());
        while(!piezasA.isEmpty()){
            numero = aleatorio.nextInt(piezasA.size());
            piezasA.get(numero).setNumPieza(numPieza);
            piezasD.add(piezasA.get(numero));
            piezasA.remove(piezasA.get(numero));
            numPieza++;
       }
    
    }
    
    public static void principal(){
        ArrayList<Pieza> piezasArmadas = new ArrayList();
        ArrayList<Pieza> piezasDesordenadas = new ArrayList();
        ArrayList<Pieza> temporalArmado;
        int orden = 9;
       
        llenarPuzzle(15, piezasArmadas, 3*3);
        temporalArmado = (ArrayList<Pieza>) piezasArmadas.clone();
        System.out.println(piezasArmadas);
        desordenar(piezasDesordenadas, piezasArmadas);

      
        AvanceRapido rapido = new AvanceRapido(piezasDesordenadas);
       
        rapido.armarCola(3, temporalArmado);
     
        //System.out.println("Armado -> " + temporalArmado);
       // FuerzaBruta.comparar(piezasDesordenadas, temporalArmado, orden);
    }

    public static void main(String[] args) {
        principal();
        
       
    }
    
}
