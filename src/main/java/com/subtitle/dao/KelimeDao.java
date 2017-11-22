/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.dao;

/**
 *
 * @author numan
 */
import com.subtitle.model.Kelime;
import com.subtitle.model.KelimeKaydet;
import com.subtitle.model.KelimeTekrar;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

public interface KelimeDao {
    
    public Kelime findByKelimeId(int kelimeId);//tek kelime al
    public List<Kelime> findByUniteNo(int uniteNo);//unite kelimelerini al
    public List<Kelime> findByMultibleUniteNo(int uniteNo0, int uniteNo2, int uniteNo3);//3 unitenin kelimelerini al sik uretmek icin
    public int findAllUnites();// database deki toplam unite sayisi
    public int findAllRepeatable(int userID); // tekrar edilecek kelime sayisini bul
    public List<KelimeTekrar> findRepeatableWords(int userID);//tekrarEdilecek kelimelerini al
    public void ogrenmeKaydet(JSONObject sonuclar, List<String> ids,int kId);//ilk kez ogrenilen bolume kaydet
    public void tekrarKaydet(JSONObject sonuclar, List<KelimeTekrar> tekrarlananlar);
    
}
