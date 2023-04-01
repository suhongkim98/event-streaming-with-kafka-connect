# infrastructure
`docker-desktop` 대신 `colima`로 로컬에서 모든 인프라를 구성합니다.

## 1. colima start
`minikube` 모드로 실행합니다. `minikube` 를 사용하지 않고 싶을 때는 해당 옵션을 뺍니다.
```bash
colima start --kubernetes --network-address --cpu 4 --memory 8 --edit
```

`insecure-registries`는 `colima status` 했을 때 나오는 IP 입니다.
```yml
// docker image registry 관련 insecure 설정
docker:
  insecure-registries:
    - 192.168.106.2:5000

// provision 설정
provision:
  - mode: system
    script: |
        sysctl -w vm.max_map_count=1000000
        echo never >/sys/kernel/mm/transparent_hugepage/enabled
```


## 2. infrastructure/on-promise/mongo-with-kafka
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



## 3. infrastructure/kubernetes
쿠버네티스 상에 올라가는 `producer`, `consumer` 에 대한 쿠버네티스 디스크립터가 정의되는 공간입니다.

### application.yml 수정
모든 마이크로서비스에 대해 application.yml을 수정하여 `colima status` 했을 때 나오는 IP로 맞춰주어 통신이 가능하게끔 해주어야 합니다. -> 그래야 붙을 수 있습니다.


### docker build
`producer`, `consumer` 등 `processor`를 도커 이미지로 빌드합니다.

#### buildx config
https://github.com/docker/buildx/issues/777 이슈 참고

```
docker buildx use colima
```

#### uploader 빌드 예시
로컬 레지스트리에 이미지를 업로드하는 명령어입니다.
`colima status` 했을 때 나오는 IP로 이미지 레지스트리를 찾아 업로드합니다.
```bash
./gradlew bootJar -p ./diff-checker/diff-checker-consumer

docker buildx build -f ./Dockerfile ./diff-checker/diff-checker-consumer --push -t 192.168.106.2:5000/diff-checker
```

### k3s deploy
쿠버네티스 상에 `producer`, `consumer`를 배포합니다.
* 먼저 각 디스크립터의 도커 이미지 경로를 확인하고 레지스트리 IP를 확인합니다. (colima IP가 맞는지)

```bash
kubectl apply -f ./infrastructure/kubernetes/
```