package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    public void testMono1() {
        Mono<?> ms = Mono.just("Dniwe str")
                .then(Mono.error(new RuntimeException("mono exc occurred")))
                .log();
        ms.subscribe(System.out::println, (e)-> System.out.println(e.getMessage()));



    }

    @Test
    public void testFlux1() {
        Flux<String> stringFlux = Flux.just("Java", "Techie", "Newbie", "Guf")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("Flux exc occurred")))
                .concatWithValues("AWS_dno")
                .log();

        stringFlux.subscribe(System.out::println, (e)-> System.out.println(e.getMessage()));
    }
}
