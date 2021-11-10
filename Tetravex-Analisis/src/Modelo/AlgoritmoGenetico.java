package Modelo;

import java.util.ArrayList;
import java.util.HashMap;

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
            Pieza siguientePieza = tetravex.get(j++);
            Pieza piezaAbajo =tetravex.get(j+limite);
            if (j<limite-1){
                if (piezaActual.getEste() == siguientePieza.getOeste())
                    matches++;
                if (piezaActual.getSur()== piezaAbajo.getNorte())
                        matches++;
            }
            if (isUltimo(limite, j)){
                if (piezaActual.getSur()== piezaAbajo.getNorte())
                        matches++;
            }
            
            if(isLastLine(limite,j)){
                if ((limite*limite)-1== j){
                    cantidadMatches.put(i, matches);
                    matches=0;
                }
                if (piezaActual.getEste() == siguientePieza.getOeste())
                    matches++;
            
            }
        
        
        }
     }
        
        return cantidadMatches;
    }
    
}
