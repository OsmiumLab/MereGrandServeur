package fr.osmium.meregrand.utils;

import java.io.*;

public class ByteUtils {

    public static byte[] objectToBytes(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static Object bytesToObject(byte[] bytes) {
//        try {
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
