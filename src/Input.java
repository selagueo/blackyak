import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static Input instance;
    private boolean keys[] = new boolean[350];

    static Input getInstance()
    {
        if(instance == null)
        {
            instance = new Input();
        }
        return instance;
    }
    public static void kayCallback(long window, int key, int sandcode, int action, int mods)
    {
        if (action == GLFW_PRESS) {
            instance.keys[key] = true;
        } else if (action == GLFW_RELEASE) {
            instance.keys[key] = false;
        }
    }

    public boolean getKeyDown(int key)
    {
        boolean result = instance.keys[key];
        instance.keys[key] = false;
        return result;

    }

    public boolean playerStand() {
        return getKeyDown(GLFW_KEY_ENTER);
    }

    public boolean PlayerHit() {
        return getKeyDown(GLFW_KEY_SPACE);
    }

    public boolean restart() { return getKeyDown(GLFW_KEY_SPACE);}

    public boolean start() { return getKeyDown(GLFW_KEY_SPACE);}

}
