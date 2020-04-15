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

ALTER TABLE cloc_policy
	ADD COLUMN fortification_policy ENUM('UNOCCUPIED_FORTIFICATION', 'MINIMAL_FUNDING_FORTIFICATION',
	    'PARTIAL_FUNDING_FORTIFICATION', 'FULL_FUNDING_FORTIFICATION', 'MAX_FORTIFICATION')
	    DEFAULT 'PARTIAL_FUNDING_FORTIFICATION',
    ADD COLUMN fortification_change BIGINT DEFAULT 0;

ALTER TABLE cloc_tech
	ADD COLUMN basic_artificial_fertilizer TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN artificial_fertilizer TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN advanced_artificial_fertilizer TINYINT UNSIGNED DEFAULT 0;

ALTER TABLE cloc_tech
	ADD COLUMN farming_machines TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN advanced_farming_machines TINYINT UNSIGNED DEFAULT 0;

ALTER TABLE cloc_policy
	ADD COLUMN farming_subsidies ENUM('NO_SUBSIDIES_FARMING', 'REDUCED_SUBSIDIES_FARMING',
										 'STANDARD_SUBSIDIES_FARMING', 'SUBSTANTIAL_SUBSIDIES_FARMING')
	DEFAULT 'STANDARD_SUBSIDIES_FARMING',
	ADD COLUMN farming_subsidies_change BIGINT DEFAULT 0;

ALTER TABLE cloc_domestic
	ADD COLUMN months_in_famine INT DEFAULT 0;