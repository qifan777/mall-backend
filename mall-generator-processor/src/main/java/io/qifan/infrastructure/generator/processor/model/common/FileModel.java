package io.qifan.infrastructure.generator.processor.model.common;


import io.qifan.infrastructure.generator.processor.model.common.ModelElement;
import io.qifan.infrastructure.generator.processor.model.common.Type;

import java.util.Set;

public abstract class FileModel extends ModelElement {
    public abstract String getFileName();

    @Override
    public Set<Type> getImportTypes() {
        return null;
    }
}
