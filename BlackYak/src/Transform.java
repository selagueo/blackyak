import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {
    public Vector3f position;
    public Vector3f scale;
    public float rotation;

    Transform(Vector2f position, Vector2f scale, float rotation)
    {
        this.position = new Vector3f(position.x, position.y, 0);
        this.scale = new Vector3f(scale.x, scale.y, 0);
        this.rotation = rotation;
    }

    public Matrix4f getWorldMatrix()
    {
        Matrix4f transMat = new Matrix4f();
        Matrix4f scaleMat = new Matrix4f();
        Matrix4f rotatMat = new Matrix4f();
        transMat.translate(position);
        scaleMat.scale(scale);
        rotatMat.rotate(rotation, new Vector3f(0, 0, 1));
        return transMat.mul(rotatMat.mul(scaleMat));
    }
}
