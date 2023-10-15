package com.example.demo;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchWordRepository extends JpaRepository<SearchedWord, String> {
    String SELECT_WORD_CONS = "select " +
            "w.word " +
            "from SearchedWord w " +
            "where w.word >= :searchWordBegin " +
            "and w.word < :searchWordEnd";

    String SELECT_WORD = "select " +
            "w.word " +
            "from SearchedWord w " +
            "where w.word like CONCAT(:searchWord,'%')";

    @Query(value = SELECT_WORD_CONS)
    List<String> findByWordCons(@Param("searchWordBegin") String searchWordBegin, @Param("searchWordEnd") String searchWordEnd);

    @Query(value = SELECT_WORD)
    List<String> findByWord(@Param("searchWord") String searchWord);
}

