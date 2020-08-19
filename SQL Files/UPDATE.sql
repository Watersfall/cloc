USE cloc;

ALTER TABLE cloc_login
	ADD COLUMN last_message BIGINT;

CREATE TABLE messages (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	sender INT,
	receiver INT,
	alliance_message BIT,
	admin_message BIT,
	system_message BIT,
	content TEXT
);

ALTER TABLE events
	DROP COLUMN description;