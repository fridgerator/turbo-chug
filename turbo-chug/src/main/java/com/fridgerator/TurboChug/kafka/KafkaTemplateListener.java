package com.fridgerator.TurboChug.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.listener.MessageListener;

import io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class KafkaTemplateListener implements MessageListener<Object, Object> {
    private final Logger logger = LogManager.getLogger(KafkaTemplateListener.class);
    
    @Override
    public void onMessage(ConsumerRecord<Object, Object> record) {
        // System.out.println("RECORD PROCESSING : "+ record);
        logger.info("record : {}", record);
        var klass = record.value().getClass();
        logger.info("value class : {}", klass);

        // byte[] avroData = ((String) record.value()).getBytes();

        // byte[] avroData = (byte[]) record.value();


        // org.apache.avro.InvalidAvroMagicException: Not an Avro data file.
        // DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        // // Create a DataFileReader to read the Avro data
        // DataFileReader<GenericRecord> dataFileReader = null;
        // try {
        //     var input = new SeekableByteArrayInput(avroData);
        //     // dataFileReader = new DataFileReader<>(null, datumReader);
        //     dataFileReader = new DataFileReader<>(input, datumReader);

        //     // Iterate over the records in the Avro data
        //     while (dataFileReader.hasNext()) {
        //         GenericRecord r = dataFileReader.next();
        //         // Access the deserialized data using the GenericRecord
        //         // ...
        //         logger.info("record : {}", r);
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // } finally {
        //     if (dataFileReader != null) {
        //         try {
        //             dataFileReader.close();
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }



        // AvroRuntimeException: Not a Specific class: interface org.apache.avro.generic.GenericRecord
        // DatumReader<Futurama> datumReader = new SpecificDatumReader<Futurama>(Futurama.class);
        // Decoder decoder = DecoderFactory.get().binaryDecoder(avroData, null);
        // try {
        //     var result = datumReader.read(null, decoder);
        //     logger.info("result : {}", result);
        // } catch (Exception e) {
        //     logger.error("woops : {}", e);
        // }

        // needs schema registry client
        // try {
        //     var des = new TurboKafkaAvroDeserializer();
        //     var result = des.deserialize(record.topic(), record.headers(), val.getBytes());
        //     logger.info("result : {}", result);
        // } catch (Exception e) {
        //     logger.error("woops : {}", e);
        // }




        // fails with: Cannot invoke "org.apache.avro.Schema.equals(Object)" because "writer" is null (needs a schema)
        // DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        // Decoder decoder = DecoderFactory.get().binaryDecoder(avroData, null);
        // try {
        //     var result = datumReader.read(null, decoder);
        //     logger.info("deserialized data='{}'", result);
        // } catch (IOException e) {
        //     logger.error("io exception : {}", e);
        // }
    }
}
