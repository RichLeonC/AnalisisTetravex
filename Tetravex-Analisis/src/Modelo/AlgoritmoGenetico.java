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
        HashMap<Integer, Integer> cantidadMatches = funcionFitness(poblaciones, 3); //Guarda la cantidad de matches de los rompecabezas padres 
        HashMap<Integer, Integer> matchesOrdenados = ordenarHashMap(cantidadMatches); //Ordena la cantidad de matches de mayor a menor
        HashMap<Integer, ArrayList> crucesExitosos = new HashMap(); //HashMap que guardará los cruces hijos
        ArrayList<Pieza> padreUno = new ArrayList(); //Array padre uno
        ArrayList<Pieza> padreDos = new ArrayList(); //Array padre dos
        ArrayList<Pieza> hijoUno = new ArrayList(); //Array hijo uno
        ArrayList<Pieza> hijoDos = new ArrayList(); //Array hijo dos
        ArrayList<Pieza> hijoUnoClone = new ArrayList(); //Clon del array hijo uno
        ArrayList<Pieza> hijoDosClone = new ArrayList(); //Clon del array hijo dos
        ArrayList<Integer>  values = new ArrayList(matchesOrdenados.values()); //Array con los matches ordenados
        int breakPoint = (int) (Math.random() * (9 + 1)); //Punto de quiebre para el cruce
        int contadorCruces = 0; //Contador de la cantidad de cruces realizados
        int contadorPoblaciones = 1; //Contador de poblaciones cruzadas
        int comparaciones = 0;
        int asignaciones = 0;
        
        values.sort(Collections.reverseOrder()); //Ordena los matches de mayor a menor
        asignaciones ++;
        System.out.println("Tipo de cruce: Cruce Shuffle");
        
        Object[] keys = matchesOrdenados.keySet().toArray(); //Array con las llaves ordenadas de las poblaciones ordenadas
        asignaciones ++;
        
        padreUno = poblaciones.get(keys[0]); //Al padre uno se le asigna la mejor poblacion
        asignaciones ++;
        Collections.shuffle(padreUno); //Se desordena la poblacion del primer padre
        
        while (contadorCruces < cruces){ //Bucle que se detiene hasta que se hayan realizado todos los cruces solicitados
            comparaciones ++;
            System.out.println("Padre 1: " + padreUno + "Puntuacion: " + values.get(0));
            
            if(contadorPoblaciones == cPoblaciones){ //Si ya no hay más cruces con la mejor poblacion, se cambia a la segunda mejor
                padreUno = poblaciones.get(keys[1]); //Se cambia el valor del primer padre
                contadorPoblaciones = 3;
                System.out.println("Padre 1: " + padreUno + "Puntuacion: " + values.get(1));
                
                asignaciones += 2;
                comparaciones ++;
            }
            
            padreDos = poblaciones.get(keys[contadorPoblaciones]); //Se asigna el segundo padre
            asignaciones ++;
            Collections.shuffle(padreDos); //Se desordena la poblacion del segundo padre
            System.out.println("Padre 2: " + padreDos + "Puntuacion: " + values.get(contadorPoblaciones));
            for (int i = 0; i < breakPoint; i++) { //Se crean los hijos con la informacion de un padre hasta el punto de cruce
                hijoUno.add(padreUno.get(i)); //Se añade informacion al hijo uno
                hijoDos.add(padreDos.get(i)); //Se añade informacion al hijo dos
                comparaciones ++;
                asignaciones ++;
            }
            for (int i = breakPoint + 1; i < 9; i++) { //Cuando se pasa el punto de cruce, se cambia a la informacion del segundo padre
                hijoUno.add(padreDos.get(i)); //Se añade informacion al hijo uno
                hijoDos.add(padreUno.get(i)); //Se añade informacion al hijo dos
                comparaciones ++;
                asignaciones ++;
            }
            
            hijoUnoClone = (ArrayList<Pieza>) hijoUno.clone(); //Se clona al hijo uno para no tener poblemas de dirección de memoria
            asignaciones ++;
            System.out.println("Hijo 1: " + hijoUnoClone + "puntuacion: " + puntacionIndividual(hijoUnoClone));
            
            hijoDosClone = (ArrayList<Pieza>) hijoDos.clone(); //Se clona al hijo dos para no tener poblemas de dirección de memoria
            asignaciones ++;
            System.out.println("Hijo 2: " + hijoDosClone + "puntuacion: " + puntacionIndividual(hijoDosClone));
            
            crucesExitosos.put(contadorCruces, hijoUnoClone); //Se añade el hijo uno a la hash de cruces exitosos
            contadorCruces ++;
            
            crucesExitosos.put(contadorCruces, hijoDosClone); //Se añade el hijo dos a la hash de cruces exitosos
            contadorCruces++;
            
            hijoUno.clear(); //Se limpia el hijo uno para seguir con el proceso
            hijoDos.clear(); //Se limpia el hijo dos para seguir con el proceso
            
            contadorPoblaciones++;
        }
        
        
        System.out.println("Asignaciones: " + asignaciones + ", Comparaciones: " + comparaciones);
        System.out.println("Cantidad total de instrucciones: " + (asignaciones + comparaciones));
        return crucesExitosos;
    }

}
