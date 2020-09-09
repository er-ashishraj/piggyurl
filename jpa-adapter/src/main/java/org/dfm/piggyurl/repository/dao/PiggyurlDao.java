package org.dfm.piggyurl.repository.dao;

import org.dfm.piggyurl.repository.entity.PiggyurlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PiggyurlDao extends JpaRepository<PiggyurlEntity, Long> {

    Optional<PiggyurlEntity> findByCode(String code);
}
