
package com.subtitle.dao;

import com.subtitle.mapers.KelimeMapper;
import com.subtitle.mapers.KelimeTekrarMapper;
import com.subtitle.model.Kelime;
import com.subtitle.model.KelimeKaydet;
import com.subtitle.model.KelimeTekrar;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author numan
 */
public class KelimeDaoImpl implements KelimeDao{
  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
  
    public Kelime findByKelimeId(int kelimeId) {
        String sql = "select * from kelimeler where id= "+kelimeId;
        List<Kelime> kelimeler = jdbcTemplate.query(sql, new KelimeMapper());
        return kelimeler.size() > 0 ? kelimeler.get(0) : null;
    }

    @Override
    public List<Kelime> findByUniteNo(int uniteNo) {
        String sql = "select * from kelimeler where unite= "+uniteNo;
        List<Kelime> kelimeler = jdbcTemplate.query(sql, new KelimeMapper());
        return kelimeler;
        }
    @Override
    public List<Kelime> findByMultibleUniteNo(int uniteNo0, int uniteNo1, int uniteNo2) {
        String sql = "SELECT * from kelimeler where unite="+uniteNo0+" or unite="+uniteNo1+" or unite="+uniteNo2;
        List<Kelime> kelimeler = jdbcTemplate.query(sql, new KelimeMapper());
        return kelimeler;
        }

    @Override
    public int findAllRepeatable(int userID) {
        String sql="select count(*) as kelimeSayisi from ogrenilen_kelimeler where tekrar_zamani<now() and user_id= " + userID;
        int total = jdbcTemplate.queryForObject(sql, Integer.class);
        return total;
    }
    
    @Override
    public int findAllUnites() {
     String sql = "SELECT count( DISTINCT(unite) ) as uniteSayisi FROM kelimeler";
     int total = jdbcTemplate.queryForObject(sql, Integer.class);
     return total;
    }

    @Override
    public List<KelimeTekrar> findRepeatableWords(int userID) {
        String sql="select ogrenilen_kelimeler.id,kelimeler.id as kelimeID,soru,cevap,seviye,difficulty,tekrar_zamani,son_goruntuleme from ogrenilen_kelimeler,kelimeler \n" +
                        "where ogrenilen_kelimeler.kelime_id=kelimeler.id and user_id= "+userID+" and tekrar_zamani<now() limit 3";
        
        List<KelimeTekrar> kelimeler = jdbcTemplate.query(sql, new KelimeTekrarMapper());
        return kelimeler;
    }
    

    @Override
    public void ogrenmeKaydet(JSONObject sonuclar, List<String> ids, int kId) {// Ilk defa kaydedilecek kelimelerin degerlerini tespit edip liste olusturur.
        final ArrayList<KelimeKaydet> kaydedilecekeler = new ArrayList<>();
        Timestamp now = new Timestamp(new Date().getTime());
        for (int i = 0; i < ids.size(); i++) {
            KelimeKaydet kelime = new KelimeKaydet();
            Timestamp later = new Timestamp(now.getTime() + (1000 * 60 * 60 * 24));
            double performanceRating = 0.6;
            String kelimeID = ids.get(i);
            JSONArray cvplar = (JSONArray)sonuclar.get(ids.get(i)); //kelime arrayi [0,0] yada [1,0] gibi
            System.out.println("Bana gelen cvplar : " + cvplar);
            for (int j = 0; j < cvplar.size(); j++) {
                if(cvplar.get(j).equals("0"))
                {
                    later = new Timestamp(now.getTime() + (1000 * 60 * 60 * 6));
                    performanceRating = 0.5;
                    break;
                }
            }
            kelime.setDifficulty(0.3);
            kelime.setKelime_id(Integer.parseInt(kelimeID));
            kelime.setUser_id(kId);
            kelime.setTekrar_zamani(later);
            kelime.setSeviye(performanceRating);
            kelime.setSon_goruntuleme(now);
            kaydedilecekeler.add(kelime);
        }
        InsertBatnch(kaydedilecekeler);//Kelimeleri kaydetmesi icin listeyi methoda yollar sql'le kaydetmek icin.
    }
    public void InsertBatnch (final List<KelimeKaydet> kelimeler){
        String sql="INSERT INTO ogrenilen_kelimeler (kelime_id, user_id, tekrar_zamani, seviye, difficulty, son_goruntuleme) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				KelimeKaydet kelime = kelimeler.get(i);
				ps.setInt(1, kelime.getKelime_id());
				ps.setInt(2, kelime.getUser_id());
				ps.setTimestamp(3, kelime.getTekrar_zamani() );
                                ps.setDouble(4, kelime.getSeviye());
				ps.setDouble(5, kelime.getDifficulty());
				ps.setTimestamp(6, kelime.getSon_goruntuleme());
			}
			
