package com.shopping;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SyncAsyncBlockingNonBlockingTest {

  @Test
  @DisplayName("Synchronous Tasks Test")
  public void testSync() {
    System.out.println("testSync started");
    performSyncTask("Task 1");
    performSyncTask("Task 2");
    System.out.println("testSync finished");
  }

  @Test
  @DisplayName("Asynchronous Tasks Test")
  public void testAsync() throws InterruptedException, ExecutionException {
    System.out.println("testAsync started");
    CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> performAsyncTask("Task 1"));
    CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> performAsyncTask("Task 2"));

    CompletableFuture.allOf(future1, future2).get(); // Wait for all tasks to complete
    System.out.println("testAsync finished");
  }

  @Test
  @DisplayName("Blocking Tasks Test")
  public void testBlocking() {
    System.out.println("testBlocking started");
    performBlockingTask("Task 1");
    performBlockingTask("Task 2");
    System.out.println("testBlocking finished");
  }

  @Test
  @DisplayName("Non-Blocking Tasks Test")
  public void testNonBlocking() throws InterruptedException, ExecutionException {
    System.out.println("testNonBlocking started");
    performNonBlockingTask("Task 1");
    performNonBlockingTask("Task 2");
    System.out.println("testNonBlocking finished");
  }

  private void performSyncTask(String taskName) {
    System.out.println(taskName + " started (Sync)");
    try {
      Thread.sleep(2000); // Simulate task taking time
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println(taskName + " finished (Sync)");
  }

  private void performAsyncTask(String taskName) {
    System.out.println(taskName + " started (Async)");
    try {
      Thread.sleep(2000); // Simulate async task taking time
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println(taskName + " finished (Async)");
  }

  private void performBlockingTask(String taskName) {
    System.out.println(taskName + " started (Blocking)");
    try {
      Thread.sleep(2000); // Simulate blocking task
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println(taskName + " finished (Blocking)");
  }

  private void performNonBlockingTask(String taskName) {
    System.out.println(taskName + " started (Non-Blocking)");
    new Thread(() -> {
      try {
        Thread.sleep(2000); // Simulate non-blocking task
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      System.out.println(taskName + " finished (Non-Blocking)");
    }).start();
    // Immediately return without waiting for the task to complete
  }
}
