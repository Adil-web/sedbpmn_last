package kz.kazgisa.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public static List<String> splitIntoBlocks(String text, int blockSize) {
        List<String> ret = new ArrayList<String>((text.length() + blockSize - 1) / blockSize);

        for (int start = 0; start < text.length(); start += blockSize) {
            ret.add(text.substring(start, Math.min(text.length(), start + blockSize)));
        }
        return ret;
    }
}
