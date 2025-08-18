package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TrelloWebhook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrelloWebhook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrelloWebhookRepository extends JpaRepository<TrelloWebhook, Long> {}
