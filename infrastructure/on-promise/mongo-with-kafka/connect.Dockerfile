FROM confluentinc/cp-kafka-connect:7.3.2

# mongodb source connector 설치
RUN confluent-hub install --no-prompt mongodb/kafka-connect-mongodb:1.8.0

ENV CONNECT_PLUGIN_PATH="/usr/share/java,/usr/share/confluent-hub-components"