			@Override
			public int getBatchSize() {
				return kelimeler.size();
			}
		});
    }

    @Override
    public void tekrarKaydet(JSONObject sonuclar, List<KelimeTekrar> tekrarlananlar) {
        Timestamp now = new Timestamp(new Date().getTime());
        System.out.println("Tekrarlanan Sayisi : " + tekrarlananlar.size());
        for (int i = 0; i < tekrarlananlar.size()-1; i++) {
            String sql="UPDATE ogrenilen_kelimeler\n" +
                        "SET tekrar_zamani = '%s', seviye = %s, difficulty = %s , son_goruntuleme=now() \n" +
                        "WHERE id= %s;";
            int kelimeID = tekrarlananlar.get(i).getId();
            System.out.println("KElime id : " + kelimeID);
            double seviye = tekrarlananlar.get(i).getSeviye();
            boolean cvp = true;
            seviye = seviye + 0.1;
            seviye = Math.min(1, seviye);
            JSONArray cvplar = (JSONArray)sonuclar.get(kelimeID+"");
            System.out.println("cvplar : " + cvplar.toJSONString());
            //System.out.println("Sonuc : "+ sonuclar.get(kelimeID+"")+"icerik"+cvplar.get(0));
            
            if ((cvplar.get(0)).equals("0")){
                cvp=false;
                seviye=seviye-0.2;
                Math.max(0.1, seviye);
            }
            
            double gecikmeZamani = gecikmeZamani(tekrarlananlar.get(i).getSon_goruntulenme(), tekrarlananlar.get(i).getTekrar_zamani(), now, cvp);
            double difficulty = zorlulukOrani(seviye, tekrarlananlar.get(i).getDifficulty(), gecikmeZamani);
            double difficultyWeight = 3-1.7*difficulty;
            System.out.println("difficulty : "+ difficulty);
            
            Timestamp tekrar_zamani = tekrarEdililecekTarih(difficultyWeight, cvp, now, tekrarlananlar.get(i).getTekrar_zamani(),tekrarlananlar.get(i).getSon_goruntulenme(), gecikmeZamani);
            //System.out.println("gelecek Tekrar : " + tekrar_zamani);
            sql=String.format(sql,tekrar_zamani,seviye,difficulty,kelimeID);
            System.out.println("Kayit : " + sql );
            jdbcTemplate.update(sql);
        }
    }
    
    
    
    //Tekrar kaydetmede kullanilan degiskenleri belirleyen methodlar
    public static double gecikmeZamani (Timestamp sonGoruntuleme , Timestamp tekrarZamani,Timestamp now , boolean cvp)
    {
        if (cvp){
            double x = (now.getTime()-sonGoruntuleme.getTime())/(tekrarZamani.getTime()-sonGoruntuleme.getTime()*1.0) ;
            System.out.println("Gecikme zamani  : "  + x);
            return Math.min(2, x);
        }
        return 1;
    }
    
    public static Timestamp tekrarEdililecekTarih (double difficultyWeight,boolean cvp,Timestamp now,Timestamp tekrarZamani , Timestamp sonGoruntuleme,double gecikmeZamani)
    {
        double x = 0; // bugunden itibaren kac gun sonra bu kelime tekrar edilecek ornek x=2,6 2 gun 6 saat gibi
        if (cvp){
            x =(tekrarZamani.getTime()-sonGoruntuleme.getTime()*1.0)*(1+(difficultyWeight-1)*gecikmeZamani);
            System.out.println("Gecikme zamani  : "  + x);
        }
        else {
            x = (1/Math.pow(difficultyWeight, 2))*(tekrarZamani.getTime()-sonGoruntuleme.getTime()*1.0);
            System.out.println("Gecikme zamani  : "  + x);
        }
        Timestamp gelecek_Tekrar = new Timestamp((long)(now.getTime()+x));
        System.out.println("gelecek_Tekrar :"+gelecek_Tekrar);
        return gelecek_Tekrar;
    }
    
    
    public static double zorlulukOrani (double seviye , double difficulty , double gecikmeZamani) 
    {
        return difficulty+(gecikmeZamani*0.05882*(8-9*seviye));//0 ila 1 arasinda olacak
    }
    //Degisken metodlarin sonu 
    
    
    
}
