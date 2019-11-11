USE cloc;

ALTER TABLE cloc_cities
	ADD devastation TINYINT DEFAULT 0 AFTER type;

ALTER TABLE cloc_army
	ADD fortification INT DEFAULT 0 AFTER artillery;