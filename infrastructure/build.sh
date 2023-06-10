# build
./gradlew bootJar -p ./image-collector/image-collector-processor
./gradlew bootJar -p ./image-remover/image-remover-processor
./gradlew bootJar -p ./complete-product-uploader/complete-product-uploader-api
./gradlew bootJar -p ./diff-checker/diff-checker-consumer
./gradlew bootJar -p ./product-collector/product-collector-api

# upload to docker schema registry
docker buildx build -f ./Dockerfile ./image-collector/image-collector-processor --push -t 127.0.0.1:5001/image-collector
docker buildx build -f ./Dockerfile ./image-remover/image-remover-processor --push -t 127.0.0.1:5001/image-remover
docker buildx build -f ./Dockerfile ./complete-product-uploader/complete-product-uploader-api --push -t 127.0.0.1:5001/complete-product-uploader
docker buildx build -f ./Dockerfile ./diff-checker/diff-checker-consumer --push -t 127.0.0.1:5001/diff-checker
docker buildx build -f ./Dockerfile ./product-collector/product-collector-api --push -t 127.0.0.1:5001/product-collector
