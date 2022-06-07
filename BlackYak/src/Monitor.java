import static org.lwjgl.glfw.GLFW.GLFW_CONNECTED;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;

public class Monitor
{
    static public void monitorCallback(long monitor, int event)
    {
        switch(event)
        {
            case GLFW_CONNECTED :
            {
                String name = glfwGetMonitorName(monitor);
                System.out.println(name);
            }break;

        }

    }

}
