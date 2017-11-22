/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.model;

/**
 *
 * @author numan
 */
public class Kelime {
    int id,uniteNo,kelimeTipi=1;
    String soru,cevap;

    public int getKelimeTipi() {
        return kelimeTipi;
    }

    public void setKelimeTipi(int kelimeTipi) {
        this.kelimeTipi = kelimeTipi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUniteNo() {
        return uniteNo;
    }

    public void setUniteNo(int uniteNo) {
        this.uniteNo = uniteNo;
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
    public static Kelime tersecevir (Kelime k)
{
    
    Kelime ters = new Kelime();
    ters.setId(k.getId());
    ters.setKelimeTipi(0);
    ters.setSoru(k.getCevap());
    ters.setCevap(k.getSoru());
    ters.setUniteNo(k.getUniteNo());
    return ters;
}
}
