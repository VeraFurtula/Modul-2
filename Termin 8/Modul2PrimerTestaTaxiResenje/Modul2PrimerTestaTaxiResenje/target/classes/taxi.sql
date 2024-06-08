DROP SCHEMA IF EXISTS taxi;
CREATE SCHEMA taxi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE taxi;

CREATE TABLE vozila (
	id BIGINT AUTO_INCREMENT,
	broj VARCHAR(75) UNIQUE NOT NULL,
	vozac VARCHAR(75) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE pozivi (
	id BIGINT AUTO_INCREMENT,
	datumIVreme DATETIME NOT NULL,
	ulica VARCHAR(100) NOT NULL,
	broj INT NOT NULL,
    voziloId BIGINT NOT NULL,
	PRIMARY KEY(id), 
    FOREIGN KEY(voziloId) REFERENCES vozila(id)
);

INSERT INTO vozila (id, broj, vozac) VALUES (1, '101', 'Petar Peric');
INSERT INTO vozila (id, broj, vozac) VALUES (2, '102', 'Sinisa Simic');
INSERT INTO vozila (id, broj, vozac) VALUES (3, '103', 'Jovan Jovanovic');

INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-01 10:00', 'Bul. Oslobođenja', 10, 1);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-01 14:00', 'Bul. Mihajla Pupina', 10, 1);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-01 20:00', 'Bul. Oslobođenja', 20, 2);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-02 10:30', 'Bul. Cara Lazara', 10, 1);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-02 14:30', 'Bul. Oslobođenja', 30, 3);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-03 10:00', 'Bul. Mihajla Pupina', 20, 2);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-03 14:00', 'Bul. Mihajla Pupina', 30, 3);
INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES ('2021-09-03 20:00', 'Bul. Cara Lazara', 20, 1);
