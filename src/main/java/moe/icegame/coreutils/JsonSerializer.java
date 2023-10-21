package moe.icegame.coreutils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.f4b6a3.uuid.UuidCreator;
import org.bukkit.material.Command;

import java.util.UUID;

public class JsonSerializer {
    public static String Serialize(Object obj, boolean requireUuid, UUID uuidOverride) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(obj);
            JsonNode rootNode = mapper.readTree(json);
            if (requireUuid) {
                UUID uuid = uuidOverride == null ? UuidCreator.getTimeBased() : uuidOverride;
                ObjectNode objectNode = (ObjectNode) rootNode;
                objectNode.put("__id", uuid.toString());
            }
            return mapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static String Serialize(Object obj) {return Serialize(obj, false, null);}
    public static String Serialize(Object obj, boolean requireUuid) {return Serialize(obj, requireUuid, null);}

    public static JsonNode Deserialize(String str) {
        // create an instance of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();

        // parse the JSON string into a JsonNode object
        try {
            return objectMapper.readTree(str);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T DeserializeObject(String str, Class<T> cls, T fallback) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(str, cls);
        } catch (JsonProcessingException e) {
            return fallback;
        }
    }

    public static <T> T DeserializeObject(String str, Class<T> cls) {
        return DeserializeObject(str, cls, null);
    }

}
