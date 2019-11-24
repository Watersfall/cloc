USE cloc;

ALTER TABLE cloc_tech
	ADD column recon_balloon_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column recon_plane_tech TINYINT UNSIGNED DEFAULT 0,
    ADD column zeppelin_bombers_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column biplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column triplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	ADD column monoplane_fighter_tech TINYINT UNSIGNED DEFAULT 0;