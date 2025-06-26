-- Example: Add index for better query performance
-- This migration adds an index for date range queries

CREATE INDEX IF NOT EXISTS idx_springrest_hourly_usage_hour_desc 
ON springrest_hourly_usage(hour DESC);

CREATE INDEX IF NOT EXISTS idx_springrest_current_percentage_hour_desc 
ON springrest_current_percentage(hour DESC);
