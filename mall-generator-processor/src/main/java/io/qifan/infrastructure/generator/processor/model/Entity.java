package io.qifan.infrastructure.generator.processor.model;


import io.qifan.infrastructure.generator.processor.model.common.Field;
import io.qifan.infrastructure.generator.processor.model.common.ModelElement;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import io.qifan.infrastructure.generator.processor.model.controller.Controller;
import io.qifan.infrastructure.generator.processor.model.dto.Dto;
import io.qifan.infrastructure.generator.processor.model.front.CreateForm;
import io.qifan.infrastructure.generator.processor.model.front.Dialog;
import io.qifan.infrastructure.generator.processor.model.front.Query;
import io.qifan.infrastructure.generator.processor.model.front.Store;
import io.qifan.infrastructure.generator.processor.model.front.Table;
import io.qifan.infrastructure.generator.processor.model.front.UpdateForm;
import io.qifan.infrastructure.generator.processor.model.front.View;

import io.qifan.infrastructure.generator.processor.model.repository.Repository;
import io.qifan.infrastructure.generator.processor.model.service.Service;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Entity extends ModelElement {

  private Type type;
  private List<Field> fields;
  private Controller controller;
  private Dto dto;
  private Repository repository;
  private Service service;
  private View view;
  private Query query;
  private Store store;
  private Table table;
  private Dialog dialog;
  private UpdateForm updateForm;
  private CreateForm createForm;

  @Override
  public Set<Type> getImportTypes() {
    return null;
  }
}
