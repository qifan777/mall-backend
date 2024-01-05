package io.qifan.mall.server.user.service;

import cn.dev33.satoken.secure.BCrypt;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.dto.UserInput;
import io.qifan.mall.server.user.entity.dto.UserSpec;
import io.qifan.mall.server.user.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public User findById(String id) {
    return userRepository.findById(id, UserRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(
            ResultCode.NotFindError, "数据不存在"));
  }

  public String save(UserInput userInput) {
    if (userInput.getPassword().length() <= 16) {
      userInput.setPassword(BCrypt.hashpw(userInput.getPassword()));
    }
    return userRepository.save(userInput).id();
  }

  public Page<User> query(QueryRequest<UserSpec> queryRequest) {
    return userRepository.findPage(queryRequest, UserRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    userRepository.deleteAllById(ids);
    return true;
  }
}