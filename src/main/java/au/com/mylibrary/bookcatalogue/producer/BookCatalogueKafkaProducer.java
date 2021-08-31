package au.com.mylibrary.bookcatalogue.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

/**
 * This class contains method for connecting to kafka
 */

@Service
public class BookCatalogueKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(BookCatalogueKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ResponseEntity<Object> send(String topic, String message) {
        logger.info("Sending message to kafka producer");

        try {

            ListenableFuture listenableFuture = kafkaTemplate.send(topic, message);

            SuccessCallback<SendResult<String, String>> successCallback =
                new SuccessCallback<SendResult<String, String>>() {

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        logger.info("Message Sent Successfully to topic");
                    }
                };
            FailureCallback failureCallback = new FailureCallback() {
                @Override
                public void onFailure(Throwable ex) {
                    logger.error("Failed to send message to topic");
                }
            };
            listenableFuture.addCallback(successCallback, failureCallback);
        } catch (Exception e) {
            logger.error("Error while sending message", e);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
