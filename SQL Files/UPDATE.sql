USE cloc;

ALTER TABLE cloc_cities
	MODIFY COLUMN population BIGINT DEFAULT 100000,
    MODIFY COLUMN iron_mines INT DEFAULT 1,
    MODIFY COLUMN coal_mines INT DEFAULT 1,
    MODIFY COLUMN oil_wells INT DEFAULT 1;