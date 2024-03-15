package io.qifan.mall.server.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
public class WalletTest {
    @Autowired
    ApplicationEventPublisher eventPublisher;
}
