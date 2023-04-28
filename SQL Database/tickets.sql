USE thetheatreroyal;

DROP TABLE IF EXISTS tickets;
CREATE TABLE tickets 
(
TicketID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
TicketNumber VARCHAR(100),
TitleOfShow VARCHAR(200),
ShowID VARCHAR(20),
TypeOfShow VARCHAR(20),
isCircleSeat boolean,
price float(20),
isDiscount boolean,
isSold boolean,
BookingID int,
byPost boolean

)
AUTO_INCREMENT = 10050;