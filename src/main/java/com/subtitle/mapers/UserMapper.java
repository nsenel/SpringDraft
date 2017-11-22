/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.mapers;

import com.subtitle.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper {

  public User mapRow(ResultSet rs, int arg1) throws SQLException {
    User user = new User();
    user.setEmail(rs.getString("email"));
    user.setSifre(rs.getString("sifre"));
    user.setkAdi(rs.getString("kAdi"));
    user.setId(rs.getInt("id"));

    return user;
  }
}
