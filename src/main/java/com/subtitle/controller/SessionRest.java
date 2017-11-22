/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.controller;

import com.subtitle.dao.KelimeDao;
import com.subtitle.model.Kelime;
import com.subtitle.model.KelimeTekrar;
import com.subtitle.model.Soru;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;

/**
 *
 * @author numan
 */
@RestController
@EnableWebMvc
public class SessionRest {
    @Autowired
    KelimeDao kelimeDao;
    
    @RequestMapping(value="sesion/startStudy",method = RequestMethod.POST )
    public String sessionPrepare(@RequestParam("unite") int uniteNo,HttpSession session){
        //List<Kelime> tKelimeler = (List<Kelime>) session.getAttribute("tKelimeler");
        //List<Kelime> siklarList = (List<Kelime>) session.getAttribute("siklarList");
        //List<String> ids = (List<String>) session.getAttribute("ids");
        //List<Kelime> sorulacakKelimeler = (List<Kelime>) session.getAttribute("sorulacakKelimeler");
        //tKelimeler.clear();
        //siklarList.clear();
        //ids.clear();
        //sorulacakKelimeler.clear();
        ArrayList<Kelime> sorulacakKelimeler =  new ArrayList<Kelime>();
        ArrayList<String> ids = new ArrayList<>();
        List<Kelime> tKelimeler=kelimeDao.findByUniteNo(uniteNo);
        List<Kelime> siklarList=kelimeDao.findByMultibleUniteNo(uniteNo, uniteNo+1, uniteNo+2);
        session.setAttribute("tKelimeler", tKelimeler);//unitenin kelimelerini sesiona ekliyor
        session.setAttribute("siklarList", siklarList);//unitenin kelimelerini sesiona ekliyor
        session.setAttribute("sorulacakKelimeler",sorulacakKelimeler);
        session.setAttribute("ids",ids);
        return ""+tKelimeler.size()+" "+siklarList.size();
    }
    @RequestMapping(value="session/getWord",method = RequestMethod.POST )
    public Kelime getWord(HttpSession session){
        Kelime kelime = new Kelime();
        List<Kelime> tKelimeler = (List<Kelime>) session.getAttribute("tKelimeler");
        List<Kelime> sorulacakKelimeler = (List<Kelime>) session.getAttribute("sorulacakKelimeler");
        List<String> ids = (List<String>) session.getAttribute("ids");
        System.out.println("tKelime size:-----"+tKelimeler.size());
        if (tKelimeler.size()>0){ 
            kelime=tKelimeler.get(0);
            sorulacakKelimeler.add(kelime);
            sorulacakKelimeler.add(Kelime.tersecevir(kelime));
            tKelimeler.remove(0);
            ids.add(kelime.getId()+"");
        }
        System.out.println("Kelime ----------->"+kelime.getSoru());
        System.out.println("Sorulacaklar Size ----------->"+sorulacakKelimeler.size());
        return kelime;
    }
    @RequestMapping(value="session/getQuestion",method = RequestMethod.POST )
    public Soru getQuestion(HttpSession session){
        Kelime kelime = new Kelime();
        Soru soru = new Soru();
        List<Kelime> sorulacakKelimeler = (List<Kelime>) session.getAttribute("sorulacakKelimeler");
        List<Kelime> siklarList = (List<Kelime>) session.getAttribute("siklarList");
        int r = (int)(Math.random()*(sorulacakKelimeler.size())-1);
        if(sorulacakKelimeler.size()>0) {
            kelime=sorulacakKelimeler.get(r);
            soru.setKelime(kelime);
        }
        sorulacakKelimeler.remove(kelime);
        /*List<Kelime> siklarList=kelimeDao.findByMultibleUniteNo(1, 1+1, 1+2);
        kelime = kelimeDao.findByKelimeId(1);
        soru.setKelime(kelime);*/
        ArrayList<String> siklar = new ArrayList<>();
        for (int i = 0; i < 3; i++) { //sik olusturur
            
            int random=0;
            while (true) {                    
                    random = (int)(Math.random()*(siklarList.size())-1);
                    if (random !=kelime.getId()) break;
                }
            
            if (kelime.getKelimeTipi()==1){
                siklar.add(siklarList.get(random).getCevap());
            }
            else{
                siklar.add(siklarList.get(random).getSoru());          
            }
        }
        siklar.add(kelime.getCevap());//sorunun dogru cevabi
        Collections.shuffle(siklar);//secenekleri karistir
        soru.setSiklar(siklar);
        
        return soru;
    }
    @RequestMapping(value="session/saveSesion",method = RequestMethod.POST )
    public String saveSession(HttpSession session,@RequestParam("snc") String sonuc){
         JSONObject snc = (JSONObject) JSONValue.parse(sonuc);
         List<String> ids = (List<String>) session.getAttribute("ids");
         String g="";
         for (int i = 0 ; i<ids.size() ; i++)
         {
             g+="\n"+snc.get(ids.get(i));
         }
         kelimeDao.ogrenmeKaydet(snc, ids,(int)session.getAttribute("id"));
         session.removeAttribute("tKelimeler");//session attribureleri sil
         session.removeAttribute("siklarList");
         session.removeAttribute("sorulacakKelimeler");
         session.removeAttribute("ids");
         return g;
    }
    @RequestMapping(value="session/repeat",method = RequestMethod.POST)
    public List<KelimeTekrar> getRepeatAbleWords(HttpSession session){
        ArrayList<KelimeTekrar> sorulanKelimeler =  new ArrayList<KelimeTekrar>();
        List<KelimeTekrar> tKelimeler = kelimeDao.findRepeatableWords((int) session.getAttribute("id"));
        List<Kelime> siklarList=kelimeDao.findByMultibleUniteNo(1, 1+1, 1+2);
        session.setAttribute("tKelimeler", tKelimeler);//tekrar edilecek kelimeleri sesiona ekliyor
        session.setAttribute("siklarList", siklarList);
        session.setAttribute("sorulanKelimeler", sorulanKelimeler);
        return tKelimeler;
    }
    @RequestMapping(value="session/getRepeatQuestion",method = RequestMethod.POST )
    public Soru getRepeatQuestion(HttpSession session){
        KelimeTekrar kelime = new KelimeTekrar();
        Soru soru = new Soru();
        List<KelimeTekrar> sorulacakKelimeler = (List<KelimeTekrar>) session.getAttribute("tKelimeler");
        List<KelimeTekrar> sorulanKelimeler = (List<KelimeTekrar>) session.getAttribute("sorulanKelimeler");
        List<Kelime> siklarList = (List<Kelime>) session.getAttribute("siklarList");
        int r = (int)(Math.random()*(sorulacakKelimeler.size())-1);
        if(sorulacakKelimeler.size()>0) {
            kelime=sorulacakKelimeler.get(r);
            soru.setKelimeTekrar(kelime);
        }
        sorulacakKelimeler.remove(kelime);
        sorulanKelimeler.add(kelime);
        
        ArrayList<String> siklar = new ArrayList<>();
        for (int i = 0; i < 3; i++) { //sik olusturur
            
            int random=0;
            while (true) {                    
                    random = (int)(Math.random()*(siklarList.size())-1);
                    if (random !=kelime.getId()) break;
                }
            
            if (kelime.getKelimeTipi()==1){
                siklar.add(siklarList.get(random).getCevap());
            }
            else{
                siklar.add(siklarList.get(random).getSoru());          
            }
        }
        siklar.add(kelime.getCevap());//sorunun dogru cevabi
        Collections.shuffle(siklar);//secenekleri karistir
        soru.setSiklar(siklar);
        
        return soru;
    }
    @RequestMapping(value="session/saveRepeatedSession",method = RequestMethod.POST )
    public String saveRepeatedSession(HttpSession session,@RequestParam("snc") String sonuc){
         JSONObject snc = (JSONObject) JSONValue.parse(sonuc);
         List<KelimeTekrar> sorulanKelimeler = (List<KelimeTekrar>) session.getAttribute("sorulanKelimeler");
         String g="";
         for (int i = 0 ; i<sorulanKelimeler.size() ; i++)
         {
             g+="\n"+snc.get(sorulanKelimeler.get(i).getId());
         }
         System.out.println("g ---------------- : "+ g);
         kelimeDao.tekrarKaydet(snc, sorulanKelimeler);
         return g;
    }
}
