-- Initial schema creation for currentPercentageService
-- Tables: percentage_hourly_usage, percentage_current_percentage

-- Create percentage_hourly_usage table
CREATE TABLE IF NOT EXISTS percentage_hourly_usage (
    hour TIMESTAMP PRIMARY KEY,
    community_produced DOUBLE PRECISION NOT NULL DEFAULT 0,
    community_used DOUBLE PRECISION NOT NULL DEFAULT 0,
    grid_used DOUBLE PRECISION NOT NULL DEFAULT 0
);

-- Create percentage_current_percentage table
CREATE TABLE IF NOT EXISTS percentage_current_percentage (
    hour TIMESTAMP PRIMARY KEY,
    community_depleted DOUBLE PRECISION NOT NULL DEFAULT 0,
    grid_portion DOUBLE PRECISION NOT NULL DEFAULT 0
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_percentage_hourly_usage_hour ON percentage_hourly_usage(hour);
CREATE INDEX IF NOT EXISTS idx_percentage_current_percentage_hour ON percentage_current_percentage(hour);
