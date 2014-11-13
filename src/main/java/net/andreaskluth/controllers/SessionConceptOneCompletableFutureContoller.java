package net.andreaskluth.controllers;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SessionConceptOneCompletableFutureContoller {

  private static final Log LOG = LogFactory.getLog(SessionConceptOneCompletableFutureContoller.class);

  private static final String SESSION_KEY = SessionConceptOneCompletableFutureContoller.class.getCanonicalName()
      + "_VALUE";

  @RequestMapping(value = "/asyncOne", method = RequestMethod.GET)
  public CompletableFuture<ModelAndView> indexAsync(HttpSession session) {
    Objects.requireNonNull(session);
    
    LOG.info("Servlet Thread Id = '" + Thread.currentThread().getName() + "'.");
    LOG.info("Stored in session = '" + session.getAttribute(SESSION_KEY) + "'.");

    CompletableFuture<ModelAndView> runAsync = CompletableFuture.supplyAsync(() -> {
      LOG.info("Backend Thread Id = '" + Thread.currentThread().getName() + "'.");
      session.setAttribute(SESSION_KEY, "Some value");
      return new ModelAndView("brokentemplate", "message", "hello async world");
    }, ForkJoinPool.commonPool());

    return runAsync;
  }

}
