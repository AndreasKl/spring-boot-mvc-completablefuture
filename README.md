spring-boot-async-completablefuture
===================================

___
> Spring Boot 1.3 (Spring 4.2) supports ``CompletableFuture<T>`` and ``CompletionStage<T>`` as a ``Controller`` return type out of the box. There is no need to add a custom ``MethodReturnValueHandler`.
___

This sample enables Spring MVC to handle JDK8 CompletableFuture as a result returned by a controller:

```
  @RequestMapping(value = "/async", method = RequestMethod.GET)
  public CompletableFuture<ModelAndView> indexAsync() {
    CompletableFuture<ModelAndView> futureModelAndView = new CompletableFuture<>();

    ... async processing ...

    return futureModelAndView;
  }
```

**It is always astonishing how extensible Spring is.**

References:

*   http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html
*   http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/context/request/async/DeferredResult.html
*   http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodReturnValueHandler.html
*   http://www.nurkiewicz.com/2013/03/deferredresult-asynchronous-processing.html
