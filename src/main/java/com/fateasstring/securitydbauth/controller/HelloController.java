package com.fateasstring.securitydbauth.controller;

        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello security!!";
    }

    @GetMapping("/dba/hello")
    public String dba(){
        return "hello dba!!";
    }

    @GetMapping("/admin/hello")
    public String admin(){
        return "hello admin!!";
    }

    @GetMapping("/user/hello")
    public String user(){
        return "hello user!!";
    }

}