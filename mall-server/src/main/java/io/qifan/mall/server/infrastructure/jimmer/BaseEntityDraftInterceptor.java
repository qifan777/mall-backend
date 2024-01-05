package io.qifan.mall.server.infrastructure.jimmer;

import cn.dev33.satoken.stp.StpUtil;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.sql.DraftInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class BaseEntityDraftInterceptor implements DraftInterceptor<BaseEntity, BaseEntityDraft> {


    @Override
    public void beforeSave(@NotNull BaseEntityDraft draft, BaseEntity baseEntity) {
        draft.applyEditor(user -> {
            user.setId(StpUtil.getLoginIdAsString());
        });
        if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.CREATOR)) {
            draft.applyCreator(user -> {
                user.setId(StpUtil.getLoginIdAsString());
            });
        }
    }
}