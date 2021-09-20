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
    private Pieza piezaInicial;

    public AvanceRapido(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
        posiblesIzquierdas = new ArrayList();
    }
    
    public void matchesPiezas(){
       // System.out.println("xd");
        for(int k = 0;k<piezas.size();k++){
            Pieza piezaActual = piezas.get(k);
            for(int i = 1;i<piezas.size();i++){
               Pieza piezasNext = piezas.get(i);
              //  System.out.println("Actual Norte = "+piezaActual.getNorte()+" == next ="+piezasNext.getNorte());
               if(piezaActual.getNorte() == piezasNext.getNorte()){
                  // System.out.println("AA");
                   piezaActual.getDict().replace(norte,piezaActual.getDict().get(norte)+1);
                   
               }
               if(piezaActual.getEste()== piezasNext.getEste()){
                  // System.out.println("BB");
                   piezaActual.getDict().replace(este,piezaActual.getDict().get(este)+1);
               }
               if(piezaActual.getSur()== piezasNext.getSur()){
                  // System.out.println("CC");
                   piezaActual.getDict().replace(sur,piezaActual.getDict().get(sur)+1);
               }
               
               if(piezaActual.getOeste()== piezasNext.getOeste()){
                 //  System.out.println("DD");
                   piezaActual.getDict().replace(oeste,piezaActual.getDict().get(oeste)+1);
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
    
    public void izquierdaEscogida(){
       
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
