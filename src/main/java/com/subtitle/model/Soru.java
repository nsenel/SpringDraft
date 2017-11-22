/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.model;

import java.util.ArrayList;

/**
 *
 * @author numan
 */

public class Soru {
    Kelime kelime;
    KelimeTekrar kelimeTekrar;
    ArrayList<String> siklar = new ArrayList<>();

    public KelimeTekrar getKelimeTekrar() {
        return kelimeTekrar;
    }

    public void setKelimeTekrar(KelimeTekrar kelimeTekrar) {
        this.kelimeTekrar = kelimeTekrar;
    }
    
    public Kelime getKelime() {
        return kelime;
    }

    public void setKelime(Kelime kelime) {
        this.kelime = kelime;
    }

    public ArrayList<String> getSiklar() {
        return siklar;
    }

    public void setSiklar(ArrayList<String> siklar) {
        this.siklar = siklar;
    }
    
}
