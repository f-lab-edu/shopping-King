package com.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MixedSyncBlockTest {

  @TempDir
  Path tempDir;

  @Test
  public void testSyncBlocking() throws IOException {
    System.out.println("testSyncBlocking started");

    Path tempFile = Files.createFile(tempDir.resolve("test.txt"));
    Files.write(tempFile, "Hello, World!".getBytes());

    String content = readFile(tempFile.toString());
    assertNotNull(content);
    assertEquals("Hello, World!", content);

    System.out.println("testSyncBlocking finished");
  }

  @Test
  public void testAsyncNonBlocking() throws ExecutionException, InterruptedException {
    System.out.println("테스트 시작");

    CompletableFuture<Integer> future1 = performAsyncCalculation(3);
    CompletableFuture<Integer> future2 = performAsyncCalculation(5);
    CompletableFuture<Integer> future3 = performAsyncCalculation(2);
    System.out.println("메인스레드 작동확인");
    CompletableFuture.allOf(future1, future2, future3).thenRun(() -> {
      try {
        System.out.println("Future 1 result: " + future1.get());
        System.out.println("Future 2 result: " + future2.get());
        System.out.println("Future 3 result: " + future3.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }).get();
    System.out.println("테스트 끝");
  }

  private String readFile(String path) throws IOException {
    System.out.println("파일 읽기: " + path);
    return new String(Files.readAllBytes(Path.of(path)));
  }

  private CompletableFuture<Integer> performAsyncCalculation(int input) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        System.out.println("작업 " + input + "초 시작 ");
        Thread.sleep(1000 * input);
        System.out.println("작업 " + input + "초 끝 ");
        return input * input;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return -1;
      }
    });
  }
}
