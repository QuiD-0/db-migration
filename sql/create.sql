create table PAYMENT
(
    'ID'              bigint auto_increment
        primary key,
    'TRANSACTION_KEY' varchar(255)   not null,
    'REFERENCE_KEY'   varchar(255)   not null,
    'STATUS'          varchar(255)   not null,
    'AMOUNT'          decimal(19, 2) not null,
    'CURRENCY'        varchar(255)   not null,
    'REG_DATE'        datetime       not null,
    'MOD_DATE'        datetime       not null
);

