package net.andreaskluth.controllers;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

import javax.servlet.http.HttpSession;

import net.andreaskluth.ModelAndViewWithBenefits;
import net.andreaskluth.WebContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

@Controller
public class SessionConceptTwoCompletableFutureContoller {

  private static final Log LOG = LogFactory.getLog(SessionConceptTwoCompletableFutureContoller.class);

  private static final String SESSION_KEY = SessionConceptTwoCompletableFutureContoller.class.getCanonicalName()
      + "_VALUE";

  @RequestMapping(value = "/asyncTwo", method = RequestMethod.GET)
  public CompletableFuture<ModelAndViewWithBenefits> indexAsync(HttpSession session) {
    Objects.requireNonNull(session);

    LOG.info("Servlet Thread Id = '" + Thread.currentThread().getName() + "'.");
    LOG.info("Stored in session = '" + session.getAttribute(SESSION_KEY) + "'.");

    CompletableFuture<ModelAndViewWithBenefits> runAsync = CompletableFuture.supplyAsync(() -> {
      LOG.info("Backend Thread Id = '" + Thread.currentThread().getName() + "'.");
      session.setAttribute(SESSION_KEY, "Some value");

      UnmodifiableIterator<Consumer<? super WebContext>> deferredActions = Iterators.singletonIterator((webcontext) -> {
        webcontext.addToSession(SESSION_KEY, 23);
      });

      return new ModelAndViewWithBenefits("index", "message", "hello async world", deferredActions);
    }, ForkJoinPool.commonPool());

    return runAsync;
  }

}
