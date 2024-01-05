package io.qifan.mall.server;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.qifan.infrastructure.generator.processor.QiFanGenerator;
import io.qifan.mall.server.dict.entity.Dict;
import io.qifan.mall.server.dict.model.DictGenContext;
import io.qifan.mall.server.dict.repository.DictRepository;
import io.qifan.mall.server.dict.service.DictService.SimpleClasspathLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MallCodeGenerator {

  @Autowired
  DictRepository dictRepository;

  public static void main(String[] args) {
    QiFanGenerator qiFanGenerator = new QiFanGenerator();
    qiFanGenerator.process("io.qifan.mall.server", "template");
  }  

  @Test
  @SneakyThrows
  public void generate() {
    List<Dict> all = dictRepository.findAll();
    Map<String, List<Dict>> dictMaps = new HashMap<>();
    all.forEach(dict -> {
      dictMaps.putIfAbsent(dict.dictEnName(), new ArrayList<>());
      List<Dict> dictList = dictMaps.get(dict.dictEnName());
      dictList.add(dict);
    });
    DictGenContext dictGenContext = new DictGenContext(
        all.stream().map(Dict::dictEnName).distinct()
            .collect(Collectors.toList()),
        dictMaps);
    // 创建Freemarker的配置对象
    Configuration configuration = new Configuration();
    configuration.setLocalizedLookup(false);
    configuration.setTemplateLoader(new SimpleClasspathLoader());
    // 获取模板
    Template template = configuration.getTemplate("/template/dict-ts.ftl");
    // 创建输出文件
    File outputFile = new File("dict-constants.ts");
    outputFile.createNewFile();
    // 创建Writer对象
    Writer writer = new FileWriter(outputFile);
    // 渲染模板
    template.process(dictGenContext, writer);

    // 获取模板
    template = configuration.getTemplate("/template/dict-java.ftl");
    // 创建输出文件
    outputFile = new File("DictConstants.java");
    outputFile.createNewFile();
    // 创建Writer对象
    writer = new FileWriter(outputFile);
    // 渲染模板
    template.process(dictGenContext, writer);
    writer.flush();
    writer.close();
  }
}
