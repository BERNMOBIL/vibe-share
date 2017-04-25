DROP TABLE IF EXISTS area CASCADE;
DROP TABLE IF EXISTS route CASCADE;
DROP TABLE IF EXISTS stop CASCADE;
DROP TABLE IF EXISTS journey CASCADE;
DROP TABLE IF EXISTS calendar_date CASCADE;
DROP TABLE IF EXISTS calendar_exception CASCADE;
DROP TABLE IF EXISTS schedule CASCADE;
DROP TABLE IF EXISTS schedule_update CASCADE;
DROP TABLE IF EXISTS journey_disruption CASCADE;

CREATE TABLE area (
  id SERIAL PRIMARY KEY,
  name TEXT,
  update TIMESTAMP
);
CREATE TABLE route(
  id SERIAL PRIMARY KEY,
  type DECIMAL,
  line TEXT,
  update TIMESTAMP
);
CREATE TABLE stop (
  id SERIAL PRIMARY KEY,
  name TEXT,
  area INTEGER,
  update TIMESTAMP
);
CREATE TABLE journey (
  id SERIAL PRIMARY KEY,
  headsign TEXT,
  route INTEGER,
  update TIMESTAMP
);
CREATE TABLE calendar_date (
  id SERIAL PRIMARY KEY,
  days JSON,
  valid_from DATE,
  valid_until DATE,
  journey INTEGER,
  update TIMESTAMP
);
CREATE TABLE calendar_exception (
  id SERIAL PRIMARY KEY,
  date DATE,
  type DECIMAL,
  calendar_date INTEGER,
  update TIMESTAMP
);
CREATE TABLE schedule (
  id SERIAL PRIMARY KEY,
  platform TEXT,
  planned_arrival TIME,
  planned_departure TIME,
  stop INTEGER,
  journey INTEGER,
  update TIMESTAMP
);
CREATE TABLE schedule_update (
  id SERIAL PRIMARY KEY,
  schedule INTEGER,
  actual_arrival TIME,
  actual_departure TIME
);
CREATE TABLE journey_disruption (
  id SERIAL PRIMARY KEY,
  message TEXT,
  start TIMESTAMP,
  planned_end TIMESTAMP,
  journey INTEGER
);
ALTER TABLE stop ADD FOREIGN KEY(area) REFERENCES area;
ALTER TABLE journey ADD FOREIGN KEY(route) REFERENCES route;
ALTER TABLE calendar_date ADD FOREIGN KEY(journey) REFERENCES journey;
ALTER TABLE calendar_exception ADD FOREIGN KEY(calendar_date) REFERENCES calendar_date;
ALTER TABLE schedule ADD FOREIGN KEY(stop) REFERENCES stop;
ALTER TABLE schedule ADD FOREIGN KEY(journey) REFERENCES journey;
ALTER TABLE schedule_update ADD FOREIGN KEY(schedule) REFERENCES schedule;
ALTER TABLE journey_disruption ADD FOREIGN KEY(journey) REFERENCES journey;

CREATE INDEX area_on_stop_fkey_index ON stop(area);
CREATE INDEX route_on_journey_fkey_index ON journey(route);
CREATE INDEX journey_on_calendar_date_fkey_index ON calendar_date(journey);
CREATE INDEX calendar_date_on_calendar_exception_fkey_index ON calendar_exception(calendar_date);
CREATE INDEX stop_on_schedule_fkey_index ON schedule(stop);
CREATE INDEX journey_on_schedule_fkey_index ON schedule(journey);
