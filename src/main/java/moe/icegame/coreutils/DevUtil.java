package moe.icegame.coreutils;

import com.github.f4b6a3.uuid.UuidCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class DevUtil {

    public static String ReadResourceFile(Class cls, String path) {
        // Get the input stream for the SQL file
        InputStream inputStream = cls.getClassLoader().getResourceAsStream(path);

        if (inputStream == null) throw new RuntimeException(String.format("Unable to find resource %s", path));

        // Read the input stream into a string using a scanner
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String ret = scanner.hasNext() ? scanner.next() : "";

        // Close the input stream and the scanner
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scanner.close();

        return ret;
    }

}
