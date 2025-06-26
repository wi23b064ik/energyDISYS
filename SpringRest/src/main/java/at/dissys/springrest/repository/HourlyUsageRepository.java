package at.dissys.springrest.repository;

import at.dissys.springrest.entity.HourlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HourlyUsageRepository extends JpaRepository<HourlyUsage, LocalDateTime> {
    List<HourlyUsage> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
