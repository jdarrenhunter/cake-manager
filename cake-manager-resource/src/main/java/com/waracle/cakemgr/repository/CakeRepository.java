package com.waracle.cakemgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CakeRepository extends JpaRepository<CakeDao, Integer> {
    @Query("select case when count(c)> 0 then true else false end from CakeDao c where lower(c.title) like lower(:title)")
    boolean cakeExists(@Param("title") String title);
}