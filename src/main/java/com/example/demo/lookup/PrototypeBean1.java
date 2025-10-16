package com.example.demo.lookup;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean1 {
    public PrototypeBean1() {
        System.out.println("PrototypeBean via lookup created: " + this);
    }

    public void doWork() {
        System.out.println("Working with PrototypeBean: " + this);
    }
}

