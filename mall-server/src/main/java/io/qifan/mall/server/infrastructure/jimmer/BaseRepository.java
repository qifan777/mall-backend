package io.qifan.mall.server.infrastructure.jimmer;


import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends JRepository<T, String> {
}