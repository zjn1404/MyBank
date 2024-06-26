CREATE TABLE IF NOT EXISTS TRANSACTIONS (
    ID          UUID default random_uuid() PRIMARY KEY,
    AMOUNT      INT,
    TIME_STAMP  TIMESTAMP,
    REFERENCE   VARCHAR(255),
    SLOGAN      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS USERS (
    ID      VARCHAR(255) PRIMARY KEY,
    NAME    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS TRANSACTIONS_USERS (
    TRANSACTION_ID  UUID PRIMARY KEY,
    USER_ID         VARCHAR(255)
);

ALTER TABLE TRANSACTIONS_USERS
ADD FOREIGN KEY (TRANSACTION_ID)
REFERENCES TRANSACTIONS(ID);

ALTER TABLE TRANSACTIONS_USERS
ADD FOREIGN KEY (USER_ID)
REFERENCES USERS(ID);