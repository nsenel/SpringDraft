package com.subtitle.mapers;

/**
 *
 * @author numan
 */


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.subtitle.model.KelimeTekrar;
public class KelimeTekrarMapper implements RowMapper {//kulanilmiyor!!!!!!!!!!!!!!

  public KelimeTekrar mapRow(ResultSet rs, int arg1) throws SQLException {
    KelimeTekrar kelime = new KelimeTekrar();
    kelime.setId(rs.getInt("id"));
    kelime.setSoru(rs.getString("soru"));
    kelime.setCevap(rs.getString("cevap"));
    kelime.setDifficulty(rs.getDouble("difficulty"));
    kelime.setSeviye(rs.getDouble("seviye"));
    kelime.setTekrar_zamani(rs.getTimestamp("tekrar_zamani"));
    kelime.setSon_goruntulenme(rs.getTimestamp("son_goruntuleme"));

    return kelime;
  }
}
