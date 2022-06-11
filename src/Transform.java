import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {
    public Vector2f position;
    public Vector2f scale;
    public float rotationZ;
    public float rotationY;

    Transform(Vector2f position, Vector2f scale, float rotation)
    {
        this.position = position;
        this.scale = scale;
        this.rotationZ = rotation;
        this.rotationY = 0.0f;
    }

    public Matrix4f getWorldMatrix()
    {
        Matrix4f transMat = new Matrix4f();
        Matrix4f scaleMat = new Matrix4f();
        Matrix4f rotatMat = new Matrix4f();
        transMat.translate(new Vector3f(position.x, position.y, 0.0f));
        scaleMat.scale(new Vector3f(scale.x, scale.y, 1.0f));
        rotatMat.rotate(rotationY, new Vector3f(0, 1, 0));
        rotatMat.rotate(rotationZ, new Vector3f(0, 0, 1));
        return transMat.mul(rotatMat.mul(scaleMat));
    }

    public static float lerp(float a, float b, float t)
    {
        return (1.0f - t) * a + t * b;
    }

    public static float invLerp(float a, float b, float v)
    {
        return (v - a) / (b - a);
    }

    public static float reMap(float iMin, float iMax, float oMin, float oMax, float v)
    {
        float t = invLerp(iMin, iMax, v);
        return lerp(oMin, oMax, t);
    }

    public static Vector2f lerpV2(Vector2f a, Vector2f b, float t)
    {
        float x = lerp(a.x, b.x, t);
        float y = lerp(a.y, b.y, t);
        Vector2f result = new Vector2f(x, y);
        return result;
    }

    /*
    float smoothstep(float a, float b, float x)
    {
        float t = saturate((x - a)/(b - a));
        return t*t*(3.0 - (2.0*t));
    }
    */

}
