package net.andreaskluth;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompletableFutureContoller {

  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("index", "message", "bye bye sync world");
  }

  @RequestMapping(value = "/async", method = RequestMethod.GET)
  public CompletableFuture<ModelAndView> indexAsync() {
    CompletableFuture<ModelAndView> futureModelAndView = new CompletableFuture<>();

    // It's already completed however it is about getting the pipeline to work
    // with CompletableFuture.
    futureModelAndView.complete(new ModelAndView("index", "message", "hello async world"));

    return futureModelAndView;
  }

}
