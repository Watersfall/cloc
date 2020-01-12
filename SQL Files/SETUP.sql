USE cloc;
CREATE TABLE cloc_main(
	turn INT UNSIGNED DEFAULT 0
);

CREATE TABLE production(
	id INT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	factories INT DEFAULT 1,
	efficiency INT DEFAULT 2500,
	production TEXT,
	progress INT DEFAULT 0,
	FOREIGN KEY fk_production (owner) REFERENCES cloc_login(id) ON DELETE CASCADE
);	

CREATE TABLE cloc_login(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(32),
	email TEXT,
	password CHAR(32),
	register_ip VARCHAR(15),
	last_ip VARCHAR(15),
	sess CHAR(32)
);

CREATE TABLE cloc_cosmetic(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nation_name VARCHAR(32),
	username VARCHAR(32),
	nation_title VARCHAR(128) DEFAULT 'Republic',
	leader_title VARCHAR(128) DEFAULT 'President',
	portrait VARCHAR(128) DEFAULT '0.png',
	flag VARCHAR(128) DEFAULT '0.png',
	description TEXT,
	FOREIGN KEY fk_cosmetic (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_economy (
	id INT PRIMARY KEY AUTO_INCREMENT,
	economic TINYINT DEFAULT 50,
	gdp DECIMAL(14, 2) UNSIGNED DEFAULT 300,
	growth DECIMAL(14, 2) DEFAULT 5,
	budget DECIMAL(14, 2) DEFAULT 1000,
	iron DECIMAL(14, 2) UNSIGNED DEFAULT 50,
	coal DECIMAL(14, 2) UNSIGNED DEFAULT 50,
	oil DECIMAL(14, 2) UNSIGNED DEFAULT 25,
	food DECIMAL(14, 2) UNSIGNED DEFAULT 100,
	steel DECIMAL(14, 2) UNSIGNED DEFAULT 0,
	nitrogen DECIMAL(14, 2) UNSIGNED DEFAULT 0,
	research DECIMAL(14, 2) UNSIGNED DEFAULT 0,
	FOREIGN KEY fk_economy (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_domestic(
	id INT PRIMARY KEY AUTO_INCREMENT,
	land INT UNSIGNED DEFAULT 25000,
	government TINYINT UNSIGNED,
	approval TINYINT UNSIGNED DEFAULT 50,
	stability TINYINT UNSIGNED DEFAULT 50,
	population BIGINT UNSIGNED DEFAULT 100000,
	rebels INT UNSIGNED DEFAULT 0,
	lost_manpower LONG DEFAULT 0,
	FOREIGN KEY fk_domestic (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

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
	name VARCHAR(64),
	type ENUM('MINING', 'DRILLING', 'INDUSTRY', 'FARMING'),
	devastation TINYINT DEFAULT 0,
	FOREIGN KEY fk_cities (owner) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_military(
	id INT PRIMARY KEY AUTO_INCREMENT,
	fighters INT UNSIGNED DEFAULT 0,
	zeppelins INT UNSIGNED DEFAULT 0,
	bombers INT UNSIGNED DEFAULT 0,
	submarines INT UNSIGNED DEFAULT 0,
	destroyers INT UNSIGNED DEFAULT 0,
	cruisers INT UNSIGNED DEFAULT 0,
	pre_battleships INT UNSIGNED DEFAULT 0,
	battleships INT UNSIGNED DEFAULT 0,
	transports INT UNSIGNED DEFAULT 0,
	war_protection TINYINT DEFAULT 4,
	FOREIGN KEY fk_military (id) REFERENCES cloc_login(id) ON DELETE CASCADE
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
	fortification INT DEFAULT 0,
	FOREIGN KEY fk_army (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_foreign(
	id INT PRIMARY KEY AUTO_INCREMENT,
	region ENUM('North America', 'South America', 'Africa', 'Middle East', 'Europe', 'Asia', 'Oceania', 'Siberia'),
	alignment TINYINT DEFAULT 1,
	FOREIGN KEY fk_foreign (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_tech(
	id INT PRIMARY KEY AUTO_INCREMENT,
	chem_tech TINYINT UNSIGNED DEFAULT 0,
	advanced_chem_tech TINYINT UNSIGNED DEFAULT 0,
	bomber_tech TINYINT UNSIGNED DEFAULT 0,
	strategic_bombing_tech TINYINT UNSIGNED DEFAULT 0,
	tank_tech TINYINT UNSIGNED DEFAULT 0,
	ship_oil_tech TINYINT UNSIGNED DEFAULT 0,
	bolt_action_manual_tech TINYINT UNSIGNED DEFAULT 0,
	semi_automatic_tech TINYINT UNSIGNED DEFAULT 0,
	machine_gun_tech TINYINT UNSIGNED DEFAULT 0,
	food_tech TINYINT UNSIGNED DEFAULT 0,
	ball_and_powder_tech TINYINT UNSIGNED DEFAULT 0,
	belt_tech TINYINT UNSIGNED DEFAULT 0,
	brass_cartridge_tech TINYINT UNSIGNED DEFAULT 0,
	detachable_magazines_tech TINYINT UNSIGNED DEFAULT 0,
	paper_cartridge_tech TINYINT UNSIGNED DEFAULT 0,
	rifle_clips_tech TINYINT UNSIGNED DEFAULT 0,
	smokeless_powder_tech TINYINT UNSIGNED DEFAULT 0,
	bolt_action_clip_tech TINYINT UNSIGNED DEFAULT 0,
	musket_tech TINYINT UNSIGNED DEFAULT 0,
	needle_nose_rifle_tech TINYINT UNSIGNED DEFAULT 0,
	rifled_musket_tech TINYINT UNSIGNED DEFAULT 0,
	single_shot_rifle_tech TINYINT UNSIGNED DEFAULT 0,
	straight_pull_rifle_tech TINYINT UNSIGNED DEFAULT 0,
	recon_balloon_tech TINYINT UNSIGNED DEFAULT 0,
	recon_plane_tech TINYINT UNSIGNED DEFAULT 0,
	zeppelin_bombers_tech TINYINT UNSIGNED DEFAULT 0,
	biplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	triplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	monoplane_fighter_tech TINYINT UNSIGNED DEFAULT 0,
	FOREIGN KEY fk_tech (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_policy(
	id INT PRIMARY KEY AUTO_INCREMENT,
	manpower_policy TINYINT UNSIGNED DEFAULT 2,
	manpower_change INT DEFAULT 0,
	food_policy TINYINT DEFAULT 1,
	food_change INT DEFAULT 0,
	economy_policy TINYINT DEFAULT 2,
	economy_change INT DEFAULT 0,
	FOREIGN KEY fk_policy (id) REFERENCES cloc_login(id) ON DELETE CASCADE
);

CREATE TABLE cloc_war(
	id INT PRIMARY KEY AUTO_INCREMENT,
	attacker INT,
	defender INT,
	start INT,
	winner INT,
	end INT DEFAULT -1
);

CREATE TABLE cloc_war_logs(
	id INT PRIMARY KEY AUTO_INCREMENT,
	attacker INT,
	region ENUM('North America', 'South America', 'Africa', 'Middle East', 'Europe', 'Asia', 'Oceania', 'Siberia') DEFAULT 'Siberia',
	type ENUM('land', 'navy', 'air', 'chemical', 'transport'),
	amount INT, -- for transports
	FOREIGN KEY fk_logs (attacker) REFERENCES cloc_login (id) ON DELETE CASCADE
);

CREATE TABLE cloc_news(
	id INT PRIMARY KEY AUTO_INCREMENT,
	sender INT,
	receiver INT,
	content TEXT,
	image TEXT DEFAULT NULL,
	time BIGINT DEFAULT 0,
	is_read BIT DEFAULT b'0',
	FOREIGN KEY fk_news (receiver) REFERENCES cloc_login (id) ON DELETE CASCADE
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

CREATE TABLE cloc_treaty_invites(
	id INT PRIMARY KEY AUTO_INCREMENT,
	alliance_id INT,
	nation_id INT,
	FOREIGN KEY fk_alliance (alliance_id) REFERENCES cloc_treaties (id),
	FOREIGN KEY fk_member (nation_id) REFERENCES cloc_login (id) ON DELETE CASCADE
);

CREATE TABLE cloc_declarations(
	id INT PRIMARY KEY AUTO_INCREMENT,
	sender INT,
	sent INT,
	content VARCHAR(2048),
	FOREIGN KEY fk_declarations (sender) REFERENCES cloc_login (id) ON DELETE CASCADE
);

INSERT INTO cloc_main (turn) VALUES (1);