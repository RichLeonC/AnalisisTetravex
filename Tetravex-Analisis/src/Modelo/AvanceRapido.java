/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import java.util.ArrayList;

/**
 *
 * @author richa
 */

/**
 * Esta clase recibe arrayList de piezas  
 * @author richa
 */
public class AvanceRapido {
    private final int norte = 0;
    private final int este = 1;
    private final int sur = 2;
    private final int oeste = 3;
    private final ArrayList<Pieza>piezas;
    private ArrayList<Pieza>posiblesIzquierdas;
    ArrayList<Pieza> piezasSolucion;
    private Pieza piezaInicial;
    
    private int c;
    private int a;

    public AvanceRapido(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
        posiblesIzquierdas = new ArrayList();
        piezasSolucion = new ArrayList();
    }
    
    private boolean isPrimera(int numero,int posicion){ //Verifica si la pieza pasada era multiplo
        return (posicion-1)%numero == 0;
    }

    public int getC() {
        return c;
    }

    public int getA() {
        return a;
    }
    
    
    
    //Esta funcion decide si es posible pared izquierda y le asigna una probabilidad de serlo.
    private void decidirProbabilidad(Pieza piezaActual, ArrayList<Pieza> posiblesIzquierdas){
        if(piezaActual.getDict().get(oeste).isEmpty() && piezaActual.getDict().get(norte).isEmpty() &&
          !piezaActual.getDict().get(este).isEmpty()){       
          piezaActual.setProbabilidad(3);         
          posiblesIzquierdas.add(piezaActual);
         }
        else if(piezaActual.getDict().get(oeste).isEmpty() && !piezaActual.getDict().get(este).isEmpty()){
           
         piezaActual.setProbabilidad(2);
         posiblesIzquierdas.add(piezaActual);
        }
        else if(piezaActual.getDict().get(oeste).size()%2==0 && !piezaActual.getDict().get(este).isEmpty()){
         piezaActual.setProbabilidad(1);
         posiblesIzquierdas.add(piezaActual);
        } 
    }
    //Esta funcion se encarga de llenar el diccionario de cada pieza, el cual contiene todos sus posibles conexiones.
    public void matchesPiezas(){
        
        //Recorre cada pieza que existe
        for(int k = 0;k<piezas.size();k++){
            Pieza piezaActual = piezas.get(k); //Por cada pieza recorre cada pieza
            for(int i = 0;i<piezas.size();i++){
               Pieza piezasNext = piezas.get(i);
               if(!piezaActual.equals(piezasNext)){
                   //Busca cada match de cada posicion de la pieza y lo asigna al diccionario.
                    if(piezaActual.getNorte() == piezasNext.getSur()){ 
                        piezaActual.getDict().get(norte).add(piezasNext);

                    }                   
                    if(piezaActual.getEste()== piezasNext.getOeste()){       
                        piezaActual.getDict().get(este).add(piezasNext);
                    }
                    if(piezaActual.getSur()== piezasNext.getNorte()){

                       piezaActual.getDict().get(sur).add(piezasNext);
                    }
                    if(piezaActual.getOeste()== piezasNext.getEste()){

                        piezaActual.getDict().get(oeste).add(piezasNext);
                    }
                 }
                
            }
           // System.out.println(piezaActual+" estes: "+piezaActual.getDict().get(este));
           // System.out.println(piezaActual+" sures: "+piezaActual.getDict().get(sur));
            decidirProbabilidad(piezaActual, posiblesIzquierdas);
             
        }
        //System.out.println("\n\n");
        
    }
    //Esta funcion se encarga de elegir la pieza inicial de todas las posibles paredes izquierdas
    private void piezaInicial(){
        int mejor = 1;
        for(int i = 0;i<posiblesIzquierdas.size();i++){
            Pieza pieza = posiblesIzquierdas.get(i);
            if(!pieza.isUsada()&&pieza.getProbabilidad()==3){ //Probabilidad 3 significa que es esquinaSup izq
                piezaInicial = pieza;    
                piezaInicial.setUsada(true);
                 break;
            }
            else if(!pieza.isUsada()&&pieza.getProbabilidad()==2) { //Tiene doble probabilidad de ser pared izq
                piezaInicial = pieza;
                mejor = 2;
                piezaInicial.setUsada(true);
            }
     
            else if(!pieza.isUsada() && pieza.getProbabilidad()>=mejor){ //Tiene probabilidad normal de ser pared izq
                piezaInicial = pieza;
                piezaInicial.setUsada(true);
            }
        }
        
       // System.out.println("Size:"+posiblesIzquierdas.size());
       // System.out.println("Posibles Izq: "+posiblesIzquierdas);
       // System.out.println("Pieza Inicial: "+piezaInicial);
        piezasSolucion.add(piezaInicial);
       
        
       
      
        }
    
    
    public Pieza setPieza(Pieza pSiguiente){ //Realiza un set de atributos de una pieza a otra
        Pieza pActual = new Pieza();
        pActual.setPosiciones(pSiguiente.getPosiciones());
        pActual.setDict(pSiguiente.getDict());
        pActual.setProbabilidad(pSiguiente.getProbabilidad());
        pActual.setUsada(pSiguiente.isUsada());
        pActual.setNumPieza(pSiguiente.getNumPieza());
        return pActual;
    }

