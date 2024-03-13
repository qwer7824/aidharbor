package com.aidharbor.Repository;

import com.aidharbor.Entity.Contact;
import com.aidharbor.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

}
