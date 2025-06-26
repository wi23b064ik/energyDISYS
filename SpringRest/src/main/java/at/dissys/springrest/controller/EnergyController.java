package at.dissys.springrest.controller;

import at.dissys.springrest.dto.CurrentEnergy;
import at.dissys.springrest.dto.HistoricalEnergy;
import at.dissys.springrest.service.EnergyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {
    private EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    @GetMapping("/current")
    public CurrentEnergy getCurrentEnergy() {
        return energyService.getCurrentEnergy();
    }

    @GetMapping("/historical")
    public List<HistoricalEnergy> getHistoricalData(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return energyService.getHistoricalUsage(start, end);
    }    @GetMapping("/test")
    public String testDatabase() {
        try {
            long usageCount = energyService.getHourlyUsageCount();
            long percentageCount = energyService.getCurrentPercentageCount();
            return "HourlyUsage records: " + usageCount + ", CurrentPercentage records: " + percentageCount;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/debug")
    public String debugData() {
        try {
            List<HistoricalEnergy> data = energyService.getHistoricalUsage(
                LocalDateTime.now().withHour(0).withMinute(0),
                LocalDateTime.now().withHour(23).withMinute(59)
            );
            StringBuilder sb = new StringBuilder();
            sb.append("Today's hourly data:\n");
            for (HistoricalEnergy h : data) {
                sb.append("Hour: ").append(h.getHour())
                  .append(", Produced: ").append(h.getCommunityProduced())
                  .append(", Used: ").append(h.getCommunityUsed())
                  .append(", Grid: ").append(h.getGridUsed())
                  .append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/detailed-debug")
    public String detailedDebug() {
        try {
            List<HistoricalEnergy> data = energyService.getHistoricalUsage(
                LocalDateTime.now().withHour(0).withMinute(0),
                LocalDateTime.now().withHour(23).withMinute(59)
            );
            StringBuilder sb = new StringBuilder();
            sb.append("Detailed Analysis:\n");
            for (HistoricalEnergy h : data) {
                double produced = h.getCommunityProduced();
                double used = h.getCommunityUsed();
                double grid = h.getGridUsed();
                double totalConsumption = used + grid;
                
                sb.append("Hour: ").append(h.getHour()).append("\n");
                sb.append("  Community Produced: ").append(produced).append(" kWh\n");
                sb.append("  Community Used: ").append(used).append(" kWh\n");
                sb.append("  Grid Used: ").append(grid).append(" kWh\n");
                sb.append("  Total Consumption: ").append(totalConsumption).append(" kWh\n");
                sb.append("  Issue: Community used (").append(used).append(") > Produced (").append(produced).append(")? ");
                sb.append(used > produced ? "YES - ERROR!" : "No - OK").append("\n");
                sb.append("  Available after use: ").append(produced - used).append(" kWh\n\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/clear-current-hour")
    public String clearCurrentHour() {
        try {
            LocalDateTime currentHour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
            energyService.clearHourlyData(currentHour);
            return "Cleared data for hour: " + currentHour;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
