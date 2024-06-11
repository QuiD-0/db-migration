# binlog가 활성화 되어 있는지 확인

show variables like 'log_bin';

# binlog 가 row_level로 설정되어 있는지 확인

show variables like 'binlog_format';

# 소스 테이블

create table PAYMENT
(
ID bigint auto_increment primary key,
TRANSACTION_KEY varchar(255)   not null,
REFERENCE_KEY varchar(255)   not null,
STATUS varchar(255)   not null,
AMOUNT decimal(19, 2) not null,
CURRENCY varchar(255)   not null,
REG_DATE datetime not null,
MOD_DATE datetime not null,
RESPONSE_JSON text
);

# 카프카 커넥터 생성

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
"name": "source-connector",
"config": {
"connector.class": "io.debezium.connector.mysql.MySqlConnector",
"database.hostname": "mysql",
"database.port": "3306",
"database.user": "user",
"database.password": "user",
"database.server.id": "184054",
"database.dbname": "old_db",
"database.server.name": "dbserver",
"table.include.list": "old_db.PAYMENT",
"topic.prefix": "dbserver"
}
}'