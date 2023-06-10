# infrastructure
`docker-desktop`을 이용해서 로컬에서 모든 인프라를 구성합니다.

## 1. docker desktop start
### enable kubernetes 
`docker desktop`을 킨 후 `enable kubernetes` 옵션을 활성화합니다.

## 2. infrastructure/on-promise/mongo-with-kafka
`on-promise` 상에 올라가는 `kafka`, `mongodb`에 대한 `docker-compose`가 정의되는 공간입니다.

* `depends_on` 설정을 위해 같은 docker-compose 에 구성했습니다. 
* 몽고디비에서 제공해주는 `source connector`를 사용하였으며 구성하며 참고한 자료는 https://www.mongodb.com/docs/kafka-connector/current/tutorials/source-connector/ 입니다. 

### kube node ip 설정
`docker-compose up`을 하기 전에 카프카에서 아이피를 확인합니다.

`kubectl get nodes -o wide`을 입력하여 나온 노드의 `internal-ip`로 카프카 리스너를 수정해주어야 합니다.

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

### docker build
`producer`, `consumer` 등 `processor`를 도커 이미지로 빌드합니다. 

먼저 `kubectl get nodes -o wide`을 입력하여 나온 노드의 `internal-ip`로 수정하는 작업이 필요합니다.
모든 애플리케이션의 `application.yml`을 위 명령어 아이피로 수정합니다.

#### uploader 빌드 예시
로컬 레지스트리에 이미지를 업로드하는 `build.sh` 스크립트를 실행합니다. `루트 디렉토리`에서 진행합니다.

```bash
sh infrastructure/build.sh
```

### k3s deploy
쿠버네티스 상에 `producer`, `consumer`를 배포합니다.

```bash
kubectl apply -f ./infrastructure/kubernetes/
```