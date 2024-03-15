package io.qifan.mall.server.wallet.record.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.dict.model.DictConstants.WalletRecordType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.wallet.root.entity.Wallet;
import jakarta.validation.constraints.NotNull;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;

import java.math.BigDecimal;

/**
 * Entity for table "wallet_record"
 */
@Entity
@GenEntity
public interface WalletRecord extends BaseEntity {

    /**
     * 钱包id
     */
    @IdView
    @GenField(value = "钱包")
    String walletId();

    @ManyToOne
    @NotNull
    Wallet wallet();

    /**
     * 金额
     */
    @NotNull
    BigDecimal amount();

    /**
     * 类型如：提现，充值，奖励，返佣等等
     */
    WalletRecordType type();

    /**
     * 描述信息
     */
    String description();
}

