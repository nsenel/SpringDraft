package com.subtitle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import com.subtitle.model.Login;
import com.subtitle.model.User;
import com.subtitle.mapers.UserMapper;
public class UserDaoImpl implements UserDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;

  public void register(User user) {

    String sql = "insert into users values(?,?,?,?)";

    jdbcTemplate.update(sql, new Object[] { user.getId(),user.getEmail(),user.getSifre(),user.getkAdi()});
  }

  public User validateUser(Login login) {

    String sql = "select * from users where email='" + login.getUsername() + "' and sifre='" + login.getPassword()
        + "'";

    List<User> users = jdbcTemplate.query(sql, new UserMapper());

    return users.size() > 0 ? users.get(0) : null;
  }

}

