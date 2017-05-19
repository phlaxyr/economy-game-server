-- Makes the main users table
CREATE TABLE `USERS` (
	`username`	VARCHAR(100) NOT NULL UNIQUE,
	`passhash`	VARCHAR(100) NOT NULL,
	`timeregistered`	BIGINT NOT NULL,
	`balance`	BIGINT NOT NULL,
	PRIMARY KEY(`username`)
);
