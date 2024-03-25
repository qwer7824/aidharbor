package com.aidharbor.Repository;

import com.aidharbor.Entity.Catalog;
import com.aidharbor.Entity.UserGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Long> {

}
