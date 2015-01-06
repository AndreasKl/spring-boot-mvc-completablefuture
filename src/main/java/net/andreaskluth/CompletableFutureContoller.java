package net.andreaskluth;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompletableFutureContoller {

  private static final Log LOG = LogFactory.getLog(CompletableFutureContoller.class);
  private SomeService someService;

  @Autowired
  public CompletableFutureContoller(SomeService someService) {
    this.someService = someService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("index", "message", "bye bye sync world");
  }

  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  public ModelAndView sync() {
    return new ModelAndView("message", "message", "bye bye sync world");
  }

  @RequestMapping(value = "/async", method = RequestMethod.GET)
  public CompletableFuture<ModelAndView> indexAsync() {
    LOG.info("Servlet Thread Id = '" + Thread.currentThread().getName() + "'.");
    
    return someService.getMessage()
      .thenApply(msg -> new ModelAndView("message", "message", msg));
  }

}
