package io.qifan.mall.server;

import io.qifan.infrastructure.generator.processor.QiFanGenerator;

public class MallCodeGenerator {

  public static void main(String[] args) {
    QiFanGenerator qiFanGenerator = new QiFanGenerator();
    qiFanGenerator.process("io.qifan.mall.server", "template");
  }
}
