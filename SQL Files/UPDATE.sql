USE cloc;

ALTER TABLE cloc_tech
	DROP COLUMN musket_tech,
	ADD COLUMN musket_tech TINYINT UNSIGNED DEFAULT 1;