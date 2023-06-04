//import Lab4.TextureHandler;
//import com.jogamp.newt.event.KeyEvent;
//import com.jogamp.newt.event.KeyListener;
//import com.jogamp.newt.event.MouseEvent;
//import com.jogamp.newt.event.MouseListener;
//import com.jogamp.opengl.GL;
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GLAutoDrawable;
//import com.jogamp.opengl.GLEventListener;
//import com.jogamp.opengl.awt.GLCanvas;
//import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.glu.GLUquadric;
//import com.jogamp.opengl.util.Animator;
//import com.sun.opengl.util.BufferUtil;
//import javax.swing.*;
//import java.awt.event.MouseMotionListener;
//import java.nio.FloatBuffer;
//import java.nio.IntBuffer;
//
//
//public abstract class Lab7 extends JFrame
//        implements
//        GLEventListener,
//        // These are the new interfaces one needs to implement
//        KeyListener,
//        MouseListener,
//        MouseMotionListener {
//    private GLCanvas canvas;
//    private Animator animator;
//    private GLU glu;
//    double equation[] = {1f, 1f, 1f, 1f};
//    private TextureHandler texture1, texture2;
//    // The x position
//    private float xpos;
//
//    // The rotation value on the y axis
//    private float yrot;
//
//    // The z position
//    private float zpos;
//    float angle = 0.0f;
//
//    private float heading;
//
//    // Walkbias for head bobbing effect
//    private float walkbias = 0.0f;
//
//    // The angle used in calculating walkbias */
//    private float walkbiasangle = 0.0f;
//
//    // The value used for looking up or down pgup or pgdown
//    private float lookupdown = 0.0f;
//    // Define camera variables
//    float cameraAzimuth = 0.0f, cameraSpeed = 0.0f, cameraElevation = 0.0f;
//
//    // Set camera at (0, 0, -20)
//    float cameraCoordsPosx = 0.0f, cameraCoordsPosy = 0.0f, cameraCoordsPosz = -20.0f;
//
//    // Set camera orientation
//    float cameraUpx = 0.0f, cameraUpy = 1.0f, cameraUpz = 0.0f;
//
//    // Define an array to keep track of the key that was pressed
//    private boolean[] keys = new boolean[250];
//
//    private void initializeJogl() {
//
//
//        // Adding the keyboard and mouse event listeners to the canvas.
//        this.canvas.addKeyListener((java.awt.event.KeyListener) this);
//        this.canvas.addMouseListener((java.awt.event.MouseListener) this);
//        this.canvas.addMouseMotionListener(this);
//
//    }
//
//
//    private void drawCube(GL gl) {
//        float vertices[] = {1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0
//                1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1};     // 8 vertex coords
//        int indices[] = {0, 1, 2, 3,
//                0, 3, 4, 5,
//                0, 5, 6, 1,
//                1, 6, 7, 2,
//                7, 4, 3, 2,
//                4, 7, 6, 5}; // 24 indices
//
//        FloatBuffer bVertices = BufferUtil.newFloatBuffer(vertices.length);
//        for (float vertex : vertices) bVertices.put(vertex);
//        bVertices.rewind();
//
//        IntBuffer bIndices = BufferUtil.newIntBuffer(indices.length);
//        for (int index : indices) bIndices.put(index);
//        bIndices.rewind();
//
//        // Activate and specify pointer to vertex array.
//        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL.GL_FLOAT, 0, bVertices);
//
//        // Draw the cube.
//        gl.glDrawElements(GL2.GL_QUADS, 24, GL.GL_UNSIGNED_INT, bIndices);
//        //gl.glDrawArrays(GL.GL_QUADS, 0, vertices.length);
//
//        // Deactivate vertex arrays after drawing.
//        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
//    }
//
//    public void display(GLAutoDrawable canvas) {
//
//    }
//}
