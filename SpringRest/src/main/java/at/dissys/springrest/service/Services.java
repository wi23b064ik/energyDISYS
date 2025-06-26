package at.dissys.springrest.service;
import at.dissys.springrest.dto.CurrentEnergy;
import at.dissys.springrest.dto.HistoricalEnergy;
import at.dissys.springrest.repository.HourlyUsageRepository;
import at.dissys.springrest.repository.CurrentPercentageRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Services implements EnergyService {
    private CurrentPercentageRepository percentageRepo;
    private HourlyUsageRepository usageRepo;

    public Services(CurrentPercentageRepository percentageRepo, HourlyUsageRepository usageRepo) {
        this.percentageRepo = percentageRepo;
        this.usageRepo = usageRepo;
    }

    @Override
    public CurrentEnergy getCurrentEnergy() {
        // Get the most recent percentage data
        return percentageRepo.findTopByOrderByHourDesc()
                .map(percentage -> {
                    CurrentEnergy dto = new CurrentEnergy();
                    dto.setCommunityPoolUsed(String.valueOf(percentage.getCommunityDepleted()));
                    dto.setGridPortion(String.valueOf(percentage.getGridPortion()));
                    return dto;
                }).orElse(null);
    }
    
    @Override
    public List<HistoricalEnergy> getHistoricalUsage(LocalDateTime start, LocalDateTime end) {
        return usageRepo.findAllByHourBetween(start, end).stream()
                .map(e -> {
                    HistoricalEnergy dto = new HistoricalEnergy();
                    dto.setHour(e.getHour());
                    dto.setCommunityProduced(e.getCommunityProduced());
                    dto.setCommunityUsed(e.getCommunityUsed());
                    dto.setGridUsed(e.getGridUsed());
                    return dto;
                }).collect(Collectors.toList());
    }
    
    @Override
    public long getHourlyUsageCount() {
        return usageRepo.count();
    }
    
    @Override
    public long getCurrentPercentageCount() {
        return percentageRepo.count();
    }
    
    @Override
    public void clearHourlyData(LocalDateTime hour) {
        usageRepo.deleteById(hour);
    }
}
