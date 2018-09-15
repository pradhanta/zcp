package com.zcp.socket;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.record.Record;

import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class PacketHandler {

    static Producer<String, String> producer;

    final static long TIME_OUT = 10000L; // 10 second wait time

    final static String TOPIC_NAME = "testScale";

    private synchronized static Producer<String, String> getProducer() {
        if(producer == null) {

            // create instance for properties to access producer configs
            Properties props = new Properties();

            //Assign localhost id
            props.put("bootstrap.servers", "kafka:9092");

            //Set acknowledgements for producer requests.
            props.put("acks", "all");

            //If the request fails, the producer can automatically retry,
            props.put("retries", 0);

            //Specify buffer size in config
            props.put("batch.size", 16384);

            //Reduce the no of requests less than 0
            props.put("linger.ms", 1);

            //The buffer.memory controls the total amount of memory available to the producer for buffering.
            props.put("buffer.memory", 33554432);

            props.put("key.serializer",
                    "org.apache.kafka.common.serialization.StringSerializer");

            props.put("value.serializer",
                    "org.apache.kafka.common.serialization.StringSerializer");

            producer = new KafkaProducer<String, String>(props);
        }

        return producer;
    }

    public PacketHandler() {

    }

    public void receivedPacket(Packet packet) {

        try {
            Producer<String, String> producer = getProducer();

            Future<RecordMetadata> kafkaMsg = producer.send(new ProducerRecord<String, String>(TOPIC_NAME,
                    "JSONPacket", packet.toJSON()));
            kafkaMsg.wait(TIME_OUT);
        } catch (Exception ex) {
            Logger.getRootLogger().error("Error occurred while sending packet to kafka", ex);
        }

    }
}
