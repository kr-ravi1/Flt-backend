package com.flt.flt.dao;

import com.flt.flt.models.Categories;
import com.flt.flt.models.Difficulties;
import com.flt.flt.models.QuizCard;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizCardRepository extends JpaRepository<QuizCard, Long> {
    @Query("SELECT q FROM QuizCard q WHERE q.id = :id")
    Optional<QuizCard> findById(@Param("id") long id);
    @Modifying
    @Transactional
    @Query("UPDATE QuizCard c SET c.cardOrder = c.cardOrder - 1 WHERE c.cardOrder > :deletedOrder")
    void updateCardOrderAfterDeletion(long deletedOrder);

    @Query("SELECT COALESCE(MAX(c.cardOrder),0) FROM QuizCard c")
    long findMaxCardOrder();

    @Query("SELECT c FROM QuizCard c WHERE c.cardOrder = :cardOrder")
    Optional<QuizCard> findByCardOrder(@Param("cardOrder") long cardOrder);

    @Query("SELECT c FROM QuizCard c")
    List<QuizCard> findAllCards();

    @Query("SELECT c FROM QuizCard c WHERE c.difficulty = :difficulty AND c.category = :category")
    List<QuizCard> findByDifficultyAndCategory(@Param("difficulty") Difficulties difficulty,@Param("category") Categories category);

    @Query("SELECT c FROM QuizCard c WHERE c.difficulty = :difficulty")
    List<QuizCard> findByDifficulty(@Param("difficulty") Difficulties difficulty);

    @Query("SELECT c FROM QuizCard c WHERE c.category = :category")
    List<QuizCard> findByCategory(@Param("category") Categories category);

    @Query("SELECT c FROM QuizCard c WHERE c.id = :id")
    Optional<QuizCard> findCardById(@Param("id") long id);
}
