package com.example.TestTask.repository;

import com.example.TestTask.Entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByLogin (String login);
    List<UserEntity> findByBirthdayBetween(Date startDate, Date endDate);

}
