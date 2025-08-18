package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Board;
import java.nio.channels.FileChannel;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Board entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTrelloId(String trelloId);
}
