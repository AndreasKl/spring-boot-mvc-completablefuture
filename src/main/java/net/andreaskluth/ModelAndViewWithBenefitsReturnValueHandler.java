package net.andreaskluth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;

public class ModelAndViewWithBenefitsReturnValueHandler extends ModelAndViewMethodReturnValueHandler {

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest) throws Exception {

    if (returnValue == null) {
      mavContainer.setRequestHandled(true);
      return;
    }

    HttpServletResponse nativeResponse = webRequest.getNativeResponse(HttpServletResponse.class);
    HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
    ((ModelAndViewWithBenefits) returnValue).execute(new WebContext(nativeRequest, nativeResponse));

    super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
  }

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return ModelAndViewWithBenefits.class.isAssignableFrom(returnType.getParameterType());
  }

}
