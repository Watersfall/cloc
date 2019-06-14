USE cloc;

CREATE TABLE cloc_main(
	turn INT UNSIGNED DEFAULT 0
);

CREATE TABLE cloc_login(
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32),
    password CHAR(32),
    sess CHAR(32)
);

CREATE TABLE cloc_cosmetic(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nation_name VARCHAR(32),
    username VARCHAR(32),
    nation_title VARCHAR(128) DEFAULT 'Republic',
    leader_title VARCHAR(128) DEFAULT 'President',
    portrait VARCHAR(128) DEFAULT 'JUWOEe0.png',
    flag VARCHAR(128) DEFAULT 'X3zWAyF.png',
    description TEXT
);

CREATE TABLE cloc_economy (
	id INT PRIMARY KEY AUTO_INCREMENT,
    gdp DECIMAL(14, 2) UNSIGNED DEFAULT 300,
    growth DECIMAL(14, 2) DEFAULT 5,
    budget DECIMAL(14, 2) DEFAULT 1000, 
    iron DECIMAL(14, 2) UNSIGNED DEFAULT 50,
    coal DECIMAL(14, 2) UNSIGNED DEFAULT 50,
    oil DECIMAL(14, 2) UNSIGNED DEFAULT 25,
    food DECIMAL(14, 2) UNSIGNED DEFAULT 100,
    manufactured DECIMAL(14, 2) UNSIGNED DEFAULT 0,
    research DECIMAL(14, 2) UNSIGNED DEFAULT 0,  
    iron_mines INT UNSIGNED DEFAULT 2,
    coal_mines INT UNSIGNED DEFAULT 2,
    oil_wells INT UNSIGNED DEFAULT 0,
    civilian_industry INT UNSIGNED DEFAULT 0,
    military_industry INT UNSIGNED DEFAULT 0,
    universities INT UNSIGNED DEFAULT 0
);

CREATE TABLE cloc_domestic(
	id INT PRIMARY KEY AUTO_INCREMENT,
	land INT UNSIGNED DEFAULT 20000,
    government TINYINT UNSIGNED,
    approval TINYINT UNSIGNED DEFAULT 50,
    stability TINYINT UNSIGNED DEFAULT 50,
    population BIGINT UNSIGNED DEFAULT 100000,
    rebels INT UNSIGNED DEFAULT 0
);

CREATE TABLE cloc_military(
	id INT PRIMARY KEY AUTO_INCREMENT,
	fighters INT UNSIGNED DEFAULT 0,
    zeppelins INT UNSIGNED DEFAULT 0,
    submarines INT UNSIGNED DEFAULT 0,
    destroyers INT UNSIGNED DEFAULT 0,
    cruisers INT UNSIGNED DEFAULT 0,
    battleships INT UNSIGNED DEFAULT 0,
    transports INT UNSIGNED DEFAULT 0,
    army_home INT UNSIGNED DEFAULT 25,
    training_home INT UNSIGNED DEFAULT 50,
    weapons_home INT UNSIGNED DEFAULT 25000,
    war_protection TINYINT DEFAULT 4
);

CREATE TABLE cloc_armies(
	id INT PRIMARY KEY AUTO_INCREMENT,
    owner INT,
    region ENUM('North America', 'South America', 'Africa', 'Middle East', 'Europe', 'Asia', 'Oceania', 'Siberia'),
    army INT UNSIGNED,
    training INT UNSIGNED,
    weapons INT UNSIGNED
);

CREATE TABLE cloc_foreign(
	id INT PRIMARY KEY AUTO_INCREMENT,
	region ENUM('North America', 'South America', 'Africa', 'Middle East', 'Europe', 'Asia', 'Oceania', 'Siberia'),
    alliance INT UNSIGNED DEFAULT 0
);

CREATE TABLE cloc_tech(
	id INT PRIMARY KEY AUTO_INCREMENT,
	chemical_weapons_tech TINYINT UNSIGNED DEFAULT 0,
    tanks_tech TINYINT UNSIGNED DEFAULT 0,
    submarine_tech TINYINT UNSIGNED DEFAULT 0,
    logistics_tech TINYINT UNSIGNED DEFAULT 0,
    offensive_tech TINYINT UNSIGNED DEFAULT 0,
    defensive_tech TINYINT UNSIGNED DEFAULT 0,
    food_tech TINYINT UNSIGNED DEFAULT 0
);

CREATE TABLE cloc_policy(
	id INT PRIMARY KEY AUTO_INCREMENT,
    manpower TINYINT UNSIGNED DEFAULT 10,
    manpower_change INT DEFAULT 0
);

CREATE TABLE cloc_war(
	id INT PRIMARY KEY AUTO_INCREMENT,
    attacker INT,
    defender INT,
    start INT,
    end INT DEFAULT -1
);

CREATE TABLE cloc_war_logs(
	id INT PRIMARY KEY AUTO_INCREMENT,
    attacker INT,
    region ENUM('North America', 'South America', 'Africa', 'Middle East', 'Europe', 'Asia', 'Oceania', 'Siberia') DEFAULT 'Siberia',
    type ENUM('land', 'navy', 'air', 'chemical', 'transport'),
    amount INT -- for transports
);

CREATE TABLE cloc_news(
	id INT PRIMARY KEY AUTO_INCREMENT,
    sender INT,
    reciever INT,
    content TEXT,
    image TEXT DEFAULT NULL
);