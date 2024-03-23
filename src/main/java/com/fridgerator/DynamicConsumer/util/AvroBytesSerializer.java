package com.fridgerator.DynamicConsumer.util;

import java.io.IOException;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class AvroBytesSerializer implements Serializer<GenericRecord> {

    @Override
    public byte[] serialize(String topic, GenericRecord data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);

        DatumWriter<GenericRecord> datumWriter = new SpecificDatumWriter<GenericRecord>(data.getSchema());
        try {
            datumWriter.write(data, binaryEncoder);
            binaryEncoder.flush();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            System.out.println("EXCEPTION : " + e);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
