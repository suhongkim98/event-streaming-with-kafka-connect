rootProject.name = "streaming"

include("tests:kotest",
    "product-collector:product-collector-api",
    "product-collector:product-collector-enum",
    "diff-checker:diff-checker-consumer",
    "diff-checker:diff-checker-enum"
)