package moe.icegame.coreutils;

import moe.icegame.coreutils.interfaces.IYamlSerializable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class YamlUtil {
    public static List<ConfigurationSection> GetPseudoList(ConfigurationSection cfg) {
        List<ConfigurationSection> ret = new ArrayList<>();
        for (String key : cfg.getKeys(false)) {
            ret.add(cfg.getConfigurationSection(key));
        }

        return ret;
    }

    public static List<ConfigurationSection> GetPseudoList(ConfigurationSection cfg, String path) {
       return GetPseudoList(cfg.getConfigurationSection(path));
    }

    public static YamlConfiguration ConstructPseudoList(List<IYamlSerializable> li) {
        YamlConfiguration ret = new YamlConfiguration();

        for (int i = 0; i < li.size(); i++) ret.set(Integer.toString(i), li.get(i).Serialize());

        return ret;
    }

    public static <T> Map<String, T> GetMap(ConfigurationSection cfg) {
        return GetMap(cfg, null);
    }

    public static <T> Map<String, T> GetMap(ConfigurationSection cfg, T def) {
        Map<String, T> ret = new HashMap<>();
        for (String key : cfg.getKeys(false)) {
            ret.put(key, (T)cfg.get(key, def));
        }

        return ret;
    }

    public static YamlConfiguration FromMap(Map<String, Object> map) {
        YamlConfiguration ret = new YamlConfiguration();
        for (String key : map.keySet()) ret.set(key, map.get(key));
        return ret;
    }

    public static List<YamlConfiguration> GetConfigurationList(List<?> li) {
        return li.stream()
                .map(c -> FromMap((Map<String, Object>) c))
                .collect(Collectors.toList());
    }

    public static <Tk, Tv, Uk, Uv> Map<Uk, Uv> Map(Map<Tk, Tv> map, Function<Tk, Uk> keyFunc, Function<Tv, Uv> valueFunc) {
        Map<Uk, Uv> ret = new HashMap<>();
        for (Tk key : map.keySet()) {
            ret.put(keyFunc.apply(key), valueFunc.apply(map.get(key)));
        }

        return ret;
    }

    public static YamlConfiguration FromString(String str) {
        try {
            YamlConfiguration ret = new YamlConfiguration();
            ret.loadFromString(str);
            return ret;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return new YamlConfiguration();
        }
    }

}
