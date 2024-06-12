# db-migration

실시간으로 들어오는 데이터를 손실없이 다른 DB로 이관해보기 위한 프로젝트입니다.   

## version

- mysql 8.0
- postgresql 16
- kafka 2.8.1
- debezium 2.6.0
- zookeeper 3.4.13

## mysql -> kafka(cdc) -> postgresql   

CDC(Change Data Capture)를 이용하여 mysql의 데이터를 kafka로 전송하고,    
발행된 topic을 읽어 postgresql로 데이터를 이관합니다.

저는 두가지 상황을 가정하였습니다.   
1. 이관 하려고 하는 DB의 스키마가 같은 경우   
2. 이관 하려고 하는 DB의 스키마가 다른 경우   

### 1. 이관 하려고 하는 DB의 스키마가 같은 경우

CDC sink connector를 이용하여 데이터를 이관합니다.

### 2. 이관 하려고 하는 DB의 스키마가 다른 경우

발행된 토픽을 구독하는 어플리케이션을 통해 데이터를 변환 후 이관합니다.

모든 데이터가 이관되면 어플리케이션의 DB 설정을 변경하여 DB를 postgresql로 변경 후 배포합니다.