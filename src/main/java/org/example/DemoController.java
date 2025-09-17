package org.example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DemoController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from " + Thread.currentThread();
    }

    // Szimuláljunk blokkoló munkát (I/O/DB helyett alvás)
    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(2000); // 2s mesterséges késleltetés
        System.out.println("Slow done on " + Thread.currentThread());
        return "Slow done on " + Thread.currentThread();
    }
}
