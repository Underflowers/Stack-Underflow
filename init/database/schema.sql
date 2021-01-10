-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema stackunderflow
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `stackunderflow` ;

-- -----------------------------------------------------
-- Schema stackunderflow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `stackunderflow` DEFAULT CHARACTER SET utf8 ;
USE `stackunderflow` ;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

CREATE TABLE IF NOT EXISTS `users` (
  `uuid` VARCHAR(255) NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `questions` ;

CREATE TABLE IF NOT EXISTS `questions` (
  `uuid` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `users_uuid` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_questions_users_idx` (`users_uuid` ASC) VISIBLE,
  CONSTRAINT `fk_questions_users`
    FOREIGN KEY (`users_uuid`)
    REFERENCES `users` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `answers` ;

CREATE TABLE IF NOT EXISTS `answers` (
  `uuid` VARCHAR(255) NOT NULL,
  `users_uuid` VARCHAR(255) NOT NULL,
  `questions_uuid` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_answers_users1_idx` (`users_uuid` ASC) VISIBLE,
  INDEX `fk_answers_questions1_idx` (`questions_uuid` ASC) VISIBLE,
  CONSTRAINT `fk_answers_users1`
    FOREIGN KEY (`users_uuid`)
    REFERENCES `users` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answers_questions1`
    FOREIGN KEY (`questions_uuid`)
    REFERENCES `questions` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `votes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `votes` ;

CREATE TABLE IF NOT EXISTS `votes` (
  `uuid` VARCHAR(255) NOT NULL,
  `users_uuid` VARCHAR(255) NOT NULL,
  `questions_uuid` VARCHAR(255) NULL,
  `answers_uuid` VARCHAR(255) NULL,
  `isUpvote` TINYINT NOT NULL,
  INDEX `fk_users_has_questions_questions1_idx` (`questions_uuid` ASC) VISIBLE,
  INDEX `fk_users_has_questions_users1_idx` (`users_uuid` ASC) VISIBLE,
  INDEX `fk_votes_answers1_idx` (`answers_uuid` ASC) VISIBLE,
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_users_has_questions_users1`
    FOREIGN KEY (`users_uuid`)
    REFERENCES `users` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_questions_questions1`
    FOREIGN KEY (`questions_uuid`)
    REFERENCES `questions` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_votes_answers1`
    FOREIGN KEY (`answers_uuid`)
    REFERENCES `answers` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comments` ;

CREATE TABLE IF NOT EXISTS `comments` (
  `uuid` VARCHAR(255) NOT NULL,
  `answers_uuid` VARCHAR(255) NULL,
  `questions_uuid` VARCHAR(255) NULL,
  `users_uuid` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_comments_answers1_idx` (`answers_uuid` ASC) VISIBLE,
  INDEX `fk_comments_questions1_idx` (`questions_uuid` ASC) VISIBLE,
  INDEX `fk_comments_users1_idx` (`users_uuid` ASC) VISIBLE,
  CONSTRAINT `fk_comments_answers1`
    FOREIGN KEY (`answers_uuid`)
    REFERENCES `answers` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_questions1`
    FOREIGN KEY (`questions_uuid`)
    REFERENCES `questions` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_users1`
    FOREIGN KEY (`users_uuid`)
    REFERENCES `users` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
