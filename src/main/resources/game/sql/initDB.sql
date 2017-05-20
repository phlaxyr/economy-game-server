-- Make the USERS table
CREATE TABLE `USERS` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`username`	VARCHAR(100) NOT NULL UNIQUE,
	`passhash`	VARCHAR(100) NOT NULL,
	`timeregistered`	BIGINT NOT NULL,
	`balance`	BIGINT NOT NULL
);

-- Make the WORLD table
CREATE TABLE `WORLD` (
	`coordx`	INTEGER NOT NULL,
	`coordz`	INTEGER NOT NULL,
	`tiletype`	INTEGER NOT NULL,
	`tileproperties`	TEXT,
	PRIMARY KEY(`coordx`,`coordz`)
);

-- Username index on USERS
CREATE UNIQUE INDEX `UK_USERS_USERNAME` ON `USERS` (
	`username`
);

