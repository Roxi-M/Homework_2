package com.roxi.homework.level1.controller;


import com.roxi.homework.level1.annotation.Cache;
import com.roxi.homework.level1.annotation.Cut;
import com.roxi.homework.level1.bean.User;
import org.springframework.web.bind.annotation.*;

import javax.management.timer.Timer;
import javax.servlet.http.HttpSession;

@RestController
public class demo {
    final String ADMIN="admin";
    final String USER="user";
    final String PASSWORD="password";
    @GetMapping("/login")
    public  String login(String name, String password, HttpSession session){
        if((ADMIN.equals(name) || (USER.equals(name))) && (PASSWORD.equals(password))){
            User user=new User();
            user.setName(name);
            user.setPassword(password);
            session.setAttribute("user",user);
            return "登陆成功";
        }
        else {
            return "登陆失败";
        }
    }
    @Cut
    @GetMapping("/admin/update")
    public String update(HttpSession session){
        return "已修改";
    }


    @Cache(expire = 5)
    @GetMapping("/search")
    public String search(){
        System.out.println("为什么没有切成功");
        //自己忘给Cache 加 组件了 组件这个东西呢 怎么说呢 预加载类吧
        try{
            Thread.sleep(2* Timer.ONE_SECOND);
        } catch (InterruptedException e) {
          System.out.println(e.getMessage());
          return "你错啦，哈哈哈哈哈哈";
        }
        return "search successfully";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        session.invalidate();
        return "退出登陆";
    }
}
