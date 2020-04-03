USE cloc;

ALTER TABLE cloc_economy
	ADD COLUMN recent_conscription INT DEFAULT 0,
	ADD COLUMN recent_deconscription INT DEFAULT 0;

ALTER TABLE cloc_domestic
	DROP COLUMN population;

ALTER TABLE cloc_cities
	ADD COLUMN population BIGINT DEFAULT 0;
ALTER TABLE global_stats_history
	CHANGE week month BIGINT;

ALTER TABLE cloc_main
	CHANGE week month BIGINT;

ALTER TABLE cloc_army
	ADD COLUMN tank INT;