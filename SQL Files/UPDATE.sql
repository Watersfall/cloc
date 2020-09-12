USE cloc;

ALTER TABLE cloc_foreign
	MODIFY COLUMN alignment ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL') DEFAULT 'NEUTRAL';

CREATE TABLE alignments (
	id ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL') PRIMARY KEY
);

INSERT INTO alignments(id) VALUES ('ENTENTE');
INSERT INTO alignments(id) VALUES ('CENTRAL_POWERS');
INSERT INTO alignments(id) VALUES ('NEUTRAL');

CREATE TABLE alignments_equipment (
    alignment ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL'),
    equipment TEXT,
    amount BIGINT,
    FOREIGN KEY (alignment) REFERENCES alignments (id)
);

ALTER TABLE cloc_foreign
	ADD COLUMN entente_reputation INT DEFAULT 0,
    ADD COLUMN central_powers_reputation INT DEFAULT 0;

INSERT INTO alignments_equipment (alignment, equipment, amount) VALUES ('CENTRAL_POWERS', 'BOLT_ACTION_MANUAL', 100000);
INSERT INTO alignments_equipment (alignment, equipment, amount) VALUES ('CENTRAL_POWERS', 'TRIPLANE_FIGHTERS', 500);
INSERT INTO alignments_equipment (alignment, equipment, amount) VALUES ('ENTENTE', 'BOLT_ACTION_MANUAL', 100000);
INSERT INTO alignments_equipment (alignment, equipment, amount) VALUES ('ENTENTE', 'BIPLANE_FIGHTERS', 500);

CREATE TABLE alignments_transactions (
    alignment ENUM('ENTENTE', 'CENTRAL_POWERS', 'NEUTRAL'),
    nation INT,
    equipment TEXT,
    amount BIGINT,
    FOREIGN KEY (alignment) REFERENCES alignments (id),
    FOREIGN KEY (nation) REFERENCES cloc_login (id) ON DELETE CASCADE
);

ALTER TABLE alignments_transactions
	ADD COLUMN month BIGINT;