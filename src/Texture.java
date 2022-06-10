import org.joml.Vector2f;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture {
    private int ID;
    private int width;
    private int height;
    private int channels;
    public Texture(String filepath)
    {
        ID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, ID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        try(MemoryStack stack = stackPush())
        {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            IntBuffer pChannels = stack.mallocInt(1);
            stbi_set_flip_vertically_on_load(true);
            ByteBuffer data = stbi_load(filepath, pWidth, pHeight, pChannels, 0);
            if(data != null) {
                width = pWidth.get(0);
                height = pHeight.get(0);
                channels = pChannels.get(0);
                int format = -1;
                switch(channels)
                {
                    case 3:
                    {
                        format = GL_RGB;
                    }break;
                    case 4:
                    {
                        format = GL_RGBA;
                    }break;
                    default:
                    {
                        System.out.println("Error: image format not supported");
                        assert false : "Error: image format not supported";
                    }break;
                }
                glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
                stbi_image_free(data);
            }
        }

    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    public  void detach()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    public int getID()
    {
        return ID;
    }
}
