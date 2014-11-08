package net.andreaskluth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

  @Autowired
  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

  @Bean
  public HandlerMethodReturnValueHandler completableFutureReturnValueHandler() {
    return new CompletableFutureReturnValueHandler();
  }
  
  @Bean
  public HandlerMethodReturnValueHandler modelAndViewWithBenefitsReturnValueHandler() {
    return new ModelAndViewWithBenefitsReturnValueHandler();
  }

  @PostConstruct
  public void init() {
    final List<HandlerMethodReturnValueHandler> originalHandlers = new ArrayList<>(
        requestMappingHandlerAdapter.getReturnValueHandlers());
    
    final int deferredPos = obtainDeferredValueHandlerPosition(originalHandlers, DeferredResultMethodReturnValueHandler.class);
    // Add our handler directly after the deferred handler.
    originalHandlers.add(deferredPos + 1, completableFutureReturnValueHandler());
    
    final int modelAndViewPos = obtainDeferredValueHandlerPosition(originalHandlers, ModelAndViewMethodReturnValueHandler.class);
    // Add our handler directly before the deferred handler.
    originalHandlers.add(modelAndViewPos, modelAndViewWithBenefitsReturnValueHandler());
    
    requestMappingHandlerAdapter.setReturnValueHandlers(originalHandlers);
  }

  private int obtainDeferredValueHandlerPosition(final List<HandlerMethodReturnValueHandler> originalHandlers, Class<?> handlerClass) {
    for (int i = 0; i < originalHandlers.size(); i++) {
      final HandlerMethodReturnValueHandler valueHandler = originalHandlers.get(i);
      if (handlerClass.isAssignableFrom(valueHandler.getClass())) {
        return i;
      }
    }
    return -1;
  }
}