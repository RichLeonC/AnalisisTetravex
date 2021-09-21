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
    
    public void matchesPiezas(){
       // System.out.println("xd");
        
        for(int k = 0;k<piezas.size();k++){
            Pieza piezaActual = piezas.get(k);
            for(int i = 1;i<piezas.size();i++){
               Pieza piezasNext = piezas.get(i);
             
               if(piezaActual.getNorte() == piezasNext.getNorte()){
                   piezaActual.getDict().get(norte).add(piezasNext.getNumPieza());
                   
               }
               if(piezaActual.getEste()== piezasNext.getEste()){
                
                   piezaActual.getDict().get(este).add(piezasNext.getNumPieza());
               }
               if(piezaActual.getSur()== piezasNext.getSur()){
                 
                  piezaActual.getDict().get(sur).add(piezasNext.getNumPieza());
               }
               
               if(piezaActual.getOeste()== piezasNext.getOeste()){
                
                   piezaActual.getDict().get(oeste).add(piezasNext.getNumPieza());
               }
            }
            
            if(piezaActual.getOeste()  == 0 && piezaActual.getNorte() == 0){
           
                  piezaActual.setProbabilidad(3);
                   
                   posiblesIzquierdas.add(piezaActual);
               }
            else if(piezaActual.getOeste() == 0){
           
                   piezaActual.setProbabilidad(2);
                   posiblesIzquierdas.add(piezaActual);
               }
             else if(piezaActual.getOeste()%2==0){
             
                   piezaActual.setProbabilidad(1);
                   posiblesIzquierdas.add(piezaActual);
               }  
        }
        
    }
    
    public void piezaInicial(){
        for(int i = 0;i<posiblesIzquierdas.size();i++){
            if(!posiblesIzquierdas.get(i).isUsada()){
                piezaInicial = posiblesIzquierdas.get(i);
                 piezaInicial.setUsadaInicial(true);
            }
        }
        piezasSolucion.add(piezaInicial);
        
       
      
    }
    
    public void armar(int limite){
        Pieza pActual = piezaInicial;
        for(int i =0;i<piezas.size();i++){
            Pieza pSiguiente = piezas.get(i);
             if(!isPrimera(limite, i)){
                 if(pActual.getEste() == pSiguiente.getEste()){
                     
                 }
             }
        }
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
