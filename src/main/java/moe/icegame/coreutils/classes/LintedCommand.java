package moe.icegame.coreutils.classes;

import moe.icegame.coreutils.GameUtil;
import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LintedCommand {
    public HashMap<String, List<String>> params = new HashMap<>();
    public String original;

    public LintedCommand(Command cmd, String[] args) {
        this(GameUtil.FullCommand(cmd, args));
    }
    public LintedCommand(String msg) {
        original = msg;

        String[] args = msg.split(" ");
        if (args.length <= 1) return;
        String currentParam = "";

        for (int i = 1; i < args.length; i++){
            String arg = args[i];
            if (arg.charAt(0) == '-') {
                if (!params.containsKey(arg)) params.put(arg, new ArrayList<>());
                currentParam = arg;

            } else if (!currentParam.equals("")) {
                params.get(currentParam).add(arg);
            }
        }

    }

    public String[] GetParam(String key) {
        List<String> ret = params.getOrDefault("-" + key, new ArrayList<>());
        if (ret.size() == 0) return null;
        return ret.toArray(new String[0]);
    }

    public String GetParamOrDefault(String key, int index, Object fallback) {
        String ret = GetParam(key, index);
        return ret == null ? fallback.toString() : ret;
    }

    public String GetParam(String key, int index) {
        String[] ret = GetParam(key);
        if (ret == null) return null;
        try {
            return ret[index];
        } catch (IndexOutOfBoundsException _) {
            return null;
        }
    }

    public String GetParamRaw(int index) {
        String[] args = original.split(" ");
        try {
            return args[index];
        } catch (ArrayIndexOutOfBoundsException _) {
            return null;
        }
    }

    public String[] GetParamRaw() {
        return original.split(" ");
    }

    public boolean HasParam(String key) {
        return params.containsKey("-" + key);
    }

}
