USE cloc;

ALTER TABLE cloc_domestic
	DROP COLUMN population;

ALTER TABLE cloc_cities
	ADD COLUMN population BIGINT DEFAULT 0;