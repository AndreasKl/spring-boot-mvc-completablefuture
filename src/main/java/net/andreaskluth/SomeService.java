package net.andreaskluth;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.springframework.stereotype.Component;

@Component
public class SomeService {

  @AsyncTimed
  public CompletableFuture<String> getMessage() {
    return CompletableFuture.supplyAsync(() -> {
      heavyWork();
      return "hello async world";
    }, ForkJoinPool.commonPool());
  }

  private void heavyWork() {
    try {
      Thread.sleep(42);
    }
    catch (InterruptedException e) {
      Thread.interrupted();
    }
  }
}