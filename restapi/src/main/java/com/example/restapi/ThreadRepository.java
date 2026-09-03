package com.example.restapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Integer> {
    @Query("SELECT DISTINCT t FROM Thread t LEFT JOIN t.replies r " +
        "WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "OR LOWER(t.text) LIKE LOWER(CONCAT('%', :q, '%'))" +
        "OR LOWER(r.text) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Thread> searchThreads(@Param("q") String query);
}
