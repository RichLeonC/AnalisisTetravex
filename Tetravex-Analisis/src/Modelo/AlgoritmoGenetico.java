package Modelo;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class AlgoritmoGenetico {
    private int asigAnd;
    private int compAnd;
    private int memoriaAnd;
    private static Instrumentation instrumentation;
    public AlgoritmoGenetico() {
        asigAnd=compAnd=memoriaAnd=0;
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
    

    
    public HashMap<Integer, ArrayList> ShuffleCrossover(HashMap<Integer, ArrayList> poblaciones, int cPoblaciones, int cruces, int dimension){
        HashMap<Integer, Integer> cantidadMatches = funcionFitness(poblaciones, dimension); //Guarda la cantidad de matches de los rompecabezas padres 
        HashMap<Integer, Integer> matchesOrdenados = ordenarHashMap(cantidadMatches); //Ordena la cantidad de matches de mayor a menor
        HashMap<Integer, ArrayList> crucesExitosos = new HashMap(); //HashMap que guardará los cruces hijos
        ArrayList<Pieza> padreUno = new ArrayList(); //Array padre uno
        ArrayList<Pieza> padreDos = new ArrayList(); //Array padre dos
        ArrayList<Pieza> hijoUno = new ArrayList(); //Array hijo uno
        ArrayList<Pieza> hijoDos = new ArrayList(); //Array hijo dos
        ArrayList<Pieza> hijoUnoClone = new ArrayList(); //Clon del array hijo uno
        ArrayList<Pieza> hijoDosClone = new ArrayList(); //Clon del array hijo dos
        ArrayList<Integer>  values = new ArrayList(matchesOrdenados.values()); //Array con los matches ordenados
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
            int breakPoint = (int) (Math.random() * ((dimension*dimension) + 1)); //Punto de quiebre para el cruce
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
            for (int i = breakPoint + 1; i < (dimension*dimension); i++) { //Cuando se pasa el punto de cruce, se cambia a la informacion del segundo padre
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
        
        System.out.println("--------------------TOP 5 HIJOS--------------------------------------");
        //topHijos(crucesExitosos);
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Asignaciones: " + asignaciones + ", Comparaciones: " + comparaciones);
        System.out.println("Cantidad total de instrucciones: " + (asignaciones + comparaciones));
        return crucesExitosos;
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
        System.out.println("--------------------------TOP 5 HIJOS---------------------------");
        topHijos(generaciones);
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Memory: "+memoriaAnd+" bits");
        System.out.println("Asignaciones: "+asigAnd);
        System.out.println("Comparaciones: "+compAnd);
        System.out.println("Cantidad de instrucciones: "+(compAnd+asigAnd));
       
        return generaciones;
    }
    
    public void topHijos( HashMap<Integer,ArrayList> generaciones){
        int limite = (int) Math.sqrt(generaciones.get(0).size());

        System.out.println("Limite: "+limite);
        HashMap<Integer,Integer> puntuaciones = funcionFitness(generaciones, limite);
        ArrayList<Integer>  values = new ArrayList(puntuaciones.values()); 
        Collections.sort(values, Collections.reverseOrder());  
        int cont = 0;
         for(int i =0;i<puntuaciones.size();i++){ //Recorre todos las puntuaciones
            if(Objects.equals(puntuaciones.get(i), values.get(cont))&&cont<5){ //Si la llave de una puntuacion es la de una puntuacion del arraylist de solo puntaciones
                cont++;
                System.out.println("Hijo: "+generaciones.get(i)+ "\n puntuacion: "+puntuaciones.get(i));
                i=0;
            }
        }
    }
  
    
}
