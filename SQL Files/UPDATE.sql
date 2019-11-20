USE cloc;

ALTER TABLE cloc_cities
	ADD devastation TINYINT DEFAULT 0 AFTER type;

ALTER TABLE cloc_army
	ADD fortification INT DEFAULT 0 AFTER artillery;

ALTER TABLE cloc_policy
	CHANGE manpower manpower_policy TINYINT UNSIGNED DEFAULT 2,
    CHANGE food food_policy TINYINT UNSIGNED DEFAULT 1,
	CHANGE economy economy_policy TINYINT UNSIGNED DEFAULT 0;