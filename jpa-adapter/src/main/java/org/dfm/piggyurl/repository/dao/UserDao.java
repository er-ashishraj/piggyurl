package org.dfm.piggyurl.repository.dao;

import org.dfm.piggyurl.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserNameAndPassword(final String userName, final String password);
    Optional<UserEntity> findByUserName(final String userName);
}