    public void armarCola(int limite, ArrayList<Pieza> armado){ //Funcion recursiva que arma el tetravex 
        matchesPiezas();
        //piezaInicial();
        piezaInicial = armado.get(0); //Marcamos la pieza inicial como la esquina sup izq
        a++;
        System.out.println("A "+a);
        piezaInicial.setUsada(true);    
        piezasSolucion.add(piezaInicial);
        Pieza pActual = piezaInicial;
        a++; //asignacion
        System.out.println("A "+a);
     //   System.out.println("Indice: "+1);
     //   System.out.println("Pactual: "+pActual);
        armarAux(limite,pActual,null,2);
    }
    //Inserta la pieza que coincide en la posicion indicada (ubicacion) de la pieza pasada por parametro
    public Pieza buscaMatch(Pieza pActual,int ubicacion){ 
        int cont = 0;
        ArrayList<Pieza> ubicaciones  = pActual.getDict().get(ubicacion);
   
        if(!ubicaciones.isEmpty()) {
         
            while(cont<ubicaciones.size()&&ubicaciones.get(cont).isUsada()){ //Pregunta si el match que se escogera esta usado
               if(!piezasSolucion.contains(ubicaciones.get(cont))) ubicaciones.get(cont).setUsada(false);
                
               cont++;                                                                     //Incrementa hasta que encuentre uno no usado
            }
            if(cont>=ubicaciones.size()) return null; //Si no encontro ninguna pieza no usada, retorna null
            Pieza pSiguiente = ubicaciones.get(cont);
            pSiguiente.setUsada(true); 
            piezasSolucion.add(pSiguiente); //Agrega la pieza
            return pSiguiente;
            
        }
        return null;
    }
    
    //Funcion que verifica que pieza encaja con la que tiene al lado izquierdo y arriba de esta.
    public Pieza piezaCentral(Pieza pActual,int numeroEste){
        
        ArrayList<Pieza> sures  = pActual.getDict().get(sur); //Obtenemos los matches en el sur 
        for (Pieza sure : sures) {
            if(sure.getOeste() == numeroEste && !sure.isUsada()){ //Pregunta si encuentra una pieza que coincida
                 Pieza pSiguiente = sure;  
                 sure.setUsada(true);
                 piezasSolucion.add(pSiguiente); //Agregar
                 return pSiguiente;
             }
             
          }
        return null;
    }
    
   
    //Esta funcion se devuelve a la pieza anterior.
    public Pieza devolverse(Pieza pActual,int indice,int limite){

        pActual = piezasSolucion.get(indice-2);
        pActual.setUsada(true);
        //pActual.setPreviamenteUsada(true);
       // System.out.println("Pieza Eliminada: "+pActual);
        piezasSolucion.remove(indice-2); //La elimina de las soluciones
        
      
      // System.out.println("Nuevo pActual: "+piezasSolucion.get(indice-3));
       return piezasSolucion.get(indice-3); //Retorna la pieza anterior.
        
    }
    
    
    public int armarAux(int limite, Pieza pActual,Pieza pSiguiente,int indice){
        
        if(piezasSolucion.size() == limite*limite){ 
            c++;//comparacion
            System.out.println("C"+c);
            System.out.println(piezasSolucion);
            return 1;}
        
        else{ 
            if(indice<=limite){ //Estamos en primera fila
                c++;// comparacion
                System.out.println("C "+c);
               pActual =  buscaMatch(pActual, este);
               a++;// asignacion
               System.out.println("A "+a);
               System.out.println("------------------------------");
               System.out.println("indice: "+indice);
               System.out.println("Pactual: "+pActual);
               
              
               if(pActual!=null) {
                   c++;//comparacion
                   System.out.println("C "+c);
                   armarAux(limite, pActual, null, indice+1);
               }
          
               else armarAux(limite,devolverse(pActual, indice,limite),null,indice-1); //sino, se devuelve
   
            }
            else if(isPrimera(limite, indice)){
                c++; //comparacion
                System.out.println("C "+c);
                pActual =piezasSolucion.get(indice-limite-1);
                a++; //asignacion
                System.out.println("A "+a);
                pActual=buscaMatch(pActual, sur);
                a++; //asignacion
                System.out.println("A "+a);
                System.out.println("------------------------------");
                System.out.println("indice: "+indice);
                System.out.println("Pactual: "+pActual);
                 if(pActual!=null){
                     c++;//comparacion 
                     System.out.println("C "+c);
                     armarAux(limite, pActual, null, indice+1);
                 }
                 else armarAux(limite,devolverse(pActual, indice,limite),null,indice-1);
                 
            }
            else{
               
                int numeroEste = pActual.getEste(); 
                 a++; //asignacion
                 System.out.println("A "+a);
                pActual = piezasSolucion.get(indice-limite-1);
                 a++; //asignacion
                 System.out.println("A "+a);
                pActual = piezaCentral(pActual, numeroEste);
                 a++; //asignacion
                 System.out.println("A "+a);
                System.out.println("------------------------------");
                System.out.println("indice: "+indice);
                System.out.println("Pactual: "+pActual);
                if(pActual!=null){
                    c++; //comparacion
                    System.out.println("C "+c);
                    armarAux(limite, pActual, null, indice+1);
                }
                else armarAux(limite,devolverse(pActual, indice,limite),null,indice-1);
                
            }
        }
    
       
        return 0;
    }
  
    public ArrayList<Pieza> getPosiblesIzquierdas() {
        return posiblesIzquierdas;
    }

    public void setPosiblesIzquierdas(ArrayList<Pieza> posiblesIzquierdas) {
        this.posiblesIzquierdas = posiblesIzquierdas;
    }

    public Pieza getPiezaInicial() {
        return piezaInicial;
    }

    public void setPiezaInicial(Pieza piezaInicial) {
        this.piezaInicial = piezaInicial;
    }
    
    
    
}
