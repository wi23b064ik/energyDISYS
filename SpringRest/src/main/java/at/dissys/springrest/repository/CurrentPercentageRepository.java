package at.dissys.springrest.repository;

import at.dissys.springrest.entity.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, LocalDateTime> {
    Optional<CurrentPercentage> findByHour(LocalDateTime hour);
    List<CurrentPercentage> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
    Optional<CurrentPercentage> findTopByOrderByHourDesc();
}
