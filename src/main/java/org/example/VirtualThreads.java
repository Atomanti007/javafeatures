package org.example;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreads {

    private static final String url = "http://localhost:8080/java";


//    public static void main(String[] args) throws Exception {
//        var client = HttpClient.newBuilder()
//                .connectTimeout(Duration.ofSeconds(5))
//                .build();
//
//        String url = "http://localhost:8080/java";
//        long t0 = System.nanoTime();
//
//        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
//            // 200 kérés, mind ugyanarra az URL-re
//            var futures = java.util.stream.IntStream.range(0, 200)
//                    .mapToObj(i -> exec.submit(() -> fetch(client, url)))
//                    .toList();
//
//            // Megvárjuk az összeset
//            for (Future<String> f : futures) {
//                System.out.println(f.get());
//            }
//        }
//
//        long ms = (System.nanoTime() - t0) / 1_000_000;
//        System.out.println("Kész (virtual threads), idő: " + ms + " ms");
//    }
//
//    static String fetch(HttpClient client, String url) {
//        try {
//            var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
//            var resp = client.send(req, HttpResponse.BodyHandlers.discarding());
//            return "%s -> %d".formatted(url, resp.statusCode());
//        } catch (Exception e) {
//            return "%s -> hiba: %s".formatted(url, e.getMessage());
//        }
//    }






//    public static void main(String[] args) throws Exception {
//        var client = HttpClient.newBuilder()
//                .connectTimeout(Duration.ofSeconds(5))
//                .build();
//
//        long t0 = System.nanoTime();
//
//        for (int i = 0; i < 200; i++) {
//            String result = fetch(client, url);
//            System.out.println(result);
//        }
//
//        long ms = (System.nanoTime() - t0) / 1_000_000;
//        System.out.println("Kész (szinkron), idő: " + ms + " ms");
//    }
//
//    static String fetch(HttpClient client, String url) {
//        try {
//            var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
//            var resp = client.send(req, HttpResponse.BodyHandlers.discarding());
//            return "%s -> %d".formatted(url, resp.statusCode());
//        } catch (Exception e) {
//            return "%s -> hiba: %s".formatted(url, e.getMessage());
//        }
//    }





    public static void main(String[] args) throws InterruptedException {
        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        String url = "http://localhost:8080/java";

        long t0 = System.nanoTime();

        List<CompletableFuture<String>> futures =
                java.util.stream.IntStream.range(0, 200)
                        .mapToObj(i -> fetchAsync(client, url))
                        .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        futures.forEach(f -> System.out.println(f.join()));

        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Kész (async), idő: " + ms + " ms");


        System.out.println("Process fut, Ctrl+C-vel állítható le");
        Thread.currentThread().join(); // főszálat sosem engedi befejeződni
    }

    static CompletableFuture<String> fetchAsync(HttpClient client, String url) {
        var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return client.sendAsync(req, HttpResponse.BodyHandlers.discarding())
                .<String>handle((resp, err) -> {
                    if (err != null) {
                        return url + " -> hiba: " + err.getMessage();
                    }
                    return "%s -> %d".formatted(url, resp.statusCode());
                });
    }

}
