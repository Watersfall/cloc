USE cloc;

ALTER TABLE cloc_economy
	ADD COLUMN recent_conscription INT DEFAULT 0,
	ADD COLUMN recent_deconscription INT DEFAULT 0;