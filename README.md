spring-boot-async-completablefuture
===================================

This sample enables Spring MVC to handle JDK8 CompletableFuture as a result returned by a controller:

```
  @RequestMapping(value = "/async", method = RequestMethod.GET)
  public CompletableFuture<ModelAndView> indexAsync() {
    CompletableFuture<ModelAndView> futureModelAndView = new CompletableFuture<>();

    ... async processing ...

    return futureModelAndView;
  }
```

*It is always astonishing how extensible Spring is.*

References:
http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html

http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/context/request/async/DeferredResult.html

http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodReturnValueHandler.html
