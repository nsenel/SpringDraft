/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.model;

import java.sql.Timestamp;

/**
 *
 * @author numan
 */
public class KelimeTekrar {
    public int id,kelimeTipi=1;
    public String soru,cevap;
    public double seviye,difficulty;
    public Timestamp Tekrar_zamani,Son_goruntulenme;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKelimeTipi() {
        return kelimeTipi;
    }

    public void setKelimeTipi(int kelimeTipi) {
        this.kelimeTipi = kelimeTipi;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }

    public double getSeviye() {
        return seviye;
    }

    public void setSeviye(double seviye) {
        this.seviye = seviye;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public Timestamp getTekrar_zamani() {
        return Tekrar_zamani;
    }

    public void setTekrar_zamani(Timestamp Tekrar_zamani) {
        this.Tekrar_zamani = Tekrar_zamani;
    }

    public Timestamp getSon_goruntulenme() {
        return Son_goruntulenme;
    }

    public void setSon_goruntulenme(Timestamp Son_goruntulenme) {
        this.Son_goruntulenme = Son_goruntulenme;
    }
    
}
