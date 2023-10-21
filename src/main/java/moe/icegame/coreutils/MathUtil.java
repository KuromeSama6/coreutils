package moe.icegame.coreutils;

public class MathUtil {
    public static int GCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return GCD(b, a % b);
    }

    public static int LCM(int a, int b) {
        return (a * b) / GCD(a, b);
    }

    public static int LCD(int... numbers) {
        int lcd = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            lcd = LCM(lcd, numbers[i]);
        }
        return lcd;
    }

}
