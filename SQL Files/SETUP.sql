CREATE DATABASE IF NOT EXISTS cloc;

USE cloc;

CREATE TABLE login(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(32),
	email TEXT,
	password CHAR(60),
	register_ip VARCHAR(15),
	last_ip VARCHAR(15)
);

CREATE TABLE nation_cosmetic(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nation_name VARCHAR(32),
	username VARCHAR(32),
	nation_title VARCHAR(128) DEFAULT 'Republic',
	leader_title VARCHAR(128) DEFAULT 'President',
	portrait VARCHAR(128) DEFAULT '0.png',
	flag VARCHAR(128) DEFAULT '0.png',
	description TEXT,
	FOREIGN KEY fk_cosmetic (id) REFERENCES login(id) ON DELETE CASCADE
);

CREATE TABLE main(
	month BIGINT UNSIGNED DEFAULT 1,
	day BIGINT UNSIGNED DEFAULT 1
);

CREATE TABLE nation_stats(
	id INT PRIMARY KEY AUTO_INCREMENT,
	last_message BIGINT,
	last_news BIGINT,
	last_login BIGINT,
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
	recent_conscription INT DEFAULT 0,
	recent_deconscription INT DEFAULT 0,
	land INT UNSIGNED DEFAULT 25000,
	government TINYINT UNSIGNED,
	approval TINYINT UNSIGNED DEFAULT 50,
	stability TINYINT UNSIGNED DEFAULT 50,
	rebels INT UNSIGNED DEFAULT 0,
	lost_manpower BIGINT DEFAULT 0,
	months_in_famine INT DEFAULT 0,
	war_protection TINYINT DEFAULT 4,
	army_size INT DEFAULT 20,
	army_training INT DEFAULT 50,
	fortification INT DEFAULT 0,
	casualties BIGINT DEFAULT 0,
	region ENUM('NORTH_AMERICA', 'SOUTH_AMERICA', 'AFRICA', 'MIDDLE_EAST', 'EUROPE', 'ASIA', 'OCEANIA', 'SIBERIA'),
	alignment ENUM('NEUTRAL', 'ENTENTE', 'CENTRAL_POWERS') DEFAULT 'NEUTRAL',
	entente_reputation INT DEFAULT 0,
	central_powers_reputation INT DEFAULT 0,
	max_fighters INT DEFAULT -1,
	max_bombers INT DEFAULT -1,
	max_recon INT DEFAULT -1,
	current_fighters INT DEFAULT 0,
	current_bombers INT DEFAULT 0,
	current_recon INT DEFAULT 0,
	FOREIGN KEY fk_stats (id) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE nation_policy (
    id INT PRIMARY KEY AUTO_INCREMENT,
	manpower_policy ENUM('DISARMED_MANPOWER', 'VOLUNTEER_MANPOWER', 'RECRUITMENT_MANPOWER', 'MANDATORY_MANPOWER', 'SCRAPING_THE_BARREL_MANPOWER') DEFAULT 'VOLUNTEER_MANPOWER',
	food_policy ENUM('RATIONING_FOOD', 'NORMAL_FOOD', 'FREE_FOOD') DEFAULT 'NORMAL_FOOD',
	economy_policy ENUM('CIVILIAN_ECONOMY', 'EXTRACTION_ECONOMY', 'INDUSTRY_ECONOMY', 'AGRARIAN_ECONOMY', 'WAR_ECONOMY') DEFAULT 'CIVILIAN_ECONOMY',
	fortification_policy ENUM('UNOCCUPIED_FORTIFICATION', 'MINIMAL_FUNDING_FORTIFICATION', 'PARTIAL_FUNDING_FORTIFICATION', 'FULL_FUNDING_FORTIFICATION', 'MAX_FORTIFICATION') DEFAULT 'PARTIAL_FUNDING_FORTIFICATION',
	farming_subsidies ENUM ('NO_SUBSIDIES_FARMING', 'REDUCED_SUBSIDIES_FARMING', 'STANDARD_SUBSIDIES_FARMING', 'SUBSTANTIAL_SUBSIDIES_FARMING') DEFAULT 'NO_SUBSIDIES_FARMING',
	manpower_change BIGINT DEFAULT 0,
	food_change BIGINT DEFAULT 0,
	economy_change BIGINT DEFAULT 0,
	fortification_change BIGINT DEFAULT 0,
	farming_subsidies_change BIGINT DEFAULT 0,
	FOREIGN KEY fk_policy (id) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE nation_tech (
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
	musket_tech TINYINT UNSIGNED DEFAULT 1,
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
	artillery_tech TINYINT UNSIGNED DEFAULT 0,
	basic_trenches_tech TINYINT UNSIGNED DEFAULT 0,
	advanced_trenches_tech TINYINT UNSIGNED DEFAULT 0,
	basic_fortifications_tech TINYINT UNSIGNED DEFAULT 0,
	reinforced_concrete_tech TINYINT UNSIGNED DEFAULT 0,
	mobile_defense_tech TINYINT UNSIGNED DEFAULT 0,
	basic_artificial_fertilizer TINYINT UNSIGNED DEFAULT 0,
	artificial_fertilizer TINYINT UNSIGNED DEFAULT 0,
	advanced_artificial_fertilizer TINYINT UNSIGNED DEFAULT 0,
	farming_machines TINYINT UNSIGNED DEFAULT 0,
	advanced_farming_machines TINYINT UNSIGNED DEFAULT 0,
	FOREIGN KEY fk_producibles (id) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE nation_producibles (
	id INT PRIMARY KEY AUTO_INCREMENT,
	recon_balloons INT UNSIGNED DEFAULT 0,
	recon_planes INT UNSIGNED DEFAULT 0,
	biplane_fighters INT UNSIGNED DEFAULT 0,
	triplane_fighters INT UNSIGNED DEFAULT 0,
	monoplane_fighters INT UNSIGNED DEFAULT 0,
	zeppelins INT UNSIGNED DEFAULT 0,
	bombers INT UNSIGNED DEFAULT 0,
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
	tank INT DEFAULT 0,
	FOREIGN KEY fk_producibles (id) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE cities(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	capital BIT,
	coastal BIT,
	railroads INT DEFAULT 1,
	ports INT DEFAULT 0,
	barracks INT DEFAULT 0,
	iron_mines INT UNSIGNED DEFAULT 1,
	coal_mines INT UNSIGNED DEFAULT 1,
	oil_wells INT UNSIGNED DEFAULT 1,
	civilian_industry INT UNSIGNED DEFAULT 1,
	nitrogen_industry INT UNSIGNED DEFAULT 0,
	universities INT UNSIGNED DEFAULT 0,
	name VARCHAR(64),
	type ENUM('MINING', 'DRILLING', 'INDUSTRY', 'FARMING') DEFAULT 'FARMING',
	devastation TINYINT DEFAULT 0,
	population BIGINT DEFAULT 100000,
	FOREIGN KEY fk_cities (owner) REFERENCES login(id) ON DELETE CASCADE
);

CREATE TABLE wars (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	attacker INT,
	defender INT,
	start BIGINT,
	end BIGINT DEFAULT -1,
	winner INT,
	peace INT default -1,
	name TEXT,
	FOREIGN KEY fk_attacker (attacker) REFERENCES login(id) ON DELETE CASCADE,
	FOREIGN KEY fk_defender (defender) REFERENCES login(id) ON DELETE CASCADE
);

CREATE TABLE war_logs(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	attacker INT,
	type ENUM('LAND', 'NAVY', 'AIR'),
	FOREIGN KEY fk_logs (attacker) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE production(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	production TEXT,
	progress INT DEFAULT 0,
	FOREIGN KEY fk_production (owner) REFERENCES login(id) ON DELETE CASCADE
);

CREATE TABLE news(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	sender INT,
	receiver INT,
	content TEXT,
	time BIGINT DEFAULT 0,
	FOREIGN KEY fk_news (receiver) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE treaties(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(32),
	flag VARCHAR(32) DEFAULT '',
	description TEXT
);

CREATE TABLE treaty_members(
	alliance_id INT,
	nation_id INT,
	founder BIT DEFAULT 0,
	manage BIT DEFAULT 0,
	kick BIT DEFAULT 0,
	invite BIT DEFAULT 0,
	edit BIT DEFAULT 0,
	FOREIGN KEY fk_alliance (alliance_id) REFERENCES treaties (id),
	FOREIGN KEY fk_member (nation_id) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE treaty_invites(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	alliance_id INT,
	nation_id INT,
	FOREIGN KEY fk_alliance (alliance_id) REFERENCES treaties (id),
	FOREIGN KEY fk_member (nation_id) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE declarations(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	sender INT,
	sent BIGINT,
	content VARCHAR(2048),
	FOREIGN KEY fk_declarations (sender) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE factories(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	city_id BIGINT,
	production_id BIGINT,
	efficiency INT DEFAULT 1500,
	FOREIGN KEY fk_production (production_id) REFERENCES production (id) ON DELETE SET NULL,
	FOREIGN KEY fk_owner (owner) REFERENCES login(id) ON DELETE CASCADE,
	FOREIGN KEY fk_city (city_id) REFERENCES cities(id) ON DELETE CASCADE
);

CREATE TABLE global_stats_history(
	month BIGINT,
	total_nations BIGINT,
	total_neutral_nations BIGINT,
	total_entente_nations BIGINT,
	total_central_powers_nations BIGINT,
	total_population BIGINT,
	total_soldiers BIGINT,
	total_civilian_factories BIGINT,
	total_military_factories BIGINT,
	total_universities BIGINT,
	total_iron_mines BIGINT,
	total_coal_mines BIGINT,
	total_oil_wells BIGINT
);

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
	FOREIGN KEY fk_nation (nation_id) REFERENCES login (id)
);

CREATE TABLE alignments (
	id ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL') PRIMARY KEY
);

CREATE TABLE alignments_equipment (
	alignment ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL'),
	equipment TEXT,
	amount BIGINT,
	FOREIGN KEY (alignment) REFERENCES alignments (id)
);

CREATE TABLE alignments_transactions (
	alignment ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL'),
	nation INT,
	equipment TEXT,
	amount BIGINT,
	month BIGINT,
	FOREIGN KEY (alignment) REFERENCES alignments (id),
	FOREIGN KEY (nation) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE anti_spam (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	user INT,
	action ENUM ('SEND_RESOURCE', 'SEND_DECLARATION', 'SEND_INVITE', 'UPDATE_FLAG', 'UPDATE_PORTRAIT', 'UPDATE_ALLIANCE_FLAG', 'MESSAGE'),
	time BIGINT,
	FOREIGN KEY (user) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE events (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	event_id ENUM('STRIKE'),
	month BIGINT,
	city_id BIGINT DEFAULT NULL,
	FOREIGN KEY (owner) REFERENCES login (id) ON DELETE CASCADE,
	FOREIGN KEY (city_id) REFERENCES cities (id) ON DELETE CASCADE
);

CREATE TABLE messages (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	sender INT,
	receiver INT,
	alliance_message BOOLEAN,
	admin_message BOOLEAN,
	system_message BOOLEAN,
	content TEXT,
	FOREIGN KEY fk_receiver (receiver) REFERENCES login (id) ON DELETE NO ACTION,
	FOREIGN KEY fk_sender (sender) REFERENCES login (id) ON DELETE NO ACTION
);

CREATE TABLE modifiers (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	user INT,
	city BIGINT,
	type TEXT,
	start BIGINT,
	FOREIGN KEY (user) REFERENCES login (id) ON DELETE CASCADE,
	FOREIGN KEY (city) REFERENCES cities (id) ON DELETE CASCADE
);

INSERT INTO alignments(id) VALUES ('ENTENTE');
INSERT INTO alignments(id) VALUES ('CENTRAL_POWERS');
INSERT INTO alignments(id) VALUES ('NEUTRAL');

INSERT INTO main (month, day) VALUES (1, 1);