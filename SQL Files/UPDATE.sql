USE cloc;

ALTER TABLE cloc_cities
	MODIFY COLUMN population BIGINT DEFAULT 100000,
    MODIFY COLUMN iron_mines INT DEFAULT 1,
    MODIFY COLUMN coal_mines INT DEFAULT 1,
    MODIFY COLUMN oil_wells INT DEFAULT 1;

ALTER TABLE cloc_policy
	MODIFY COLUMN manpower_change BIGINT DEFAULT -2,
    MODIFY COLUMN food_change BIGINT DEFAULT -2,
    MODIFY COLUMN economy_change BIGINT DEFAULT -2,
    MODIFY COLUMN fortification_change BIGINT DEFAULT -2,
    MODIFY COLUMN farming_subsidies_change BIGINT DEFAULT -2;