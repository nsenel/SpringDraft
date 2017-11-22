
package com.subtitle.mapers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.subtitle.model.Kelime;
public class KelimeMapper implements RowMapper {

  public Kelime mapRow(ResultSet rs, int arg1) throws SQLException {
    Kelime kelime = new Kelime();
    kelime.setId(rs.getInt("id"));
    kelime.setUniteNo(rs.getInt("unite"));
    kelime.setSoru(rs.getString("soru"));
    kelime.setCevap(rs.getString("cevap"));

    return kelime;
  }
}
