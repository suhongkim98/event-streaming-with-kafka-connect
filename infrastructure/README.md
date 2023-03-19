# infrastructure
`docker-desktop` 대신 `colima`로 로컬에서 모든 인프라를 구성합니다.

## colima start
minikube 모드로 실행합니다. minikube를 사용하지 않고 싶을 때는 해당 옵션을 뺍니다.
```bash
colima start --kubernetes --network-address --cpu 4 --memory 8 --edit
```
```yml
provision:
  - mode: system
    script: |
        sysctl -w vm.max_map_count=1000000
        echo never >/sys/kernel/mm/transparent_hugepage/enabled
```

## infrastructure/on-promise/mongo-with-kafka
`on-promise` 상에 올라가는 `kafka`, `mongodb`에 대한 `docker-compose`가 정의되는 공간입니다.

* `depends_on` 설정을 위해 같은 docker-compose 에 구성했습니다. 
* 몽고디비에서 제공해주는 `source connector`를 사용하였으며 구성하며 참고한 자료는 https://www.mongodb.com/docs/kafka-connector/current/tutorials/source-connector/ 입니다. 


### docker-compose up
```bash
docker-compose -p mongo-kafka up -d
``` 


### source connector 붙이기
몽고디비 접속
```bash
docker exec -it mongo1 /bin/bash
```

connect 클러스터로 api call을 하여 커넥터 붙이기
```bash
curl -X POST -H "Content-Type: application/json" \
-d @/example-stream/source_connector/simple_source.json http://connect:8083/connectors -w "\n"
```
해당 json 파일은 커스텀 몽고디비를 구성할 때 COPY해줌

### kafka connect status 확인
```bash
docker exec mongo1 status
```

### change event topic 확인
```bash
docker exec -it mongo1 /bin/bash

kc example-stream.product
```



## infrastructure/kubernetes
쿠버네티스 상에 올라가는 `producer`, `consumer` 에 대한 쿠버네티스 디스크립터가 정의되는 공간입니다.