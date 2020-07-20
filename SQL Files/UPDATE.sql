USE cloc;

ALTER TABLE cloc_army
	ADD COLUMN casualties BIGINT;

CREATE TABLE nation_history (
    nation_id INT,
    month BIGINT,
    gdp DECIMAL(14,2),
    growth DECIMAL(14,2),
    population BIGINT,
    airforce BIGINT,
    navy BIGINT,
    army INT,
    casualties BIGINT,
    FOREIGN KEY fk_nation (nation_id) REFERENCES cloc_login (id)
);