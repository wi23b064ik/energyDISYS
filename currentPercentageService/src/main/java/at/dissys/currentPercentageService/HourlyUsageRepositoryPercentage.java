    package at.dissys.currentPercentageService;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.time.LocalDateTime;
    import java.util.List;


    @Repository
    public interface HourlyUsageRepositoryPercentage extends JpaRepository<HourlyUsagePercentage, LocalDateTime> {
        List<HourlyUsagePercentage> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
    }
