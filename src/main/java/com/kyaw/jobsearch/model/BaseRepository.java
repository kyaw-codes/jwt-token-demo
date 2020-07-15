package com.kyaw.jobsearch.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> search(String jpql, Map<String, Object> params);

    Long count(String jpql, Map<String, Object> params);

    <D> List<D> search(String jpql, Map<String, Object> params, Class<D> type);

}
