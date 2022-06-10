import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

    public static void addShader(String name, String vertexFilepath, String fragmentFilepath)
    {
        shaders.put(name, new Shader(vertexFilepath, fragmentFilepath));
    }

    public static Shader getShader(String name)
    {
        return shaders.get(name);
    }

    public static void addTexture(String name, String filepath)
    {
        textures.put(name, new Texture(filepath));
    }

    public static Texture getTexture(String name)
    {
        return textures.get(name);
    }
}
