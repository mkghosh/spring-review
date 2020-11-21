package com.mithun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaseController {
    
    @RequestMapping(value={"/home" , "/"})
	public String showHome() {
		return "home";
	}
	
	@RequestMapping("/restricted")
	public String showRestricted() {
		return "/admin/restricted";
	}
	
	@RequestMapping("/admin")
	public String getAdmin() {
		return "admin";
	}
	
	@RequestMapping("/user")
	public String getUser() {
		return "user";
	}
	
	@RequestMapping("/about")
	public String getAbout() {
		return "about";
	}
    
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
    
    @GetMapping("/401")
    public String error401() {
        return "/error/401";
    }
      
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
