USE cloc;

ALTER TABLE cloc_military
    DROP COLUMN fighters,
	ADD COLUMN recon_balloons INT UNSIGNED DEFAULT 0,
    ADD COLUMN recon_planes INT UNSIGNED DEFAULT 0,
    ADD COLUMN biplane_fighters INT UNSIGNED DEFAULT 0,
    ADD COLUMN triplane_fighters INT UNSIGNED DEFAULT 0,
    ADD COLUMN monoplane_fighters INT UNSIGNED DEFAULT 0;

ALTER TABLE cloc_tech
	ADD column recon_balloon_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column recon_plane_tech TINYINT UNSIGNED DEFAULT 0,
    ADD column zeppelin_bombers_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column biplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column triplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column monoplane_fighter_tech TINYINT UNSIGNED DEFAULT 0;

CREATE TABLE production(
	id INT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	factories INT DEFAULT 1,
	efficiency INT DEFAULT 2500,
	production TEXT,
	progress INT DEFAULT 0,
	FOREIGN KEY fk_production (owner) REFERENCES cloc_login(id) ON DELETE CASCADE
);