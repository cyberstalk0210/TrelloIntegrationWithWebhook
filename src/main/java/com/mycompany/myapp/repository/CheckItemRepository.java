package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CheckItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckItemRepository extends JpaRepository<CheckItem, Long> {
    Optional<CheckItem> findByCheckItemId(String checkItemId);
}
