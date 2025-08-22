package com.mycompany.myapp.repository;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.mycompany.myapp.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByTrelloId(String trelloId);
}
