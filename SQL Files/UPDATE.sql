DROP TABLE cloc_armies;

ALTER TABLE cloc_war
	ADD COLUMN winner INT DEFAULT -1
	AFTER end;

ALTER TABLE cloc_economy
	DROP COLUMN iron_mines,
	DROP COLUMN coal_mines,
	DROP COLUMN oil_wells,
	DROP COLUMN civilian_industry,
	DROP COLUMN military_industry,
	DROP COLUMN universities;

ALTER TABLE cloc_foreign
	DROP COLUMN alliance;

ALTER TABLE cloc_login
	DROP COLUMN sess,
    ADD COLUMN email TEXT AFTER username,
	ADD COLUMN register_ip VARCHAR(15) AFTER password,
    ADD COLUMN last_up VARCHAR(15) AFTER register_ip;

ALTER TABLE cloc_military
    ADD COLUMN bombers INT UNSIGNED DEFAULT 0 AFTER zeppelins,
	ADD COLUMN pre_battleships INT UNSIGNED DEFAULT 0 AFTER cruisers,
    DROP COLUMN army_home,
    DROP COLUMN training_home,
    DROP COLUMN weapons_home;

ALTER TABLE cloc_policy
    DROP COLUMN manpower,
    DROP COLUMN manpower_change,
    ADD COLUMN manpower TINYINT UNSIGNED DEFAULT 2,
	ADD COLUMN manpower_change INT DEFAULT 0,
    ADD COLUMN food TINYINT DEFAULT 1,
    ADD COLUMN food_change INT DEFAULT 0,
    ADD COLUMN economy TINYINT UNSIGNED DEFAULT 2,
	ADD COLUMN economy_change INT DEFAULT 0;

ALTER TABLE cloc_tech
	DROP COLUMN chemical_weapons_tech,
	DROP COLUMN tanks_tech,
	DROP COLUMN submarine_tech,
	DROP COLUMN logistics_tech,
	DROP COLUMN offensive_tech,
	DROP COLUMN defensive_tech,
	DROP COLUMN food_tech,
	ADD COLUMN chem_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN advanced_chem_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN bomber_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN strategic_bombing_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN tank_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN ship_oil_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN bolt_action_manual_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN semi_automatic_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN machine_gun_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN food_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN ball_and_powder_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN belt_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN brass_cartridge_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN detachable_magazines_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN paper_cartridge_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN rifle_clips_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN smokeless_powder_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN bolt_action_clip_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN musket_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN needle_nose_rifle_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN rifled_musket_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN single_shot_rifle_tech TINYINT UNSIGNED DEFAULT 0,
	ADD COLUMN straight_pull_rifle_tech TINYINT UNSIGNED DEFAULT 0;


ALTER TABLE cloc_cosmetic
	ADD CONSTRAINT fk_cosmetic FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_economy
	ADD CONSTRAINT fk_economy FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_domestic
	ADD CONSTRAINT fk_domestic FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_foreign
	ADD CONSTRAINT fk_foreign FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_military
	ADD CONSTRAINT fk_military FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_policy
	ADD CONSTRAINT fk_policy FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_tech
	ADD CONSTRAINT fk_tech FOREIGN KEY (id) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_news
	ADD CONSTRAINT fk_armies FOREIGN KEY (receiver) REFERENCES cloc_login (id) ON DELETE CASCADE;

ALTER TABLE cloc_war_logs
	ADD CONSTRAINT fk_logs FOREIGN KEY (attacker) REFERENCES cloc_login (id) ON DELETE CASCADE;

CREATE TABLE cloc_cities(
	id INT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	capital BIT,
	coastal BIT,
	railroads INT DEFAULT 1,
	ports INT DEFAULT 0,
	barracks INT DEFAULT 0,
	iron_mines INT UNSIGNED DEFAULT 2,
	coal_mines INT UNSIGNED DEFAULT 2,
	oil_wells INT UNSIGNED DEFAULT 0,
	civilian_industry INT UNSIGNED DEFAULT 0,
	military_industry INT UNSIGNED DEFAULT 0,
	nitrogen_industry INT UNSIGNED DEFAULT 0,
	universities INT UNSIGNED DEFAULT 0,
	name TEXT,
	type ENUM('MINING', 'DRILLING', 'INDUSTRY', 'FARMING'),
	FOREIGN KEY fk_cities (owner) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_army(
	id INT PRIMARY KEY AUTO_INCREMENT,
	size INT DEFAULT 20,
	training INT DEFAULT 50,
	musket INT DEFAULT 20000,
	rifled_musket INT DEFAULT 0,
	single_shot INT DEFAULT 0,
	needle_nose INT DEFAULT 0,
	bolt_action_manual INT DEFAULT 0,
	bolt_action_clip INT DEFAULT 0,
	straight_pull INT DEFAULT 0,
	semi_auto INT DEFAULT 0,
	machine_gun INT DEFAULT 0,
	artillery INT DEFAULT 0,
	FOREIGN KEY fk_army (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_treaties(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(32),
	flag VARCHAR(32) DEFAULT '',
	description TEXT
);

CREATE TABLE cloc_treaties_members(
	alliance_id INT,
	nation_id INT,
	founder BIT DEFAULT 0,
	manage BIT DEFAULT 0,
	kick BIT DEFAULT 0,
	invite BIT DEFAULT 0,
	edit BIT DEFAULT 0,
	FOREIGN KEY fk_alliance (alliance_id) REFERENCES cloc_treaties (id),
	FOREIGN KEY fk_member (nation_id) REFERENCES cloc_login (id) ON DELETE CASCADE
);