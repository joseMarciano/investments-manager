CREATE TABLE EXECUTIONS(
	ID VARCHAR(32) PRIMARY KEY NOT NULL,
	I_ORIGINS VARCHAR(32),
	I_STOCKS VARCHAR(32) NOT NULL,
	I_WALLETS VARCHAR(32) NOT NULL,
	PROFIT_PERCENTAGE DECIMAL(10,5) NOT NULL,
	BUY_EXECUTED_QUANTITY INTEGER,
	BUY_EXECUTED_PRICE DECIMAL(10,5),
	BUY_EXECUTED_VOLUME DECIMAL(10,5),
	SELL_EXECUTED_QUANTITY INTEGER,
	SELL_EXECUTED_PRICE DECIMAL(10,5),
	SELL_EXECUTED_VOLUME DECIMAL(10,5),
	STATUS VARCHAR(50),
	BOUGHT_AT TIMESTAMP(6),
	SOLD_AT TIMESTAMP(6),
	CREATED_AT TIMESTAMP(6),
	UPDATED_AT TIMESTAMP(6),
	CONSTRAINT FK_ORIGINS FOREIGN KEY(I_ORIGINS) REFERENCES EXECUTIONS(ID),
	CONSTRAINT FK_STOCKS FOREIGN KEY(I_STOCKS) REFERENCES STOCKS(ID),
	CONSTRAINT FK_WALLETS FOREIGN KEY(I_WALLETS) REFERENCES WALLETS(ID)
);

