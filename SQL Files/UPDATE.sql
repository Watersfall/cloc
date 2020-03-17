USE cloc;

DELETE FROM production WHERE id>0;

ALTER TABLE cloc_cities
	DROP COLUMN military_industry;

ALTER TABLE production
	DROP COLUMN efficiency,
    DROP COLUMN factories;

CREATE TABLE factories(
	id INT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	city_id INT,
	production_id INT,
	efficiency INT DEFAULT 1500,
	FOREIGN KEY fk_production (production_id) REFERENCES production (id) ON DELETE SET NULL,
	FOREIGN KEY fk_owner (owner) REFERENCES cloc_login(id) ON DELETE CASCADE,
	FOREIGN KEY fk_city (city_id) REFERENCES cloc_cities(id) ON DELETE CASCADE
);