package io.qifan.mall.server.dict.service;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.dict.entity.Dict;
import io.qifan.mall.server.dict.entity.dto.DictInput;
import io.qifan.mall.server.dict.entity.dto.DictSpec;
import io.qifan.mall.server.dict.repository.DictRepository;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DictService {

  private final DictRepository dictRepository;

  public Dict findById(String id) {
    return dictRepository.findById(id, DictRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(DictInput dictInput) {
    return dictRepository.save(dictInput).id();
  }

  public Page<Dict> query(QueryRequest<DictSpec> queryRequest) {
    return dictRepository.findPage(queryRequest, DictRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    dictRepository.deleteAllById(ids);
    return true;
  }
  public static final class SimpleClasspathLoader implements TemplateLoader {
    @Override
    public Reader getReader(Object name, String encoding) throws IOException {
      URL url = getClass().getClassLoader().getResource(java.lang.String.valueOf(name));
      if (url == null) {
        throw new IllegalStateException(name + " not found on classpath");
      }
      URLConnection connection = url.openConnection();

      // don't use jar-file caching, as it caused occasionally closed input streams [at least under JDK 1.8.0_25]
      connection.setUseCaches(false);

      InputStream is = connection.getInputStream();

      return new InputStreamReader(is, StandardCharsets.UTF_8);
    }

    @Override
    public long getLastModified(Object templateSource) {
      return 0;
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
      return name;
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
    }
  }

}