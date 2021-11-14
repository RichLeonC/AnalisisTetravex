package Modelo;

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

    public AlgoritmoGenetico() {
    
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
        byte bit ;
        for(int i =0;i<padre.size();i++){
            bit = (byte) aleatorio.nextInt(2);
            padre.get(i).setBin(bit);
            
        }
        
    }
    //Funcion  que realiza el cruce and entre los dos padres y genera un hijo
    public ArrayList<Pieza> creaHijoAnd(ArrayList<Pieza> padre1,ArrayList<Pieza> padre2){
         ArrayList<Pieza> hijo = new ArrayList();

         for(int i =0;i<padre1.size();i++){
             if(padre1.get(i).getBin()==1 && padre2.get(i).getBin()==1 ) hijo.add(padre1.get(i));          
             else if(padre1.get(i).getBin()==0 && padre2.get(i).getBin()==0 ) hijo.add(padre2.get(i));
             else if(padre1.get(i).getBin()==1 && padre2.get(i).getBin()==0) hijo.add(padre2.get(i));            
             else hijo.add(padre1.get(i));
         }

         return hijo;
         
    }
    //Metodo que se encarga de realizar el cruce 
    public HashMap<Integer,ArrayList> cruceAnd(HashMap<Integer,Integer> matches,HashMap<Integer,ArrayList> poblacionesIniciales,int totalG){
       ArrayList<Integer>  values = new ArrayList(matches.values()); //
       values.sort(Collections.reverseOrder());
       HashMap<Integer,ArrayList> generaciones = new HashMap();
       ArrayList<Pieza> padre1 = new ArrayList();
       ArrayList<Pieza> padre2;
       ArrayList<Pieza> hijo;
        for(int i =0;i<matches.size();i++){ //Recorre todos las puntuaciones
            if(Objects.equals(matches.get(i), values.get(0)))  //Si la llave de una puntuacion es la de una puntuacion del arraylist de solo puntaciones
                padre1 =poblacionesIniciales.get(i); //Asigna al padre1 la poblacion que contiene esa puntuacion      
        }
       int gen = 0;
       int pob = 1;
       int anterior = 1;
        while(gen<totalG){ //Mientras no se haya llegado al total de generaciones requeridas
            
             if(pob>=poblacionesIniciales.size()){

               anterior++;
               pob=anterior;
               for(int i =0;i<matches.size();i++){
                     if(Objects.equals(matches.get(i), values.get(pob-1))) 
                        padre1 =poblacionesIniciales.get(i);                     
                }
             }
             while(pob<poblacionesIniciales.size()){ //Mientras haya padres con que cruzar 

                 padre2 = poblacionesIniciales.get(pob); //Asigamos un padre2
                 padreBinario(padre2);
                 hijo = creaHijoAnd(padre1, padre2);
                 generaciones.put(gen, hijo);
                 gen++;
                 pob++;
                 if(gen==totalG) pob=poblacionesIniciales.size(); //Si ya se llego al total de generaciones requeridas, rompemos el while
             }
             
            
        }
           
        
        return generaciones;
    }
    
}
