package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterJpaRepository extends JpaRepository<Writer, Integer> {

}