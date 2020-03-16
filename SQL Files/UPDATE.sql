USE cloc;

ALTER TABLE global_stats_history
	CHANGE week month BIGINT;

ALTER TABLE cloc_main
	CHANGE week month BIGINT;