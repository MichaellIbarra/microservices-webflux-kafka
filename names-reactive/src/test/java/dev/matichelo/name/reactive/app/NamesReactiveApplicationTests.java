package dev.matichelo.name.reactive.app;

import dev.matichelo.name.reactive.app.controllers.NameRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class NamesReactiveApplicationTests {

    @Autowired
    private NameRest nameRest;

//    @Test
//    void contextLoads() {
//        StepVerifier.create(nameRest.getNames())
//                .expectSubscription()
//                .expectNext("one")
//                .expectNext("two")
//                .expectNext("three")
//                .expectNext("four")
//                .expectNextCount(1)
//                .verifyComplete();
//    }

}
