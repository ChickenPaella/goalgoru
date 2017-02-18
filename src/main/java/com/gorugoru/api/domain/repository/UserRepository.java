package com.gorugoru.api.domain.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorugoru.api.domain.model.User;

/**
 * repository layer
 * 쿼리는 method네이밍으로 자동 생성
 * 네이밍 참고: http://docs.spring.io/spring-data/jpa/docs/1.7.0.RELEASE/reference/html/#jpa.query-methods
 * @author Administrator
 *
 */

@Transactional
@RepositoryRestResource(path = "user")
public interface UserRepository extends CrudRepository<User, Long> {
 
	public abstract List<User> findByNameAndBirthDateLessThan(String name, Date birth_date);
 
	@Query("select t from User t where name=:name and birth_date < :birth_date")
	public abstract List<User> findByNameAndBirthDateLessThanSQL(@Param("name") String name, @Param("birth_date") Date birth_date);
 
	public abstract List<User> findByNameAndBirthDateLessThanOrderByBirthDateDesc(String name, Date birth_date);

	public abstract List<User> findByAuthProviderAndAuthUID(String string, String authUID);
 
}