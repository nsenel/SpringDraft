
package com.subtitle.mapers;

/**
 *
 * @author numan
 */


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.subtitle.model.KelimeKaydet;
public class KelimeKaydetMapper implements RowMapper {//kulanilmiyor!!!!!!!!!!!!!!

  public KelimeKaydet mapRow(ResultSet rs, int arg1) throws SQLException {
    KelimeKaydet kelime = new KelimeKaydet();
    kelime.setKelime_id(rs.getInt("kelime_id"));
    kelime.setUser_id(rs.getInt("user_id"));
    kelime.setDifficulty(rs.getDouble("difficulty"));
    kelime.setSeviye(rs.getDouble("seviye"));
    kelime.setTekrar_zamani(rs.getTimestamp("tekrar_zamani"));
    kelime.setSon_goruntuleme(rs.getTimestamp("son_goruntuleme"));

    return kelime;
  }
}
