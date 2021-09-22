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
    
    private void matchesPiezas(){
       // System.out.println("xd");
        
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
            //System.out.println("Oeste: "+piezaActual.getOeste()+" Norte: "+piezaActual.getNorte());
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
        
    }
    
    private void piezaInicial(){
        int mejor = 1;
        for(int i = 0;i<posiblesIzquierdas.size();i++){
            Pieza pieza = posiblesIzquierdas.get(i);
            if(!pieza.isUsada()&&pieza.getProbabilidad()==3){
                piezaInicial = pieza;
                 System.out.println("Proba: "+pieza.getProbabilidad());
                 break;
            }
            else if(!pieza.isUsada()&&pieza.getProbabilidad()==2) {
                piezaInicial = pieza;
                mejor = 2;
                System.out.println("Proba: "+pieza.getProbabilidad());
            }
     
            else if(!pieza.isUsada() && pieza.getProbabilidad()>=mejor){
                piezaInicial = pieza;
                System.out.println("Proba: "+pieza.getProbabilidad());
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

       /* for(int i =1;i<piezas.size();i++){
            pActual = pSiguiente;
            
            
             if(!isPrimera(limite, i)){
                 ArrayList<Pieza> estes  = pActual.getDict().get(este);
                 System.out.println("estesSize:"+estes.size());
                 while(cont<estes.size()&&estes.get(cont).isUsada()){
                     System.out.println("cont: "+cont);
                    cont++;
                 }
                 System.out.println("xd");
                 pSiguiente = pActual.getDict().get(este).get(cont);
                 pSiguiente.setUsada(true);
                 piezasSolucion.add(pSiguiente);
                 cont = 0;
                
             }
             
        }
        System.out.println(piezasSolucion);*/
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
