USE cloc;

ALTER TABLE cloc_policy
	DROP COLUMN manpower_policy,
	DROP COLUMN food_policy,
	DROP COLUMN economy_policy,
	ADD COLUMN manpower_policy ENUM('DISARMED_MANPOWER', 'VOLUNTEER_MANPOWER', 'RECRUITMENT_MANPOWER', 'MANDATORY_MANPOWER', 'SCRAPING_THE_BARREL_MANPOWER') DEFAULT 'VOLUNTEER_MANPOWER',
	ADD COLUMN food_policy ENUM('RATIONING_FOOD', 'NORMAL_FOOD', 'FREE_FOOD') DEFAULT 'NORMAL_FOOD',
	ADD COLUMN economy_policy ENUM('CIVILIAN_ECONOMY', 'EXTRACTION_ECONOMY', 'INDUSTRY_ECONOMY', 'AGRARIAN_ECONOMY', 'WAR_ECONOMY') DEFAULT 'CIVILIAN_ECONOMY';

USE cloc;

ALTER TABLE cloc_main
    DROP COLUMN turn,
    ADD COLUMN week BIGINT,
	ADD COLUMN day BIGINT;

USE cloc;

ALTER TABLE cloc_policy
	DROP COLUMN manpower_change,
    DROP COLUMN food_change,
    DROP COLUMN economy_change,
    ADD COLUMN manpower_change BIGINT DEFAULT 0,
    ADD COLUMN food_change BIGINT DEFAULT 0,
    ADD COLUMN economy_change BIGINT DEFAULT 0;

ALTER TABLE cloc_war
	DROP COLUMN start,
    DROP COLUMN end,
    ADD COLUMN start BIGINT DEFAULT -1,
    ADD COLUMN end BIGINT DEFAULT -1;

ALTER TABLE cloc_declarations
	DROP COLUMN sent,
    ADD COLUMN sent BIGINT;

CREATE TABLE global_stats_history(
    week BIGINT,
	total_nations BIGINT,
	total_neutral_nations BIGINT,
	total_entente_nations BIGINT,
	total_central_powers_nations BIGINT,
	total_population BIGINT,
	total_soldiers BIGINT,
	total_civilian_factories BIGINT,
	total_military_factories BIGINT,
	total_universities BIGINT,
	total_iron_mines BIGINT,
	total_coal_mines BIGINT,
	total_oil_wells BIGINT
);

ALTER TABLE cloc_cities
	MODIFY iron_mines INT DEFAULT 5,
    MODIFY coal_mines INT DEFAULT 5,
    MODIFY civilian_industry INT DEFAULT 1,
    MODIFY military_industry INT DEFAULT 1;