package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineJpaRepository extends JpaRepository<Magazine,Integer>{

}