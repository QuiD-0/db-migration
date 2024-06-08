# binlog가 활성화 되어 있는지 확인

show variables like 'log_bin';

# binlog 가 row_level로 설정되어 있는지 확인

show variables like 'binlog_format';

# 소스 테이블

create table PAYMENT
(
    ID bigint auto_increment primary key,
    TRANSACTION_KEY varchar(255)   not null,
    REFERENCE_KEY   varchar(255)   not null,
    STATUS          varchar(255)   not null,
    AMOUNT          decimal(19, 2) not null,
    CURRENCY        varchar(255)   not null,
    REG_DATE        datetime       not null,
    MOD_DATE        datetime       not null,
    RESPONSE_JSON        text
);

# 커넥터 설정

docker exec -it kafka bash
cd /opt/kafka_2.13-2.8.1
mkdir connectors

docker cp debezium-connector-mysql-2.6.2.Final-plugin.tar kafka:/opt/kafka_2.13-2.8.1/connectors/debezium-connector-mysql-2.6.2.Final-plugin.tar

cd /opt/kafka_2.13-2.8.1/connectors
tar -xvf debezium-connector-mysql-2.6.2.Final-plugin.tar

cd /opt/kafka_2.13-2.8.1/config
vim connect-distributed.properties

plugin.path=/opt/kafka_2.13-2.8.1/connectors

docker container restart kafka

# 카프카 커넥터 생성

docker exec -it kafka bash
connect-distributed.sh /opt/kafka/config/connect-distributed.properties

curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "source-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": "1",
    "database.hostname": "localhost",
    "database.port": "3306",
    "database.user": "user",
    "database.password": "user",
    "database.server.id": "1",
    "database.server.name": "mysql",
    "database.allowPublicKeyRetrieval": "true",
    "database.include.list": "old_db",
    "database.history.kafka.bootstrap.servers": "kafka:9092",
    "database.history.kafka.topic": "history.old_db",
    "key.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter.schemas.enable": "true",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "true",
    "transforms": "unwrap,addTopicPrefix",
    "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",
    "transforms.addTopicPrefix.type":"org.apache.kafka.connect.transforms.RegexRouter",
    "transforms.addTopicPrefix.regex":"(.*)",
    "transforms.addTopicPrefix.replacement":"$1"
  }
}'
