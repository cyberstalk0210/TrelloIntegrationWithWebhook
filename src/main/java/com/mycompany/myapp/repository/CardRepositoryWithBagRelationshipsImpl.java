package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CardRepositoryWithBagRelationshipsImpl implements CardRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CARDS_PARAMETER = "cards";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Card> fetchBagRelationships(Optional<Card> card) {
        return card.map(this::fetchLabels);
    }

    @Override
    public Page<Card> fetchBagRelationships(Page<Card> cards) {
        return new PageImpl<>(fetchBagRelationships(cards.getContent()), cards.getPageable(), cards.getTotalElements());
    }

    @Override
    public List<Card> fetchBagRelationships(List<Card> cards) {
        return Optional.of(cards).map(this::fetchLabels).orElse(Collections.emptyList());
    }

    Card fetchLabels(Card result) {
        return entityManager
            .createQuery("select card from Card card left join fetch card.labels where card.id = :id", Card.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Card> fetchLabels(List<Card> cards) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cards.size()).forEach(index -> order.put(cards.get(index).getId(), index));
        List<Card> result = entityManager
            .createQuery("select card from Card card left join fetch card.labels where card in :cards", Card.class)
            .setParameter(CARDS_PARAMETER, cards)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
