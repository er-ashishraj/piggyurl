package org.dfm.piggyurl.repository.dao;

import java.util.List;
import java.util.Optional;
import org.dfm.piggyurl.repository.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDao extends JpaRepository<CardEntity, Long> {
  Optional<CardEntity> findById(final Long cardId);

  List<CardEntity> findAllByGroupIdAndApproved(final Long groupId, final String approved);

}
