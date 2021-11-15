package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AlgoritmoGenetico {

    public AlgoritmoGenetico() {
    
    }
    
    public HashMap<Integer, Integer> ordenarHashMap(HashMap<Integer,Integer> cantidadMatches){
       HashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
       
       ArrayList<Integer> list = new ArrayList<>();
       for (Map.Entry<Integer, Integer> entry : cantidadMatches.entrySet()) {
            list.add(entry.getValue());
        }
        list.sort(Collections.reverseOrder());
        for (int num : list) {
            for (Entry<Integer, Integer> entry : cantidadMatches.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        return sortedMap;
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
                //System.out.println(j);
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
    
    public HashMap<Integer, ArrayList> ShuffleCrossover(HashMap<Integer, ArrayList> poblaciones, int cPoblaciones, int cruces){
        HashMap<Integer, Integer> cantidadMatches = funcionFitness(poblaciones, 3);
        HashMap<Integer, Integer> matchesOrdenados = ordenarHashMap(cantidadMatches);
        HashMap<Integer, ArrayList> crucesExitosos = new HashMap();
        ArrayList<Pieza> padreUno = new ArrayList();
        ArrayList<Pieza> padreDos = new ArrayList();
        ArrayList<Pieza> hijoUno = new ArrayList();
        ArrayList<Pieza> hijoDos = new ArrayList();
        ArrayList<Pieza> hijoUnoClone = new ArrayList();
        ArrayList<Pieza> hijoDosClone = new ArrayList();
        int breakPoint = (int) (Math.random() * (9 + 1));
        int contadorCruces = 0;
        int contadorPoblaciones = 1;
        
        Object[] keys = matchesOrdenados.keySet().toArray();
        
        padreUno = poblaciones.get(keys[0]);
        Collections.shuffle(padreUno);
        
        while (contadorCruces < cruces){
            
            if(contadorPoblaciones <= cPoblaciones){
                padreUno = poblaciones.get(keys[1]);
                contadorPoblaciones = 3;
            }
            
            padreDos = poblaciones.get(keys[contadorPoblaciones]);
            Collections.shuffle(padreDos);
            for (int i = 0; i < breakPoint; i++) {
                hijoUno.add(padreUno.get(i));
                hijoDos.add(padreDos.get(i));
            }
            for (int i = breakPoint + 1; i < 9; i++) {
                hijoUno.add(padreDos.get(i));
                hijoDos.add(padreUno.get(i));
            }
            
            hijoUnoClone = (ArrayList<Pieza>) hijoUno.clone();
            hijoDosClone = (ArrayList<Pieza>) hijoDos.clone();
            
            crucesExitosos.put(contadorCruces, hijoUnoClone);
            contadorCruces ++;
            
            crucesExitosos.put(contadorCruces, hijoDosClone);
            contadorCruces++;
            
            hijoUno.clear();
            hijoDos.clear();
            
            contadorPoblaciones++;
        }
        
        
        
        return crucesExitosos;
    }

}
