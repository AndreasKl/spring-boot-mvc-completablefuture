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
    if (returnValue == null || !CompletableFuture.class.isAssignableFrom(returnValue.getClass())) {
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

    WebAsyncUtils.getAsyncManager(webRequest).startDeferredResultProcessing(deferredResult, mavContainer);
  }

}
