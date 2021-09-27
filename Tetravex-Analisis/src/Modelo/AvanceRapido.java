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
public class AvanceRapido {
    private final int norte = 0;
    private final int este = 1;
    private final int sur = 2;
    private final int oeste = 3;
    private ArrayList<Pieza>piezas;
    private ArrayList<Pieza>posiblesIzquierdas;
    ArrayList<Pieza> piezasSolucion;
    private Pieza piezaInicial;
    private int vueltas;

    public AvanceRapido(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
        
        posiblesIzquierdas = new ArrayList();
        piezasSolucion = new ArrayList();
    }

    public int getVueltas() {
        return vueltas;
    }
    
    
    private boolean isPrimera(int numero,int posicion){ //Verifica si la pieza pasada era multiplo
        return (posicion-1)%numero == 0;
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
    
    public void matchesPiezas(){
        
        for(int k = 0;k<piezas.size();k++){
            Pieza piezaActual = piezas.get(k);
           // vueltas++;
            for(int i = 0;i<piezas.size();i++){
               Pieza piezasNext = piezas.get(i);
               if(!piezaActual.equals(piezasNext)){
                   
                    if(piezaActual.getNorte() == piezasNext.getSur()){
                        piezaActual.getDict().get(norte).add(piezasNext);

                    }
                    //if(piezaActual.getNumPieza() == 2) System.out.println("ActEste: "+piezaActual.getEste()+" == "+piezasNext.getOeste());
                    if(piezaActual.getEste()== piezasNext.getOeste()){
                      //  if(piezaActual.getNumPieza() == 2) System.out.println("Entre");
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
            System.out.println(piezaActual+" estes: "+piezaActual.getDict().get(este));
            System.out.println(piezaActual+" sures: "+piezaActual.getDict().get(sur));
            decidirProbabilidad(piezaActual, posiblesIzquierdas);
             
        }
        System.out.println("\n\n");
        
    }
    
    private void piezaInicial(){
        int mejor = 1;
        for(int i = 0;i<posiblesIzquierdas.size();i++){
            Pieza pieza = posiblesIzquierdas.get(i);
            if(!pieza.isUsada()&&pieza.getProbabilidad()==3){
                piezaInicial = pieza;    
                piezaInicial.setUsadaInicial(true);
                piezaInicial.setUsada(true);
                 break;
            }
            else if(!pieza.isUsada()&&pieza.getProbabilidad()==2) {
                piezaInicial = pieza;
                mejor = 2;
                piezaInicial.setUsadaInicial(true);
                piezaInicial.setUsada(true);
            }
     
            else if(!pieza.isUsada() && pieza.getProbabilidad()>=mejor){
                piezaInicial = pieza;
                piezaInicial.setUsadaInicial(true);
                piezaInicial.setUsada(true);
            }
        }
        
        System.out.println("Size:"+posiblesIzquierdas.size());
        System.out.println("Posibles Izq: "+posiblesIzquierdas);
        System.out.println("Pieza Inicial: "+piezaInicial);
        piezasSolucion.add(piezaInicial);
       
        
       
      
        }
    
    public Pieza primeraPieza(){
       
        System.out.println("Size array Este: "+piezaInicial.getDict().get(este).size());
        System.out.println(piezaInicial);
        Pieza pSiguiente = piezaInicial.getDict().get(este).get(0);
        System.out.println(pSiguiente);
        pSiguiente.setUsada(true);
        piezasSolucion.add(pSiguiente);
        return pSiguiente;
    }
    
    
    

    
    public Pieza setPieza(Pieza pSiguiente){
        Pieza pActual = new Pieza();
        pActual.setPosiciones(pSiguiente.getPosiciones());
        pActual.setDict(pSiguiente.getDict());
        pActual.setProbabilidad(pSiguiente.getProbabilidad());
        pActual.setUsada(pSiguiente.isUsada());
        pActual.setUsadaInicial(pSiguiente.isUsadaInicial());
        pActual.setNumPieza(pSiguiente.getNumPieza());
        return pActual;
    }

    public void armarCola(int limite, ArrayList<Pieza> armado){
        matchesPiezas();
        //piezaInicial();
        piezaInicial = armado.get(0);
        piezaInicial.setUsadaInicial(true);
        piezaInicial.setUsada(true);    
        piezasSolucion.add(piezaInicial);
        Pieza pActual = piezaInicial;
        System.out.println("Indice: "+1);
        System.out.println("Pactual: "+pActual);
        armarAux(limite,pActual,null,2);
    }
    
    
    public Pieza buscaMatch(Pieza pActual,int ubicacion){
        int cont = 0;
        ArrayList<Pieza> ubicaciones  = pActual.getDict().get(ubicacion);
        System.out.println("En este punto pActual esta en buscar matches y su estado es:" + pActual.isUsada());
    
     
        
        //System.out.println("ubic size: "+ubicaciones.size());
        if(!ubicaciones.isEmpty()) {
               for (Pieza piezas: ubicaciones){
                if(!piezasSolucion.contains(piezas)) piezas.setUsada(false);
                if (piezas.isPreviamenteUsada() && piezas.isUsada()){
                    
                    cont++;
                    break;}
                }}
        //    while(cont<ubicaciones.size()&&(ubicaciones.get(cont).isUsada()||ubicaciones.get(cont).isPreviamenteUsada())){ //Pregunta si el match que se escogera esta usado
               // if(!piezasSolucion.contains(ubicaciones.get(cont))) ubicaciones.get(cont).setUsada(false);
   
              //  if(cont>=ubicaciones.size()){
                //    for(Pieza pieza:ubicaciones){
                  //      System.out.println("x");
                    //    System.out.println("En este punto pActual esta en buscar matches antes de un ify su estado es:" + pActual.isUsada());
                   
                      //  System.out.println("En este punto pActual esta en buscar matches despues de un ify su estado es:" + pActual.isUsada());
                   // }
                   // break;
               // }
                //else cont++;                                                                     //Incrementa hasta que encuentre uno no usado
            
            if(cont>=ubicaciones.size()) return null;
            Pieza pSiguiente = ubicaciones.get(cont);
            
           // System.out.println("Pieza SiguienteX: "+pSiguiente);
            pSiguiente.setUsada(true); 
            System.out.println("En este punto pSiguiente esta en buscar maches desde pues de ser elejida:" + pSiguiente.isUsada());
            piezasSolucion.add(pSiguiente);
            return pSiguiente;

        
        //return null;
    }

    public Pieza piezaCentral(Pieza pActual,int numeroEste){
        
        ArrayList<Pieza> sures  = pActual.getDict().get(sur);
        for (Pieza sure : sures) {
            if(sure.getOeste() == numeroEste && !sure.isUsada()){
                 Pieza pSiguiente = sure;
                // System.out.println("Pieza Siguiente: "+pSiguiente);
                 sure.setUsada(true);
                 piezasSolucion.add(pSiguiente);
                 return pSiguiente;
             }
             
          }
        return null;
    }
    
    public boolean tieneSolucion(){
        for(Pieza p : piezas){
            if(!p.isUsada()) return true;
        }
        return false;
    }
    
    public Pieza devolverse(Pieza pActual,int indice,int limite){
       // piezasSolucion.remove(indice-2);
        pActual = piezasSolucion.get(indice-2);
        System.out.println("En este punto pActual esta en devolverse antes de ser eliminada:" + pActual.isUsada());
        pActual.setUsada(false);
        System.out.println("En este punto pActual esta en devolver despues de ser eliminada:" + pActual.isUsada());
        pActual.setPreviamenteUsada(true);
        System.out.println("Pieza Eliminada: "+pActual);
        piezasSolucion.remove(indice-2);
        
      
        System.out.println("Nuevo pActual: "+piezasSolucion.get(indice-3));
       return piezasSolucion.get(indice-3);
        
    }
    public int armarAux(int limite, Pieza pActual,Pieza pSiguiente,int indice){
        
        if(piezasSolucion.size() == limite*limite) return 1;
        
        else if(tieneSolucion()){
            if(indice<=limite){ //Estamos en primera fila
                vueltas++;
             //  Pieza pTemporal = setPieza(pActual);
               pActual =  buscaMatch(pActual, este);
                System.out.println("------------------------------");
               System.out.println("indice: "+indice);
               System.out.println("Pactual: "+pActual);
           //    System.out.println("En este punto pActual esta en armarAux una vez ingresada en el arreglo solucion:" + pActual.isUsada());
               
              
               if(pActual!=null) {
                   
                   armarAux(limite, pActual, null, indice+1);
               }
               else{
               
                   armarAux(limite,devolverse(pActual, indice,limite),null,indice-1);
                  // pTemporal.setUsada(false);
               }
               
            }
            else if(isPrimera(limite, indice)){
            vueltas++;
                pActual =piezasSolucion.get(indice-limite-1);   
                pActual=buscaMatch(pActual, sur);
                System.out.println("------------------------------");
                System.out.println("indice: "+indice);
                System.out.println("Pactual: "+pActual);
              //  System.out.println("En este punto pActual esta en  una vez ingresada en el arreglo solucion:" + pActual.isUsada());
                 if(pActual!=null){
                      vueltas++;
                     armarAux(limite, pActual, null, indice+1);
                 }
                 else{ vueltas++;
                 armarAux(limite,devolverse(pActual, indice,limite),null,indice-1);}
                 
            }
            else{
                vueltas++;
                int numeroEste = pActual.getEste(); 
                pActual = piezasSolucion.get(indice-limite-1);
                pActual = piezaCentral(pActual, numeroEste);
                System.out.println("------------------------------");
                System.out.println("indice: "+indice);
                System.out.println("Pactual: "+pActual);
                //System.out.println("En este punto pActual esta en  una vez ingresada en el arreglo solucion:" + pActual.isUsada());
                if(pActual!=null){
                   
                    armarAux(limite, pActual, null, indice+1);
                }
                else {  vueltas++; armarAux(limite,devolverse(pActual, indice,limite),null,indice-1);
                }
                
            }
        }
        else{
           // armarAux(limite,devolverse(pActual, indice,limite),null,indice-1);
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
