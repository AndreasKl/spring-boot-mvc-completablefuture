package net.andreaskluth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

  @Override
  public String getErrorPath() {
    // TODO Auto-generated method stub
    return "/error";
  }

  @RequestMapping("/error")
  public ModelAndView error() {
    System.out.println("Und alle so yeah.");
    return new ModelAndView("error");
  }

}
