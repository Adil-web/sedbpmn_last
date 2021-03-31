package kz.kazgisa.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CommonUtils {
    public static byte[] inputStreamToByteArray(InputStream is) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String compressString(String str) {
        try {
            if (str == null || str.length() == 0) {
                return str;
            }

            System.out.println("String length : " + str.length());
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(obj);
            gzip.write(str.getBytes("UTF-8"));
            gzip.close();
            System.out.println("obj length : " + obj.size());
            String outStr = Base64.getEncoder().encodeToString(obj.toByteArray());
            System.out.println("Output String length : " + outStr.length());
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decompressString(String str) {
        try {
            if (str == null || str.length() == 0) {
                return str;
            }
            byte[] compressed = Base64.getDecoder().decode(str);

            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
            BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
            String outStr = "";
            String line;
            while ((line = bf.readLine()) != null) {
                outStr += line;
            }
            System.out.println("Output String lenght : " + outStr.length());
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static Field getField(String name,Class<?> type) {

        try {
            return getAllFields(new LinkedList<>(), type)
                    .stream().filter(field -> field.getName().equals(name))
                    .findAny().get();
        }catch (Exception e) {
            return getAllFields(new LinkedList<>(), type)
                    .stream().filter(field -> field.getName().startsWith("is")&& field.getName().toLowerCase().endsWith(name))
                    .findAny().get();
        }

    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
