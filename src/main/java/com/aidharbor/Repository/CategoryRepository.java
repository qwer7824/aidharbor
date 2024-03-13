package com.aidharbor.Repository;

import com.aidharbor.Entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {

    @Query("SELECT c FROM ProductCategory c LEFT JOIN c.parent p ORDER BY p.id ASC NULLS FIRST, c.id ASC")
    List<ProductCategory> findAllOrderByParentIdAscNullsFirstCategoryIdAsc();

}
