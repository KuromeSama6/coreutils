package moe.icegame.coreutils.classes;

import java.util.Random;

/**
 * Represents a Callsign UID object.
 */
public class Cuid {
    public String fix = "*****"; // Five readables
    public int idNum = 0;
    public String sector = "*";

    public static final String vowels = "aeiou";
    public static final String consonants = "bcdfghjklmnpqrstvwxz";
    public static final String digits = "0123456789";
    public static final String chrSet = "abcdefghijklmnopqrstuvwxz";
    public static final long sizeLimit = (long)(Math.pow(chrSet.length(), 2) * Math.pow(consonants.length(), 3) * vowels.length() * 1000);

    public String toString() {
        return String.format("%s%s%s", fix, idNum, sector);
    }

    public static Cuid Random() {
        Cuid ret = new Cuid();

        ret.fix = GeneratePronounceableWord();
        ret.idNum = (int)(Math.random() * 1000);
        ret.sector = RandomSample(chrSet);

        return ret;
    }

    public static Cuid FromDenary(long num) {
        Cuid ret = new Cuid();

        final int alphabetLength = chrSet.length();
        final int vowelLength = vowels.length();
        final int consonantLength = consonants.length();

        ret.sector = OrdToChr(num % alphabetLength);
        ret.idNum = (int)(num / alphabetLength % 1000);

        String[] fix = new String[] {"*", "*", "*", "*", "*"};

        fix[4] = OrdToChr(num / 25000, consonants);
        fix[3] = OrdToChr(num / (25000 * consonantLength), vowels);

        String fix2 = OrdToChr(num / (25000 * consonantLength * vowelLength), consonants);
        String fix1 = OrdToChr(num / (25000 * consonantLength * vowelLength * consonantLength), consonants.concat(vowels));
        String fix0 = OrdToChr(num / (25000L * consonantLength * vowelLength * consonantLength * consonants.concat(vowels).length()), vowels.contains(fix1) ? consonants : vowels);

        if (fix1.equals(fix2)) fix2 = "y";

        fix[2] = fix2;
        fix[1] = fix1;
        fix[0] = fix0;

        ret.fix = String.join("", fix);
        return ret;
    }

    public static <T> T RandomSample(T[] arr) {
        return arr[new Random().nextInt(arr.length - 1)];
    }

    public static String RandomSample(String arr) {
        return RandomSample(arr.split(""));
    }

    public static String OrdToChr(long ord, String sampleSet) {
        String[] samples = sampleSet.split("");
        return samples[(int)(ord % samples.length)];
    }

    public static String OrdToChr(long ord) {
        return OrdToChr(ord, "abcdefghijklmnopqrstuvwxz");
    }

    public static String GeneratePronounceableWord() {
        String firstCharSet = Math.random() < 0.5 ? vowels : consonants;
        String secondCharSet = firstCharSet.equals(vowels) ? consonants : vowels;
        String secondChar = RandomSample(secondCharSet);
        String firstTwoChars = RandomSample(firstCharSet) + secondChar;

        String lastThreeChars = "";

        for (int i = 0; i < 3; i++) {
            String charSet = "";
            switch (i) {
                case 0:
                    charSet = secondCharSet.equals(consonants) ? consonants.replace(secondChar, "") : consonants;
                    break;

                case 1:
                    charSet = vowels; break;
                case 2:
                    charSet = consonants; break;
            }
            lastThreeChars = lastThreeChars.concat(RandomSample(charSet));
        }

        return firstTwoChars + lastThreeChars;
    }

}
