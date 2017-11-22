/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subtitle.controller;


import com.subtitle.dao.KelimeDao;
import com.subtitle.dao.UserDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.subtitle.model.Login;
import com.subtitle.model.User;
import com.subtitle.model.Kelime;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;
        
@Controller
public class LoginController {

  @Autowired
  UserDao userDao;
  @Autowired
  KelimeDao kelimeDao;
    

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView getHome(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
      ModelAndView mav = new ModelAndView("../home");
      if (session.getAttribute("id") != null){
        int unite_sayisi = kelimeDao.findAllUnites();
        int tekrar_sayisi = kelimeDao.findAllRepeatable((int)session.getAttribute("id"));
        mav.addObject("unite_Sayisi", unite_sayisi);
        mav.addObject("tekrar_Sayisi", tekrar_sayisi);
        mav.addObject("firstname", session.getAttribute("kAdi"));
      }
      else{
          mav.addObject("login", new Login());
      }
      return mav;
  }
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
    session.invalidate();
    ModelAndView mav = new ModelAndView("../home");
    mav.addObject("login", new Login());
    return mav;
  }

  @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,HttpSession session,
      @ModelAttribute("login") Login login) {
    ModelAndView mav = null;

    User user = userDao.validateUser(login);

    if (null != user) {
        //ArrayList<Kelime> tKelimeler = new ArrayList<Kelime>();
        //ArrayList<Kelime> sorulacakKelimeler =  new ArrayList<Kelime>();
        //ArrayList<Kelime> siklarList =  new ArrayList<Kelime>();
        //ArrayList<String> ids = new ArrayList<>();
        //session.setAttribute("tKelimeler",tKelimeler);
        //session.setAttribute("sorulacakKelimeler",sorulacakKelimeler);
        //session.setAttribute("siklarList",siklarList);
        //session.setAttribute("ids",ids);
        session.setAttribute("id", user.getId());
        session.setAttribute("kAdi", user.getkAdi());
        int unite_sayisi = kelimeDao.findAllUnites();
        int tekrar_sayisi = kelimeDao.findAllRepeatable(user.getId());
        mav = new ModelAndView("../home");
        mav.addObject("unite_Sayisi", unite_sayisi);
        mav.addObject("tekrar_Sayisi", tekrar_sayisi);
        mav.addObject("firstname", user.getkAdi());
    } 
    else {
      mav = new ModelAndView("login");
      mav.addObject("message", "Username or Password is wrong!!");
    }

    return mav;
  }
  @RequestMapping(value = "/ogren", method = RequestMethod.GET)
  public ModelAndView getOgren(HttpServletRequest request,HttpServletRequest response,HttpSession session){
      ModelAndView mav = null;
      mav = new ModelAndView("welcome");
      Kelime kelime = kelimeDao.findByKelimeId(1);
      mav.addObject("firstname", kelime.getKelimeTipi());
      return mav;
  }
  @RequestMapping(value = "/uniteogren/{uniteNo}", method = RequestMethod.GET)
  public ModelAndView getuniteOgren(HttpServletRequest request,HttpServletRequest response,HttpSession session,
                                    @PathVariable("uniteNo") int uniteNo){
      ModelAndView mav = null;
      mav = new ModelAndView("ogren");
      /*
      List<Kelime> kelimeler = kelimeDao.findByUniteNo(uniteNo);
      String kelimelerim="";
      for (int i = 0; i < kelimeler.size(); i++) {
          kelimelerim+="\n "+kelimeler.get(i).getSoru();
      }*/
      mav.addObject("uniteNo", uniteNo);
      return mav;
  }
  @RequestMapping(value = "/tekrar",method=RequestMethod.GET)
  public ModelAndView getTekrar(HttpServletRequest request,HttpServletRequest response,HttpSession session){
      ModelAndView mav = new ModelAndView("tekrar");
      return mav;
  }
  

}
