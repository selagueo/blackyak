import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;


public class Shader {
    private int ID;

    public void use() {
        glUseProgram(ID);
    }

    public void detach() {
        glUseProgram(0);
    }

    public Shader(String vertexFilepath, String fragmentFilepath) {
        // read the vertex and frament files
        String vertexSource = "";
        String fragmentSource = "";
        try {
            vertexSource = new String(Files.readAllBytes(Paths.get(vertexFilepath)));
        }catch(IOException e){
            System.out.println("Error: fail to load " + vertexFilepath);
            assert false : "Error: Could not open file for shader: '" + vertexFilepath + "'";
        }
        try {
            fragmentSource = new String(Files.readAllBytes(Paths.get(fragmentFilepath)));
        }catch(IOException e){
            System.out.println("Error: fail to load " + fragmentFilepath);
            assert false : "Error: Could not open file for shader: '" + fragmentFilepath + "'";
        }

        // create the shader
        int vertexID, fragmentID;
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // compile vertex shader
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE)
        {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: '" + vertexFilepath + "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // compile fragment shader
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE)
        {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: '" + fragmentFilepath + "'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // create the shader program and attach the vertex and fragment shaders
        ID = glCreateProgram();
        glAttachShader(ID, vertexID);
        glAttachShader(ID, fragmentID);
        glLinkProgram(ID);
        success = glGetProgrami(ID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(ID, GL_INFO_LOG_LENGTH);
            System.out.println(glGetProgramInfoLog(ID, len));
            assert false : "";
        }
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);
        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform2f(varLocation, vec.x, vec.y);
    }

    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform1f(varLocation, val);
    }

    public void uploadInt(String varName, int val) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform1i(varLocation, val);
    }

    public void uploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform1i(varLocation, slot);
    }

    public void uploadIntArray(String varName, int[] array) {
        int varLocation = glGetUniformLocation(ID, varName);
        use();
        glUniform1iv(varLocation, array);
    }
}
