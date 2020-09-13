package org.dfm.piggyurl.repository.dao;

import java.util.Optional;
import org.dfm.piggyurl.repository.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends JpaRepository<GroupEntity, Long> {

  Optional<GroupEntity> findByNameAndType(final String groupName, final String groupType);
}
