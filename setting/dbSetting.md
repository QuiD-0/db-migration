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
"tasks.max": 1,
"database.hostname": "mysql",
"database.port": 3306,
"database.user": "local",
"database.password": "local",
"database.dbname" : "mysql",
"database.server.id": "184054",
"topic.prefix": "dbserver1",
"schema.include.list": "old_db",
"topic.creation.default.replication.factor": 1,
"topic.creation.default.partitions": 5,
"topic.creation.default.cleanup.policy": "compact",
"topic.creation.default.compression.type": "gzip",
"topic.creation.default.delete.retention.ms" : 2592000000,
"topic.creation.groups": "productlog",
"topic.creation.productlog.include": "dbserver1\\.old_db\\.payment",
"topic.creation.productlog.replication.factor": 1,
"topic.creation.productlog.partitions": 10,
"topic.creation.productlog.cleanup.policy": "delete",
"topic.creation.productlog.compression.type": "producer",
"topic.creation.productlog.retention.ms": 7776000000,
"schema.history.internal.kafka.bootstrap.servers": "kafka1:29092,kafka2:29093",
"schema.history.internal.kafka.topic": "dbhistory.schema"
}
}'

