# 사전준비

## binlog가 활성화 되어 있는지 확인

show variables like 'log_bin';

## binlog 가 ROW 로 설정되어 있는지 확인

show variables like 'binlog_format';

# 두 DB의 스키마가 다를경우 어플리케이션에서 처리하는 방법

## 소스 테이블 (mysql)

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

## 싱크 테이블 (postgres)

```sql
create table DEAD_LETTER
(
    DEAD_LETTER_SEQ BIGSERIAL
        constraint DEAD_LETTER_pk
            primary key,
    MESSAGE         text
);

create table PAYMENT
(
    ID             BIGSERIAL
        constraint PAYMENT_pk
            primary key,
    TRANSACTION_KEY varchar(255)   not null,
    REFERENCE_KEY   varchar(255)   not null,
    STATUS          varchar(255)   not null,
    AMOUNT          decimal(19, 2) not null,
    CURRENCY        varchar(255)   not null,
    REG_DATE        timestamp      not null,
    MOD_DATE        timestamp      not null
);

create table PAYMENT_RESPONSE
(
    ID             BIGSERIAL
        constraint PAYMENT_RESPONSE_pk
            primary key,
    TRANSACTION_KEY varchar(255)   not null,
    REFERENCE_KEY   varchar(255)   not null,
    STATUS          varchar(255)   not null,
    PAY_DATE        timestamp      not null
);


```

## 카프카 커넥터 생성

```bash
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "pay-source-connector",
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

# 두 DB의 스키마가 같은경우 sink-connector를 사용해서 처리하는 방법 


## 소스 테이블 (mysql)

```sql
create table MEMBER (
    MEMBER_SEQ bigint not null auto_increment,
    MEMBER_ID varchar(255) not null,
    PASSWORD varchar(255) not null,
    NAME varchar(255) not null,
    LEVEL varchar(255) not null,
    REG_DATE datetime not null,
    MOD_DATE datetime not null,
    primary key (MEMBER_SEQ)
);
```

## 싱크 테이블 (postgres)

-> 자동 생성됨

## source-connector 설정 변경

```bash
curl -i -X PUT -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/source-connectors/config -d '{
  "connector.class": "io.debezium.connector.mysql.MySqlConnector",
  "tasks.max": 1,
  "database.hostname": "mysql",
  "database.port": 3306,
  "database.user": "root",
  "database.password": "root",
  "database.dbname": "mysql",
  "database.server.id": "970628",
  "database.include.list": "old_db",
  "database.server.name": "mysql",
  "database.history.kafka.bootstrap.servers": "kafka1:29092,kafka2:29093",
  "database.history.kafka.topic": "mysql.database.history",
  "topic.prefix": "mysql",
  "table.include.list": "old_db.PAYMENT, old_db.MEMBER",
  "schema.history.internal.kafka.bootstrap.servers": "kafka1:29092,kafka2:29093",
  "schema.history.internal.kafka.topic": "mysql.schema",
  "key.converter": "org.apache.kafka.connect.json.JsonConverter",
  "value.converter": "org.apache.kafka.connect.json.JsonConverter",
  "decimal.handling.mode": "double",
  "snapshot.lock.mode": "none",
  "transforms": "unwrap",
  "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",
  "transforms.unwrap.drop.tombstones": "false"
}'
```

## sink-connector 생성

```bash
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "sink-connector",
  "config": {
    "connector.class": "io.debezium.connector.jdbc.JdbcSinkConnector",
    "connection.url": "jdbc:postgresql://postgres:5432/new_db",
    "connection.username": "local",
    "connection.password": "local",
    "topics" : "mysql.old_db.MEMBER",
    "auto.create": "true",
    "insert.mode": "upsert",
    "delete.enabled": "true",
    "primary.key.mode": "record_key",
    "schema.evolution": "basic",
    "transforms": "unwrap",
    "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",
    "table.name.format": "MEMBER"
  }
}'
```