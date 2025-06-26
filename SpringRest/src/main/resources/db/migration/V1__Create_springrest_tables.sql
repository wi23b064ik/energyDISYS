-- Initial schema creation for SpringRest service
-- Tables: springrest_hourly_usage, springrest_current_percentage

-- Create springrest_hourly_usage table
CREATE TABLE IF NOT EXISTS springrest_hourly_usage (
    hour TIMESTAMP PRIMARY KEY,
    community_produced DOUBLE PRECISION NOT NULL DEFAULT 0,
    community_used DOUBLE PRECISION NOT NULL DEFAULT 0,
    grid_used DOUBLE PRECISION NOT NULL DEFAULT 0
);

-- Create springrest_current_percentage table  
CREATE TABLE IF NOT EXISTS springrest_current_percentage (
    hour TIMESTAMP PRIMARY KEY,
    community_depleted DOUBLE PRECISION NOT NULL DEFAULT 0,
    grid_portion DOUBLE PRECISION NOT NULL DEFAULT 0
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_hourly_usage_hour ON springrest_hourly_usage(hour);
CREATE INDEX IF NOT EXISTS idx_current_percentage_hour ON springrest_current_percentage(hour);
