-- Example: Add constraints for data validation
-- This migration adds constraints to ensure data quality

-- Add check constraints to ensure non-negative energy values
ALTER TABLE springrest_hourly_usage 
ADD CONSTRAINT chk_community_produced_positive 
CHECK (community_produced >= 0);

ALTER TABLE springrest_hourly_usage 
ADD CONSTRAINT chk_community_used_positive 
CHECK (community_used >= 0);

ALTER TABLE springrest_hourly_usage 
ADD CONSTRAINT chk_grid_used_positive 
CHECK (grid_used >= 0);

-- Add check constraints for percentage values (0-100)
ALTER TABLE springrest_current_percentage 
ADD CONSTRAINT chk_community_depleted_valid 
CHECK (community_depleted >= 0 AND community_depleted <= 100);

ALTER TABLE springrest_current_percentage 
ADD CONSTRAINT chk_grid_portion_valid 
CHECK (grid_portion >= 0 AND grid_portion <= 100);
