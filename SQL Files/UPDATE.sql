ALTER TABLE anti_spam
	MODIFY COLUMN action ENUM ('SEND_RESOURCE', 'SEND_DECLARATION', 'SEND_INVITE', 'UPDATE_FLAG', 'UPDATE_PORTRAIT', 'UPDATE_ALLIANCE_FLAG', 'MESSAGE');

ALTER TABLE nation_stats
    ADD COLUMN max_fighters INT DEFAULT -1,
	ADD COLUMN max_bombers INT DEFAULT -1,
	ADD COLUMN max_recon INT DEFAULT -1;

ALTER TABLE nation_stats
	ADD COLUMN current_fighters INT DEFAULT 0,
    ADD COLUMN current_bombers INT DEFAULT 0,
    ADD COLUMN current_recon INT DEFAULT 0;

ALTER TABLE cities
	ADD COLUMN garrison_level ENUM('DECREASED', 'NORMAL', 'INCREASED') DEFAULT 'NORMAL';

CREATE TABLE armies (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	owner INT,
	name TEXT,
	training INT,
	experience INT,
	specialization_type ENUM('NONE', 'AMPHIBIOUS', 'URBAN'),
	specialization_amount INT,
	location ENUM('NATION', 'CITY'),
	location_id BIGINT,
	priority ENUM('HIGH', 'NORMAL', 'LOW'),
	FOREIGN KEY fk_owner (owner) REFERENCES login (id) ON DELETE CASCADE
);

CREATE TABLE army_battalions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    owner BIGINT,
	size INT,
	type ENUM('INFANTRY', 'ARTILLERY', 'ARMORED'),
	FOREIGN KEY fk_owner (owner) REFERENCES armies (id) ON DELETE CASCADE
);

CREATE TABLE army_equipment (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	owner BIGINT,
	type ENUM('MUSKET', 'RIFLED_MUSKET', 'SINGLE_SHOT', 'NEEDLE_NOSE', 'BOLT_ACTION_MANUAL', 'BOLT_ACTION_CLIP', 'STRAIGHT_PULL', 'SEMI_AUTO', 'MACHINE_GUN', 'ARTILLERY', 'TANK', 'BIPLANE_FIGHTERS', 'TRIPLANE_FIGHTERS', 'MONOPLANE_FIGHTERS', 'RECON_BALLOONS', 'RECON_PLANES', 'ZEPPELINS', 'BOMBERS'),
	amount INT,
	FOREIGN KEY fk_owner (owner) REFERENCES army_battalions (id) ON DELETE CASCADE
);

ALTER TABLE nation_stats
	DROP COLUMN army_training,
    DROP COLUMN army_size;