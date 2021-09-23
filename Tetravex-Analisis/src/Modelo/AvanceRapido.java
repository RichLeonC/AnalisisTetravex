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
                // System.out.println("Proba: "+pieza.getProbabilidad());
                 break;
            }
            else if(!pieza.isUsada()&&pieza.getProbabilidad()==2) {
                piezaInicial = pieza;
                mejor = 2;
              //  System.out.println("Proba: "+pieza.getProbabilidad());
            }
     
            else if(!pieza.isUsada() && pieza.getProbabilidad()>=mejor){
                piezaInicial = pieza;
              //  System.out.println("Proba: "+pieza.getProbabilidad());
            }

        }
        piezaInicial.setUsadaInicial(true);
        piezaInicial.setUsada(true);
        System.out.println("Size:"+posiblesIzquierdas.size());
        System.out.println("Posibles Izq: "+posiblesIzquierdas);
        System.out.println("Pieza Inicial: "+piezaInicial);
        piezasSolucion.add(piezaInicial);
       
        
       
      
        }
    
    public void armar(int limite){
        matchesPiezas();
        piezaInicial();
        Pieza pActual = piezaInicial;
        System.out.println("Size array Este: "+pActual.getDict().get(este).size());
        System.out.println("Dict: "+piezaInicial +" son: "+pActual.getDict().get(este).get(0));
        Pieza pSiguiente = pActual.getDict().get(este).get(0);
        pSiguiente.setUsada(true);
        piezasSolucion.add(pSiguiente);
  
        int cont = 0;

        for(int i =3;i<piezas.size()+3;i++){ //Recorre todas las piezas
            pActual = pSiguiente;  //Asigna a la actual la siguiente
            System.out.println("i: "+i+" es primera: "+isPrimera(limite, i));
             if(!isPrimera(limite, i)&&i<=limite){ //Pregunta si es primera pieza de fila
                  System.out.println("i: "+i);
                 ArrayList<Pieza> estes  = pActual.getDict().get(este); //Tiene todos posibles matches de actual en el este
                // System.out.println("estesSize:"+estes.size());
                 while(cont<=estes.size()&&estes.get(cont).isUsada()){ //Pregunta si el match que se escogera esta usado
                     System.out.println("cont: "+cont);
                   cont++;                                                                      //Incrementa hasta que encuentre uno no usado
                 }
                 if(cont<=estes.size()){ //Si existe algun match no usado
                     pSiguiente = pActual.getDict().get(este).get(cont);
                     System.out.println("Pieza Siguiente: "+pSiguiente);
                     pSiguiente.setUsada(true);
                     piezasSolucion.add(pSiguiente);
                 }
                 
                 cont = 0;
                
             }
             else{ //Es segunda fila o siguiente
                // System.out.println("I:"¡);
                 if(isPrimera(limite, i)){
                   pActual = piezasSolucion.get(i-limite-1); //Obtenemos como actual la pieza de arriba de la primera pieza de fila
                    ArrayList<Pieza> sures  = pActual.getDict().get(sur); //Obtenemos todos los matches en el sur de la pieza de arriba
             
                 while(cont<=sures.size()&&sures.get(cont).isUsada()){ //Preguntamos si existe algun match usado
                     //System.out.println("cont: "+cont);
                     cont++;
                 }
                 pSiguiente = pActual.getDict().get(sur).get(cont);
                 System.out.println("Pieza Siguiente: "+pSiguiente);
                 pSiguiente.setUsada(true);
                 piezasSolucion.add(pSiguiente);
                 
                 }
                 else{
                     
                     int numeroEste = pActual.getEste(); 
                     pActual = piezasSolucion.get(i-limite-1);
                   
                     ArrayList<Pieza> sures  = pActual.getDict().get(sur);
                     for (Pieza sure : sures) {
                         if(sure.getOeste() == numeroEste && !sure.isUsada()){
                             pSiguiente = sure;
                             System.out.println("Pieza Siguiente: "+pSiguiente);
                             sure.setUsada(true);
                             piezasSolucion.add(pSiguiente);
                         }
                         else{
                             
                         }
                     }
                     cont = 0;
                    
                 }
                 
                 
             }
             
        }
        System.out.println(piezasSolucion);
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
