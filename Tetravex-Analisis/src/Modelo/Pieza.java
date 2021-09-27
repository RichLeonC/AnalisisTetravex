/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author richa
 */
public class Pieza {
   private Integer []posiciones = new Integer[4];
   private int numPieza;
   private HashMap<Integer, ArrayList<Pieza>> dictMatches;
   private int probabilidad;
   private boolean usada;
   private boolean usadaInicial;
   private boolean previamenteUsada;
   

    public Pieza() {
        dictMatches = new HashMap();
        dictMatches.put(0, new ArrayList()); // (0,1), (0,2), (0,3)
        dictMatches.put(1, new ArrayList());
        dictMatches.put(2, new ArrayList());
        dictMatches.put(3, new ArrayList());
        probabilidad = 0;
        usada = false;
        usadaInicial = false;
        
    }

    public Pieza(int norte,int este, int sur, int oeste, int numPieza) {
        posiciones[0]  = norte;
        posiciones[1] = este;
        posiciones[2] = sur;
        posiciones[3] = oeste;
        this.numPieza = numPieza;
    }

    public Pieza(int numPieza) {
        this.numPieza = numPieza;
    }
    
    

    public Integer[] getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(Integer[] posiciones) {
        this.posiciones = posiciones;
    }

    public int getNumPieza() {
        return numPieza;
    }

    public void setNumPieza(int numPieza) {
        this.numPieza = numPieza;
    }

    public HashMap<Integer,ArrayList<Pieza>> getDict() {
        return dictMatches;
    }

    public void setDict(HashMap<Integer, ArrayList<Pieza>> dict) {
        this.dictMatches = dict;
    }
    
    public int getNorte(){
        return posiciones[0];
    }
    public void setNorte(int norte){
        posiciones[0]  = norte;
    }
    public int getEste(){
        return posiciones[1];
    }
    
    public void setEste(int este){
        posiciones[1] = este;
    }
    
    public int getSur(){
        return posiciones[2];
    }
    public void setSur(int sur){
        posiciones[2] = sur;
    }
    
    public int getOeste(){
        return posiciones[3];
    }
    
    public void setOeste(int oeste){
        posiciones[3] = oeste;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public boolean isUsadaInicial() {
        return usadaInicial;
    }

    public void setUsadaInicial(boolean usadaInicial) {
        this.usadaInicial = usadaInicial;
    }

    public boolean isPreviamenteUsada() {
        return previamenteUsada;
    }

    public void setPreviamenteUsada(boolean previamenteUsada) {
        this.previamenteUsada = previamenteUsada;
    }
    
    

    @Override
    public String toString() {
        return "Pieza{" + "posiciones={" + getNorte() +","+getEste()+","+getSur()+","+getOeste()+"}"
                + ", numPieza=" + numPieza + '}'+"\n";
    }
        
    
    
    
   
   
    
}
