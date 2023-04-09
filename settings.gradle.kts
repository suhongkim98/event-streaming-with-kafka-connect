rootProject.name = "streaming"

include("tests:kotest",
    "product-collector:product-collector-api",
    "product-collector:product-collector-enum",
    "diff-checker:diff-checker-consumer",
    "diff-checker:diff-checker-enum",
    "image-collector:image-collector-processor",
    "image-collector:image-collector-enum",
    "image-remover:image-remover-processor",
    "image-remover:image-remover-enum",
    "complete-product-uploader:complete-product-uploader-api",
    "complete-product-uploader:complete-product-uploader-enum"
)