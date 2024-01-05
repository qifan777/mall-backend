package io.qifan.mall.server.inviter.history.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseDateTime;
import io.qifan.mall.server.infrastructure.jimmer.UUIDIdGenerator;
import io.qifan.mall.server.user.entity.User;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.ManyToOne;

import javax.validation.constraints.Null;

/**
 * Entity for table "invite_history"
 */
@GenEntity
@Entity
public interface InviteHistory extends BaseDateTime {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    String id();

    @ManyToOne
    @GenField(value = "邀请人", order = 0)
    User inviter();

    @Null
    @ManyToOne
    @GenField(value = "被邀请人", order = 1)
    User invitee();

    @GenField(value = "状态", order = 2)
    int status();
}

