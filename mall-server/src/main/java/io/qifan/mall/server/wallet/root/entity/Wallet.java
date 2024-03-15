package io.qifan.mall.server.wallet.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.user.entity.User;
import jakarta.validation.constraints.NotNull;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.OneToOne;

import javax.validation.constraints.Null;
import java.math.BigDecimal;

/**
 * Entity for table "wallet"
 */
@Entity
@GenEntity
public interface Wallet extends BaseEntity {

    /**
     * 余额
     */
    @NotNull
    @GenField(value = "余额")
    BigDecimal balance();

    /**
     * 钱包密码
     */
    @Null
    @GenField(value = "密码")
    String password();

    @IdView
    String userId();

    @OneToOne
    @Key
    User user();
}

