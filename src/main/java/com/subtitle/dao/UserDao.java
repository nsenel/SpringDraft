package com.subtitle.dao;

import com.subtitle.model.Login;
import com.subtitle.model.User;
public interface UserDao {
    
  User validateUser(Login login);
}
