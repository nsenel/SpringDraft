
package com.subtitle.model;

import java.sql.Timestamp;

/**
 *
 * @author numan
 */
public class KelimeKaydet {//kulanilmiyor !!!!!!!!!!!!!!
    int kelime_id,user_id;
    Timestamp tekrar_zamani,son_goruntuleme;
    double seviye,difficulty;
    
    public int getKelime_id() {
        return kelime_id;
    }

    public void setKelime_id(int kelime_id) {
        this.kelime_id = kelime_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getTekrar_zamani() {
        return tekrar_zamani;
    }

    public void setTekrar_zamani(Timestamp tekrar_zamani) {
        this.tekrar_zamani = tekrar_zamani;
    }

    public Timestamp getSon_goruntuleme() {
        return son_goruntuleme;
    }

    public void setSon_goruntuleme(Timestamp son_goruntuleme) {
        this.son_goruntuleme = son_goruntuleme;
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
    
}
