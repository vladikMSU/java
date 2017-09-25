DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS exam;
DROP TABLE IF EXISTS student;

CREATE TABLE `student` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(100) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `exam` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(100) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `tasks` (
	`student` int NOT NULL,
	`exam` int NOT NULL,
	`points` int NOT NULL
);

ALTER TABLE `tasks` ADD CONSTRAINT `tasks_fk0` FOREIGN KEY (`student`) REFERENCES `student`(`id`);

ALTER TABLE `tasks` ADD CONSTRAINT `tasks_fk1` FOREIGN KEY (`exam`) REFERENCES `exam`(`id`);

ALTER TABLE `tasks` ADD CONSTRAINT `PK_Person` PRIMARY KEY (`student`, `exam`);
