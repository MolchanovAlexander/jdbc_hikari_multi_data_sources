package com.example.demo.lookup;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean1 {
    public void usePrototype() {
        System.out.println(" **************** from lookup singleton method usePrototype *****************");
        PrototypeBean1 prototypeBean = getPrototypeBean();
        prototypeBean.doWork();
    }

    @Lookup
    protected PrototypeBean1 getPrototypeBean() {
        // Spring автоматично замінить реалізацію цього методу
        return null;
    }
}
