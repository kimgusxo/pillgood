package com.kit.pillgood.pillgood;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = {"com.kit.pillgood.service: com.kit.pillgood.service.GroupMemberService"})
class PillgoodApplicationTests {


    @Test
    void contextLoads() {
    }

}
