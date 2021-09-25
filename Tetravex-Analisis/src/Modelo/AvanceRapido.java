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

    public AvanceRapido(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
        
        posiblesIzquierdas = new ArrayList();
        piezasSolucion = new ArrayList();
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
    
    private void matchesPiezas(){
        
        for(int k = 0;k<piezas.size();k++){
            Pieza piezaActual = piezas.get(k);
            for(int i = 1;i<piezas.size();i++){
               Pieza piezasNext = piezas.get(i);
               if(!piezaActual.equals(piezasNext)){
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
            decidirProbabilidad(piezaActual, posiblesIzquierdas);
             
        }
        
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
    
    public void colocaPiezas(int ubicacion, Pieza pActual,Pieza pSiguiente){
        int cont = 0;
        ArrayList<Pieza> ubicaciones  = pActual.getDict().get(ubicacion); //Tiene todos posibles matches de actual en el este  
        System.out.println("cont"+cont+"&& "+ubicaciones.get(cont)+" "+ubicaciones.get(cont).isUsada());
        while(cont<=ubicaciones.size()&&ubicaciones.get(cont).isUsada()){ //Pregunta si el match que se escogera esta usado
            cont++;                                                                      //Incrementa hasta que encuentre uno no usado
             System.out.println("cont: "+cont);
          }
          if(cont<=ubicaciones.size()){ //Si existe algun match no usado
            pSiguiente = ubicaciones.get(cont);
            System.out.println("Pieza SiguienteX: "+pSiguiente);
            pSiguiente.setUsada(true);
             piezasSolucion.add(pSiguiente);
           }
        
    }
    
    public boolean existePiezaCentral(Pieza pActual,Pieza pSiguiente, int numeroEste){
        ArrayList<Pieza> sures  = pActual.getDict().get(sur);
        for (Pieza sure : sures) {
            if(sure.getOeste() == numeroEste && !sure.isUsada()){
                 pSiguiente = sure;
                 System.out.println("Pieza Siguiente: "+pSiguiente);
                 sure.setUsada(true);
                 piezasSolucion.add(pSiguiente);
                 return true;
             }
             
          }
        return false;
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
    
    public void armar(int limite, ArrayList<Pieza> armado){
        
        matchesPiezas();
        //piezaInicial();
        piezaInicial = armado.get(0);
        piezaInicial.setUsadaInicial(true);
        piezaInicial.setUsada(true);
        piezasSolucion.add(piezaInicial);
        Pieza pActual;
        Pieza pSiguiente = primeraPieza();
       
        
        int cont = 0;
        int i = 3;
        //for(int i =3;i<(piezas.size()+3);i++){ //Recorre todas las piezas
        while(piezasSolucion.size()<limite*limite){
            
            
            pActual = setPieza(pSiguiente);  //Asigna a la actual la siguiente
            //System.out.println(pActual+"Usada: "+pActual.isUsada());
            System.out.println("i: "+i+" es primera: "+isPrimera(limite, i));
             if(!isPrimera(limite, i)&&i<=limite){ //Pregunta si no es primera pieza de la primera fila
                 System.out.println("x");
               colocaPiezas(este, pActual, pSiguiente);
             }
             else{ //Es segunda fila o siguiente
               
                 if(isPrimera(limite, i)){ //Si es primera pieza de fila
                   System.out.println("y");
                   pActual = setPieza(piezasSolucion.get(i-limite-1)); //Obtenemos como actual la pieza de arriba de la primera pieza de fila
                     System.out.println("ACTUAL: "+pActual);
                   colocaPiezas(sur, pActual, pSiguiente);
                 }
                 else{ //Significa que es la segunda pieza de fila o posteriores
                     
                     int numeroEste = pActual.getEste(); 
                     pActual = piezasSolucion.get(i-limite-1);
                     existePiezaCentral(pActual, pSiguiente,numeroEste); //Busca pieza que encaje con la de arriba e izquierda
                     cont = 0;
                    
                 }
      
             }
             i++;
        }
        System.out.println(piezasSolucion);
    }
    
    public void armarCola(int limite, ArrayList<Pieza> armado){
        matchesPiezas();
        //piezaInicial();
        piezaInicial = armado.get(0);
        piezaInicial.setUsadaInicial(true);
        piezaInicial.setUsada(true);
        piezasSolucion.clear();
        piezasSolucion.add(piezaInicial);
        Pieza pActual = piezaInicial;
        System.out.println("indice: "+1);
        System.out.println("Pactual: "+pActual);
        armarAux(limite,pActual,null,2);
    }
    public Pieza buscaMatch(Pieza pActual,int ubicacion){
        int cont = 0;
        ArrayList<Pieza> ubicaciones  = pActual.getDict().get(ubicacion);
        if(!ubicaciones.isEmpty()) {
            //System.out.println("ubic size: "+ubicaciones.size());
            while(cont<ubicaciones.size()&&ubicaciones.get(cont).isUsada()){ //Pregunta si el match que se escogera esta usado
                System.out.println(cont);
                if(cont>=ubicaciones.size()) break;
                else cont++;
                                                                                    //Incrementa hasta que encuentre uno no usado
            }
            if(cont>=ubicaciones.size()) return null;
            Pieza pSiguiente = ubicaciones.get(cont);
           // System.out.println("Pieza SiguienteX: "+pSiguiente);
            pSiguiente.setUsada(true); 
            piezasSolucion.add(pSiguiente);
            return pSiguiente;
            
        }
        return null;
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
    
    public Pieza devolverse(Pieza pActual,int indice){
        pActual = piezasSolucion.get(indice-2);
       return null;
        
    }
    public int armarAux(int limite, Pieza pActual,Pieza pSiguiente,int indice){
        
        if(piezasSolucion.size() == limite*limite) return 1;
        
        else if(tieneSolucion()){
            if(indice<=limite){ //Estamos en primera fila
               
              // Pieza pTemporal = setPieza(pActual);
               pActual =  buscaMatch(pActual, este);
               System.out.println("indice: "+indice);
               System.out.println("Pactual: "+pActual);
               if(pActual!=null) armarAux(limite, pActual, null, indice+1);
               else{
                //   armarAux(limite,piezasSolucion.get(indice-2),null,indice-1);
                  // pTemporal.setUsada(false);
               }
               
            }
            else if(isPrimera(limite, indice)){
           
                pActual =piezasSolucion.get(indice-limite-1);   
                pActual=buscaMatch(pActual, sur);
                System.out.println("indice: "+indice);
                System.out.println("Pactual: "+pActual);
                 if(pActual!=null) armarAux(limite, pActual, null, indice+1);
                 
            }
            else{
               
                int numeroEste = pActual.getEste(); 
                pActual = piezasSolucion.get(indice-limite-1);
                pActual = piezaCentral(pActual, numeroEste);
                System.out.println("indice: "+indice);
                System.out.println("Pactual: "+pActual);
                if(pActual!=null) armarAux(limite, pActual, null, indice+1);
                
            }
        }
        else{
            
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
