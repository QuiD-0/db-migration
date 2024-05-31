# db-migration

실시간으로 들어오는 데이터를 손실없이 다른 DB로 이관해보기 위한 프로젝트입니다.   

## mysql -> kafka(cdc) -> postgresql   

CDC(Change Data Capture)를 이용하여 mysql의 데이터를 kafka로 전송하고,    
kafka에서 데이터를 읽어 postgresql로 이관합니다.    

CDC 이전의 데이터는 mysql에서 직접 dump하여 postgresql로 이관합니다.   
이때 PK가 중복되지 않도록 주의해야합니다.   
저는 마지막 PK + 1000을 새로운 PK로 사용했습니다.   

모든 데이터가 이관되면 어플리케이션의 DB 설정을 변경하여 DB를 postgresql로 변경 후 배포합니다.   

최종 목표는 mysql의 데이터를 손실없이 postgresql로 이관하고   
읽기와 쓰기 DB를 분리하여 성능을 향상시키는 것입니다.   