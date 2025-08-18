package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CardRepositoryWithBagRelationships {
    Optional<Card> fetchBagRelationships(Optional<Card> card);

    List<Card> fetchBagRelationships(List<Card> cards);

    Page<Card> fetchBagRelationships(Page<Card> cards);
}
