CREATE TABLE bank(  ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        AMOUNT DECIMAL(11,2) NOT NULL,
                        CUSTOMER_ID VARCHAR(11) NOT NULL,
                        OPERATION VARCHAR(11) NOT NULL,
                        DATE_TRANSACTION DATE NOT NULL
                    );