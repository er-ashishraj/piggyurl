package org.dfm.piggyurl.repository.dao;

import java.util.List;
import java.util.Optional;
import org.dfm.piggyurl.repository.entity.CardUpdateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardUpdateDao extends JpaRepository<CardUpdateEntity, Long> {

  Optional<CardUpdateEntity> findById(final Long cardId);

  List<CardUpdateEntity> findAllByApproverUserName(final String userName);

}
