package com.example.oauth2.controller;


import com.example.oauth2.service.impl.SysAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (SysAdmin)表控制层
 *
 * @author makejava
 * @since 2020-05-11 17:15:00
 */
@RestController
@RequestMapping("/api")
public class SysAdminController {
    /**
     * 服务对象
     */
    @Autowired
    private SysAdminServiceImpl sysAdminService;


    @GetMapping("/hello")
    public String hello(){
        return  "hello";
    }

   @GetMapping("/index")
    public String index(){
       return  "index";
   }
//   @GetMapping("/index")
//    public String index(){
//       return  "index";
//   }
//
//    @GetMapping("/index")
//    public String index() {
//        return "index";
//    }

}