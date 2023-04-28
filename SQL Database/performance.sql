DROP TABLE IF EXISTS theTheatreRoyal.performance;
CREATE TABLE IF NOT EXISTS `theTheatreRoyal`.`performance` (
  `showID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NULL,
  `type` VARCHAR(45) NULL,
  `description` VARCHAR(260) NULL,
  `length` INT NULL,
  `time` VARCHAR(45) NULL,
  `date` DATE NULL,
  `stallPrice` DECIMAL NULL,
  `circlePrice` DECIMAL NULL,
  `language` VARCHAR(45) NULL,
  `liveMusic` VARCHAR(45) NULL,
  `stallSeat` int,
  `circleSeat` int,
  
  PRIMARY KEY (`showID`))
  AUTO_INCREMENT = 10050
ENGINE = InnoDB