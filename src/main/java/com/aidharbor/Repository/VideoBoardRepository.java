package com.aidharbor.Repository;

import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.VideoBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoBoardRepository extends JpaRepository<VideoBoard,Long> {
}
