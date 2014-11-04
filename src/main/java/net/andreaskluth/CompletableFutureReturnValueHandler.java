package net.andreaskluth;

import java.util.concurrent.CompletableFuture;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CompletableFutureReturnValueHandler implements HandlerMethodReturnValueHandler {

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return CompletableFuture.class.isAssignableFrom(returnType.getParameterType());
  }

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest) throws Exception {
    if (returnValue == null) {
      mavContainer.setRequestHandled(true);
      return;
    }

    final DeferredResult<Object> deferredResult = new DeferredResult<>();

    @SuppressWarnings("unchecked")
    CompletableFuture<? extends Object> futureValue = (CompletableFuture<? extends Object>) returnValue;
    futureValue.whenComplete((result, ex) -> {
      if (ex != null) {
        deferredResult.setErrorResult(ex);
      } else {
        deferredResult.setResult(result);
      }
    });

    startDeferredResultProcessing(mavContainer, webRequest, deferredResult);
  }

  /**
   * Added to enable partial mocking and unit testing.
   */
  void startDeferredResultProcessing(ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
      final DeferredResult<Object> deferredResult) throws Exception {
    WebAsyncUtils.getAsyncManager(webRequest).startDeferredResultProcessing(deferredResult, mavContainer);
  }

}
