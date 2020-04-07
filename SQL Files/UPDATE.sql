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

ALTER TABLE cloc_tech
	ADD COLUMN basic_trenches_tech TINYINT UNSIGNED DEFAULT 0,
    ADD COLUMN advanced_trenches_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN basic_fortifications_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN reinforced_concrete_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN mobile_defense_tech TINYINT UNSIGNED DEFAULT 0;