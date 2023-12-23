package moe.icegame.coreutils;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    public static <T> T RandomChoice(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List is empty or null");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    @SafeVarargs
    public static <T> T RandomChoice(T... array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
    }

    public static double RandRange(double start, double end) {
        return start + new Random().nextDouble() * (end - start);
    }
}
