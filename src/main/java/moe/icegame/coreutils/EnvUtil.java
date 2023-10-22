package moe.icegame.coreutils;

public class EnvUtil {
    public static boolean IsProxy() {
        return BungeeMain.getInstance() != null;
    }
}
