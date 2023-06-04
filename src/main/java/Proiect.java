import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.util.gl2.GLUT;

import static com.jogamp.opengl.GL.GL_TRIANGLES;


public class Proiect implements GLEventListener, KeyListener {

    private GLU glu = new GLU();
    private float x_rotation, y_rotation, z_rotation;
    private int texture;

    private boolean rotateX = false;
    private boolean rotateY = false;
    private boolean rotateZ = false;
    private int index;

    private JFrame frame;
    private String textureName = "textura1.jpg";
    private GLUT glut;

    public void cub(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glScalef(2.0f, 2.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0f, 0f, -5.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glRasterPos2f(0.0f, 2f);


        String text = "Cub";
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, text); // Render the text
        gl.glScalef(1.0f, 1.0f, 1.0f);
        gl.glRotatef(x_rotation, 1.0f, 1.0f, 1.0f);
        gl.glRotatef(y_rotation, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(z_rotation, 0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glFlush();
        x_rotation -= 5.0f;


        try {
            File im = new File("D:\\facultate\\An III\\SEM II\\GUI\\untitled\\Texturi\\" + textureName);
            Texture t = TextureIO.newTexture(im, true);
            texture = t.getTextureObject(gl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rotateX) {
            x_rotation += .1f;
        }
        if (rotateY) {
            y_rotation += .1f;
        }
        if (rotateZ) {
            z_rotation += .1f;
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (index == 0)
            cub(drawable);
        else
            piramida(drawable);
    }

    private void piramida(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glScalef(2.0f, 2.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0f, 0f, -5.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glRasterPos2f(0.0f, 2f);


        String text = "Piramida";
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, text); // Render the text
        gl.glScalef(1.0f, 1.0f, 1.0f);
        gl.glRotatef(x_rotation, 1.0f, 1.0f, 1.0f);
        gl.glRotatef(y_rotation, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(z_rotation, 0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

        gl.glBegin(GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles
        // Front
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);


        // Right
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        // Back
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);

        // Left
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glEnd();   // Done drawing the pyramid

        gl.glFlush();

        x_rotation += 0.6f;
        try {
            File im = new File("D:\\facultate\\An III\\SEM II\\GUI\\untitled\\Texturi\\" + textureName);
            Texture t = TextureIO.newTexture(im, true);
            texture = t.getTextureObject(gl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        index = 0;
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        float r = 255 / 255f;
        float g = 255 / 255f;
        float b = 204 / 255f;
        gl.glClearColor(r, g, b, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        glut = new GLUT();
        frame.addKeyListener(this);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        if (height <= 0)
            height = 1;
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 20.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (index == 0) {
                index++;
            } else {
                index = 0;
            }


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Proiect r = new Proiect();
        glcanvas.addGLEventListener(r);
        glcanvas.setSize(800, 800);
        final JFrame frame = new JFrame("Proiect");
        r.frame = frame;
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setResizable(false);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
    }
}