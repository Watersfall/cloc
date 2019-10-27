USE cloc;

ALTER TABLE cloc_news
	ADD time BIGINT default 0,
	ADD is_read BIT DEFAULT b'0';