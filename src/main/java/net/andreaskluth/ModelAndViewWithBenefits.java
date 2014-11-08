package net.andreaskluth;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.web.servlet.ModelAndView;

public class ModelAndViewWithBenefits extends ModelAndView {
  
  private final Iterator<Consumer<? super WebContext>> actions;

  public ModelAndViewWithBenefits(String view, String model, Object data) {
    super(view, model, data);
    this.actions = Collections.emptyIterator();
  }

  public ModelAndViewWithBenefits(String view, String model, Object data, Iterator<Consumer<? super WebContext>> actions) {
    super(view, model, data);
    this.actions = Objects.requireNonNull(actions);
  }

  public void execute(WebContext context) {
    actions.forEachRemaining(r -> r.accept(context));
  }
  
}