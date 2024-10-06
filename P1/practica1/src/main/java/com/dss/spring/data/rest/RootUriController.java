package com.dss.spring.data.rest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class RootUriController {
	@GetMapping("/")
    public String index() {
        return "index"; // Thymeleaf buscará el archivo en templates/index.html
    }

    
    @GetMapping("/index.html")
    public String redirectToIndex() {
        return "index"; // Thymeleaf buscará el archivo en templates/index.html
    }
    
    
}
