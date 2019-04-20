CREATE TABLE BodyStyles (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, BodyStyle TEXT NOT NULL CONSTRAINT BodyStyleCK CHECK (BodyStyle NOT LIKE '%[0-9!-,.-~]%'));