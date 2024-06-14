# binlog가 활성화 되어 있는지 확인

show variables like 'log_bin';

# binlog 가 ROW 로 설정되어 있는지 확인

show variables like 'binlog_format';

# 소스 테이블

```sql
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
```

# 카프카 커넥터 생성

```bash
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "source-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": 1,
    "database.hostname": "mysql",
    "database.port": 3306,
    "database.user": "root",
    "database.password": "root",
    "database.dbname" : "mysql",
    "database.server.id": "970628",
    "database.include.list": "old_db",
    "database.server.name": "mysql",
    "database.history.kafka.bootstrap.servers": "kafka1:29092,kafka2:29093",
    "database.history.kafka.topic": "mysql.database.history",
    "topic.prefix": "mysql",
    "table.include.list": "old_db.PAYMENT",
    "schema.history.internal.kafka.bootstrap.servers": "kafka1:29092,kafka2:29093",
    "schema.history.internal.kafka.topic": "mysql.schema",
    "key.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "decimal.handling.mode":"double",
    "snapshot.lock.mode":"none",
    "transforms": "unwrap",
    "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",
    "transforms.unwrap.drop.tombstones": "false"
  }
}'
```

