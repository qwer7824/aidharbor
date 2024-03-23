package com.aidharbor.Repository;

import com.aidharbor.Entity.Member;
import com.aidharbor.Entity.Partners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnersRepository extends JpaRepository<Partners,Long> {
}
