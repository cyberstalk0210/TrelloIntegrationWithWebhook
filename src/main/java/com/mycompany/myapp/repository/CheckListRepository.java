package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CheckList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    Optional<CheckList> findByCheckListId(String checkListId);
}
