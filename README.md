# event-streaming-with-kafka-connect
사이드 프로젝트로 판매 상품을 수집하며 해당 상품의 이미지도 수집해주는 미니멀한 스트리밍 시스템 `MSA`로 구현해보기


## 학습목표
* `코틀린`과 `코루틴`, `웹플럭스`, `reactive mongodb`를 통해 코틀린에서의 비동기, 논블로킹 프로그래밍을 내 것으로 만든다.
* `카프카 커넥트`를 이용하여 `CDC`를 구현해보고 `스트림즈`로 데이터베이스의 변화 이벤트를 받아 가공하여 다른 토픽에 넣어본다.
* `쿠버네티스`의 `HPA`를 이용해 `Auto Scaling`을 구현하고 `Scale In` 과정에서 데이터 유실을 고민해본다.
* `NoSQL`의 장점을 체감해본다.


## 기술 스택
* `kotlin`, `coroutine`
* `spring boot 3.0`, `webflux`
* `kafka connect`, `kafka streams`
* `kubernetes(minikube 사용)`
* `mongodb`
* `localstack`


## 기능적 요구사항
* [ ] 상품을 `수집`할 수 있다.
``` json
"POST /api/v1/product"

{
  "productId": "String",
  "title": "String",
  "content": "String",
  "category": "String",
  "price": "String",
  "imageUrl": "String"
}
```
* [ ] 상품 수집 시 `image url`을 받으면 실제 해당 이미지를 수집하여 `localstack S3`에 업로드한다.
* [ ] `productId`가 동일하면 상품을 `업데이트`한다. 상품이 업데이트되면 이미지를 재수집한다.
  * [ ] 기존 이미지는 삭제한다. 
* [ ] 상품을 `조회`할 수 있다. 조회 시 실제 수집한 이미지의 경로도 반환한다. 
  * [ ] 수집되지 않은 상품은 조회되지 않는다.
```json
"GET /api/v1/products"
```
* [ ] 상품을 `삭제`할 수 있다.
  * [ ] 상품이 삭제되면 S3에서 해당 상품의 이미지도 삭제한다.
```json
"DELETE /api/v1/{productId}"
```

## 비기능적 요구사항
* [ ] `코틀린`으로 구현한다.
* [ ] `웹플럭스`를 이용해 비동기, 논브블로킹하게 전반적인 시스템을 스트림 형태로 구현한다.
* [ ] 마이크로서비스 아키텍처로 설계, 개발한다.
* [ ] `MSA`를 고려하며 `multi module` 로 구성하기! 
* [ ] `nGrinder`나 다른 도구를 이용해 성능측정 해보기, 그 과정에서 Auto Scaling 확인한다.



