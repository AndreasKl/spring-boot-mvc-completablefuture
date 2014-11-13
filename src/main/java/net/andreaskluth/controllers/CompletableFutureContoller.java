package net.andreaskluth.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompletableFutureContoller {

  private static final Log LOG = LogFactory.getLog(CompletableFutureContoller.class);
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("index", "message", "bye bye sync world");
  }
  
  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  public ModelAndView sync() {
    return new ModelAndView("brokentemplate", "message", "bye bye sync world");
  }

  @RequestMapping(value = "/async", method = RequestMethod.GET)
  public CompletableFuture<ModelAndView> indexAsync() {

    LOG.info("Servlet Thread Id = '" + Thread.currentThread().getName() + "'.");

    CompletableFuture<ModelAndView> runAsync = CompletableFuture.supplyAsync(() -> {
      LOG.info("Backend Thread Id = '" + Thread.currentThread().getName() + "'.");
      return new ModelAndView("brokentemplate", "message", "hello async world");
    }, ForkJoinPool.commonPool());

    return runAsync;
  }

}
