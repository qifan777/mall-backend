package io.qifan.mall.server.infrastructure.jimmer;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.sql.DraftInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BaseDateTimeDraftInterceptor implements
    DraftInterceptor<BaseDateTime, BaseDateTimeDraft> {


  @Override
  public void beforeSave(@NotNull BaseDateTimeDraft draft, BaseDateTime baseDateTime) {
    draft.setEditedTime(LocalDateTime.now());
    if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.CREATED_TIME)) {
      draft.setCreatedTime(LocalDateTime.now());
    }
  }
}
