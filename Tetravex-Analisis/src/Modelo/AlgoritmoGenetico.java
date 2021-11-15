package Modelo;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class AlgoritmoGenetico {
    private int asigAnd;
    private int compAnd;
    private int memoriaAnd;
    private static Instrumentation instrumentation;
    public AlgoritmoGenetico() {
        asigAnd=compAnd=memoriaAnd=0;
    }
    
    
    private boolean isUltimo(int numero,int posicion){ //Verifica si la pieza pasada era multiplo limite == numero posicion== indice
        return (posicion+1)%numero == 0;//1
    }
    
    private boolean isLastLine(int limite, int posicion){
        return posicion>=(limite*limite)-limite;
    }
    
    public HashMap<Integer, Integer> funcionFitness(HashMap<Integer, ArrayList> poblaciones, int limite) {
        int matches = 0;
        HashMap<Integer, Integer> cantidadMatches = new HashMap();
        
        for (int i=0; i<poblaciones.size() ; i++){
            ArrayList<Pieza> tetravex= poblaciones.get(i);
           
        for (int j=0; j<tetravex.size(); j++){
            Pieza piezaActual = tetravex.get(j);
            if (!isLastLine(limite,j)){
                Pieza siguientePieza = tetravex.get(j+1);
                Pieza piezaAbajo =tetravex.get(j+limite);
                
                 if (isUltimo(limite, j)){
                    if (piezaActual.getSur()== piezaAbajo.getNorte())
                     matches++;
                 }  
                 else{
                    if (piezaActual.getEste() == siguientePieza.getOeste())
                         matches++;
                    if (piezaActual.getSur()== piezaAbajo.getNorte())
                        matches++;
                 }
                
            }
  
           else if(isLastLine(limite,j)){
                if ((limite*limite)-1== j){
                    cantidadMatches.put(i, matches);
                    matches=0;
                }
                
                else{
                      Pieza siguientePieza = tetravex.get(j+1);
                     if (piezaActual.getEste() == siguientePieza.getOeste())
                        matches++;
                        }
            
            }
          
        }
     }
        
        return cantidadMatches;
    }
    
    
    public void padreBinario(ArrayList<Pieza> padre){
        Random aleatorio = new Random();
        asigAnd++;
        byte bit;
        memoriaAnd+=8;
        for(int i =0;i<padre.size();i++){
            asigAnd++;
            compAnd++;
            
            bit = (byte) aleatorio.nextInt(2);
            asigAnd++;
            padre.get(i).setBin(bit);
            asigAnd++;
            
        }
        
    }
    //Funcion  que realiza el cruce and entre los dos padres y genera un hijo
    public ArrayList<Pieza> creaHijoAnd(ArrayList<Pieza> padre1,ArrayList<Pieza> padre2){
         ArrayList<Pieza> hijo = new ArrayList();
         asigAnd++;
         memoriaAnd+=176*padre1.size();
         
         for(int i =0;i<padre1.size();i++){
            asigAnd++;
            compAnd++;
            
            compAnd++; //Comparaciones del if y los else if
            compAnd++;
            compAnd++;
             if(padre1.get(i).getBin()==1 && padre2.get(i).getBin()==1 ) {
                 hijo.add(padre1.get(i));
                 asigAnd++;
             }  
             
             else if(padre1.get(i).getBin()==0 && padre2.get(i).getBin()==0 ){
                 hijo.add(padre2.get(i));
                 asigAnd++;
             }
             else if(padre1.get(i).getBin()==1 && padre2.get(i).getBin()==0) {
                 hijo.add(padre2.get(i));
                 asigAnd++;
             }            
             else {
                 hijo.add(padre1.get(i));
                 asigAnd++;
             }
         }

         return hijo;
         
    }
    
    
    //Metodo que se encarga de puntuar una sola poblacion 
    public int puntacionIndividual(ArrayList<Pieza> tetravex){
        int limite = (int) Math.sqrt(tetravex.size());
        int matches = 0;
        
        for (int j=0; j<tetravex.size(); j++){
            Pieza piezaActual = tetravex.get(j);
            if (!isLastLine(limite,j)){
                Pieza siguientePieza = tetravex.get(j+1);
                Pieza piezaAbajo =tetravex.get(j+limite);
                
                 if (isUltimo(limite, j)){
                    if (piezaActual.getSur()== piezaAbajo.getNorte())
                     matches++;
                 }  
                 else{
                    if (piezaActual.getEste() == siguientePieza.getOeste())
                         matches++;
                    if (piezaActual.getSur()== piezaAbajo.getNorte())
                        matches++;
                 }
                
            }
  
           else if(isLastLine(limite,j)){
                if ((limite*limite)-1== j){
                    return matches;
                }
                
                else{
                      Pieza siguientePieza = tetravex.get(j+1);
                     if (piezaActual.getEste() == siguientePieza.getOeste())
                        matches++;
                        }
            
            }
          
        }
        return matches;
    }
    //Metodo que se encarga de realizar el cruce 
    public HashMap<Integer,ArrayList> cruceAnd(HashMap<Integer,Integer> matches,HashMap<Integer,ArrayList> poblacionesIniciales,int totalG){
       

       ArrayList<Integer>  values = new ArrayList(matches.values()); 
       memoriaAnd+=values.size()*32;
       asigAnd++;
       values.sort(Collections.reverseOrder()); 
       HashMap<Integer,ArrayList> generaciones = new HashMap();
       memoriaAnd+=generaciones.size()*32+generaciones.size()*176;
       
       asigAnd++;
       ArrayList<Pieza> padre1 = new ArrayList();
       memoriaAnd+=176*poblacionesIniciales.get(0).size(); //176 cada pieza por el total de piezas que contiene cada poblacion
       asigAnd++;
       ArrayList<Pieza> padre2;
        memoriaAnd+=176*poblacionesIniciales.get(0).size(); 
       ArrayList<Pieza> hijo;
        memoriaAnd+=176*poblacionesIniciales.get(0).size(); 
       int puntuacionP1 = 0;
       memoriaAnd+=32;
       asigAnd++;
       
      
        for(int i =0;i<matches.size();i++){ //Recorre todos las puntuaciones
            asigAnd++;
            compAnd++;
            
            compAnd++;
            if(Objects.equals(matches.get(i), values.get(0))){ //Si la llave de una puntuacion es la de una puntuacion del arraylist de solo puntaciones
                padre1 =poblacionesIniciales.get(i); //Asigna al padre1 la poblacion que contiene esa puntuacion      
                asigAnd++;
                puntuacionP1 = matches.get(i);
                compAnd++;
            }
        }
       int gen = 0;
       memoriaAnd+=32;
       asigAnd++;
       int pob = 1;
       memoriaAnd+=32;
        asigAnd++;
       int anterior = 1;
       memoriaAnd+=32;
        asigAnd++;
        
        
        while(gen<totalG){ //Mientras no se haya llegado al total de generaciones requeridas
             compAnd++;
             compAnd++;
             if(pob>=poblacionesIniciales.size()){ //Si ya sobrepasasmos el total de poblaciones iniciales, necesitamos asignar un nuevo padre1              
               anterior++;
               asigAnd++;
               pob=anterior;
               asigAnd++;
               for(int i =0;i<matches.size();i++){
                   asigAnd++;
                   compAnd++;
                   
                   compAnd++;
                     if(Objects.equals(matches.get(i), values.get(pob-1))) {               
                        padre1 =poblacionesIniciales.get(i); 
                        asigAnd++;
                        puntuacionP1 = matches.get(i);
                         asigAnd++;
                     }
                }
             }
             while(pob<poblacionesIniciales.size()){ //Mientras haya padres con que cruzar 
                 compAnd++;
                 padre2 = poblacionesIniciales.get(pob); //Asigamos un padre2
                 asigAnd++;
                 padreBinario(padre2); //Asigna a cada pieza del padre un numero entre 0 y 1
                 hijo = creaHijoAnd(padre1, padre2); // Asigna el nuevo hijo
                 asigAnd++;
                 generaciones.put(gen, hijo); //Agregamos el hijo a las generaciones
                 asigAnd++;
                 gen++; 
                 asigAnd++;
                 pob++;
                 asigAnd++;
                 System.out.println("Padre1: "+padre1+" - puntuacion: "+puntuacionP1);
                 System.out.println("Padre2: "+padre2+" - puntuacion: "+matches.get(pob));
                 System.out.println("Hijo: "+hijo+" - puntuacion: "+puntacionIndividual(hijo));
                 compAnd++;
                 if(gen==totalG) pob=poblacionesIniciales.size(); //Si ya se llego al total de generaciones requeridas, rompemos el while
             }
             
            
        }
           
        System.out.println("Memory: "+memoriaAnd+" bits");
        System.out.println("Asignaciones: "+asigAnd);
        System.out.println("Comparaciones: "+compAnd);
        System.out.println("Cantidad de instrucciones: "+(compAnd+asigAnd));
        return generaciones;
    }
    
    
   public static void premain(String args, Instrumentation inst) {
    instrumentation = inst;
  }

  public static long getObjectSize(Object o) {
    return instrumentation.getObjectSize(o);
  }
    
}
