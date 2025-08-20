package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BoardList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BoardList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
    //    List<BoardList> findByTrelloId(String trelloId);

    Optional<BoardList> findByTrelloId(String trelloId);
}
