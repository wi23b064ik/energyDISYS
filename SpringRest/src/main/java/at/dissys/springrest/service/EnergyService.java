package at.dissys.springrest.service;

import at.dissys.springrest.dto.CurrentEnergy;
import at.dissys.springrest.dto.HistoricalEnergy;

import java.time.LocalDateTime;
import java.util.List;

public interface EnergyService {
    CurrentEnergy getCurrentEnergy();
    List<HistoricalEnergy> getHistoricalUsage(LocalDateTime start, LocalDateTime end);
    long getHourlyUsageCount();
    long getCurrentPercentageCount();
    void clearHourlyData(LocalDateTime hour);
}
