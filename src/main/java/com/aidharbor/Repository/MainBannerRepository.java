package com.aidharbor.Repository;

import com.aidharbor.Entity.MainBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainBannerRepository extends JpaRepository<MainBanner,Long> {
}
