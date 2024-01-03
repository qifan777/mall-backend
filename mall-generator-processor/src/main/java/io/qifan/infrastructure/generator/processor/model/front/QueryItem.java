package io.qifan.infrastructure.generator.processor.model.front;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryItem extends ItemField {
    public QueryItem() {
        super();

    }

    public QueryItem(ItemField itemField) {
        super(itemField.getEntityType(), itemField.getLabel(),
                itemField.getBind(),
                itemField.getFieldName(),
                itemField.getDictId(),
                itemField.getItemType(),
                itemField.getNullable());
    }
}
