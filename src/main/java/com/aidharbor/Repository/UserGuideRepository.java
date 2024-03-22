package com.aidharbor.Repository;

import com.aidharbor.Entity.UserGuide;
import com.aidharbor.Entity.VideoBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGuideRepository extends JpaRepository<UserGuide,Long> {

}
