package io.qifan.mall.server.inviter.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;

import javax.validation.constraints.Null;

/**
 * Entity for table "inviter"
 */
@GenEntity
@Entity
public interface Inviter extends BaseEntity {
    @GenField(value = "邀请码", order = 0)
    String code();

    @Null
    @GenField(value = "邀请二维码", order = 1)
    String qrCode();
}

