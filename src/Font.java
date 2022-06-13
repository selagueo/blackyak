import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.glDrawArrays;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;

public class Font {
    static private int glyphCountX = 18;
    static private int glyphCountY = 6;
    static private int glyphWidth = 7;
    static private final int glyphHeight = 9;
    static private Texture atlas;
    static private Font instance;
    static private Vector2f ratio;

    public static Font getInstance() {
        if(instance == null) {
            instance = new Font();
        }
        return instance;
    }

    private Font() {
        atlas = AssetManager.getTexture("font");
        ratio = new Vector2f((float)glyphWidth/(float)atlas.getWidth(), (float)glyphHeight/(float)atlas.getHeight());
    }

    private void beginRenderGlyph(Vector4f color) {
        Renderer.getInstance().setShader(AssetManager.getShader("spritesheet"));
        Renderer.getInstance().getShader().use();

        glActiveTexture(GL_TEXTURE0);
        Renderer.getInstance().getShader().uploadTexture("texture0", 0);
        atlas.bind();

        ARBVertexArrayObject.glBindVertexArray(Renderer.getInstance().getVAO());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        Matrix4f projectionMat = new Matrix4f();
        projectionMat.ortho(0, 1280, 0, 720, 0, 1000);
        Matrix4f viewMat = new Matrix4f();
        Vector3f position = new Vector3f(0, 0, 100);
        Vector3f front = new Vector3f(position.x, position.y, -1);
        Vector3f up = new Vector3f(0, 1, 0);
        viewMat.lookAt(position, front, up);

        Renderer.getInstance().getShader().uploadMat4f("uProj", projectionMat);
        Renderer.getInstance().getShader().uploadMat4f("uView", viewMat);
        Renderer.getInstance().getShader().uploadVec4f("uColor", color);
    }

    private void endRenderGlyph() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        ARBVertexArrayObject.glBindVertexArray(0);
        atlas.detach();
        Renderer.getInstance().getShader().detach();
    }
    private void renderGlyph(Transform transform, Vector4f color, char glyph) {
        int index = (glyph - ' ');
        int x = index % glyphCountX;
        int y = (glyphCountY - 1) - (index / glyphCountX);

        Renderer.getInstance().getShader().uploadMat4f("uWorld", transform.getWorldMatrix());
        if(ratio.x > 0.0f || ratio.y > 0.0f)
        {
            Renderer.getInstance().getShader().uploadVec2f("tile", new Vector2f(x, y));
            Renderer.getInstance().getShader().uploadVec2f("ratio", ratio);
        }
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public void render(String text, int x, int y, Vector4f color, float scale) {
        Vector2f position = new Vector2f(x, y);
        beginRenderGlyph(color);
        for (char glyph : text.toCharArray()) {
            Transform transform = new Transform(position, new Vector2f(glyphWidth*scale, glyphHeight*scale), 0);
            renderGlyph(transform, color, glyph);
            position.x += (glyphWidth*scale);
        }
        endRenderGlyph();
    }
}
