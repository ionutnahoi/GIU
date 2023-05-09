import Lab4.TextureHandler;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.event.MouseMotionListener;


public abstract class Lab7 extends JFrame
        implements
        GLEventListener,
        // These are the new interfaces one needs to implement
        KeyListener,
        MouseListener,
        MouseMotionListener {
    private GLCanvas canvas;
    private Animator animator;
    private GLU glu;
    double equation[] = {1f, 1f, 1f, 1f};
    private TextureHandler texture1, texture2;
    // The x position
    private float xpos;

    // The rotation value on the y axis
    private float yrot;

    // The z position
    private float zpos;
    float angle = 0.0f;

    private float heading;

    // Walkbias for head bobbing effect
    private float walkbias = 0.0f;

    // The angle used in calculating walkbias */
    private float walkbiasangle = 0.0f;

    // The value used for looking up or down pgup or pgdown
    private float lookupdown = 0.0f;
    // Define camera variables
    float cameraAzimuth = 0.0f, cameraSpeed = 0.0f, cameraElevation = 0.0f;

    // Set camera at (0, 0, -20)
    float cameraCoordsPosx = 0.0f, cameraCoordsPosy = 0.0f, cameraCoordsPosz = -20.0f;

    // Set camera orientation
    float cameraUpx = 0.0f, cameraUpy = 1.0f, cameraUpz = 0.0f;

    // Define an array to keep track of the key that was pressed
    private boolean[] keys = new boolean[250];

    private void initializeJogl() {


        // Adding the keyboard and mouse event listeners to the canvas.
        this.canvas.addKeyListener((java.awt.event.KeyListener) this);
        this.canvas.addMouseListener((java.awt.event.MouseListener) this);
        this.canvas.addMouseMotionListener(this);

    }

    public void keyReleased(KeyEvent event) {
        return;
    }

    public void keyTyped(KeyEvent event) {
        return;
    }

    public void mousePressed(MouseEvent event) {
        return;
    }

    public void mouseReleased(MouseEvent event) {
        return;
    }

    public void mouseClicked(MouseEvent event) {
        return;
    }

    public void mouseMoved(MouseEvent event) {
        return;
    }

    public void mouseDragged(MouseEvent event) {
        return;
    }

    public void mouseEntered(MouseEvent event) {
        return;
    }

    public void mouseExited(MouseEvent event) {
        return;
    }

    private void drawSphere(GL gl, GLU glu, float radius, boolean texturing) {

        if (texturing) {
            this.texture.bind();
            this.texture.enable();
        }

        GLUquadric sphere = glu.gluNewQuadric();
        if (texturing) {
            // Enabling texturing on the quadric.
            glu.gluQuadricTexture(sphere, true);
        }
        glu.gluSphere(sphere, radius, 64, 64);
        glu.gluDeleteQuadric(sphere);
    }

    public void moveCamera() {
        float[] tmp = polarToCartesian(cameraAzimuth, cameraSpeed, cameraElevation);

        // Replace old x, y, z coords for camera
        cameraCoordsPosx += tmp[0];
        cameraCoordsPosy += tmp[1];
        cameraCoordsPosz += tmp[2];
    }

    public void aimCamera(GL2 gl, GLU glu) {
        gl.glLoadIdentity();

        // Calculate new eye vector
        float[] tmp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation);

        // Calculate new up vector
        float[] camUp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation + 90);

        cameraUpx = camUp[0];
        cameraUpy = camUp[1];
        cameraUpz = camUp[2];

        glu.gluLookAt(cameraCoordsPosx, cameraCoordsPosy, cameraCoordsPosz,
                cameraCoordsPosx + tmp[0], cameraCoordsPosy + tmp[1],
                cameraCoordsPosz + tmp[2], cameraUpx, cameraUpy, cameraUpz);
    }

    private float[] polarToCartesian(float azimuth, float length, float altitude) {
        float[] result = new float[3];
        float x, y, z;

        // Do x-z calculation
        float theta = (float) Math.toRadians(90 - azimuth);
        float tantheta = (float) Math.tan(theta);
        float radian_alt = (float) Math.toRadians(altitude);
        float cospsi = (float) Math.cos(radian_alt);

        x = (float) Math.sqrt((length * length) / (tantheta * tantheta + 1));
        z = tantheta * x;

        x = -x;

        if ((azimuth >= 180.0 && azimuth <= 360.0) || azimuth == 0.0f) {
            x = -x;
            z = -z;
        }

        // Calculate y, and adjust x and z
        y = (float) (Math.sqrt(z * z + x * x) * Math.sin(radian_alt));

        if (length < 0) {
            x = -x;
            z = -z;
            y = -y;
        }

        x = x * cospsi;
        z = z * cospsi;

        // In contrast we could use the simplest form for computing Cartesian from Spherical as follows:
        // x = (float)(length * Math.sin(Math.toRadians(altitude))*Math.cos(Math.toRadians(azimuth)));
        // y = (float)(length * Math.sin(Math.toRadians(altitude))*Math.sin(Math.toRadians(azimuth)));
        // z = (float)(length * Math.cos(Math.toRadians(altitude)));


        result[0] = x;
        result[1] = y;
        result[2] = z;

        return result;
    }

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            cameraElevation -= 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            cameraElevation += 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            cameraAzimuth -= 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            cameraAzimuth += 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_I) {
            cameraSpeed += 0.05;
        }

        if (event.getKeyCode() == KeyEvent.VK_O) {
            cameraSpeed -= 0.05;
        }

        if (event.getKeyCode() == KeyEvent.VK_S) {
            cameraSpeed = 0;
        }

        if (cameraAzimuth > 359)
            cameraAzimuth = 1;

        if (cameraAzimuth < 1)
            cameraAzimuth = 359;
    }

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        float xTrans = -xpos;
        float yTrans = -walkbias - 0.43f;
        float zTrans = -zpos;
        float sceneroty = 360.0f - yrot;

        aimCamera(gl, glu);
        moveCamera();

        gl.glLoadIdentity();
        // Perform operations on the scene
        gl.glRotatef(lookupdown, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(sceneroty, 0.0f, 1.0f, 0.0f);

        gl.glTranslatef(xTrans, yTrans, zTrans);
        // Start with a fresh transformation i.e. the 4x4 identity matrix.
        gl.glLoadIdentity();

        // Save (push) the current matrix on the stack.
        gl.glPushMatrix();

        // Translate the first sphere to coordinates (0,0,0).
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        // Then draw it.
        this.drawSphere(gl, glu, 0.5f, false);

        // Save (push) on the stack the matrix holding the transformations produced by translating the first sphere.
        gl.glPushMatrix();

        // NOTE THE FOLLOWING ORDER OF OPERATIONS. THEY ACHIEVE A TRANSLATION FOLLOWED BY A ROTATION IN REALITY.

        // Rotate it with angle degrees around the X axis.
        gl.glRotatef(angle, 1, 0, 0);
        // Translate the second sphere to coordinates (10,0,0).
        gl.glTranslatef(10.0f, 0.0f, 0.0f);
        // Scale it to be half the size of the first one.
        gl.glScalef(0.5f, 0.5f, 0.5f);
        // Draw the second sphere.
        this.drawSphere(gl, glu, 0.5f, false);

        // Restore (pop) from the stack the matrix holding the transformations produced by translating the first sphere.
        gl.glPopMatrix();

        // Restore (pop) from the stack the matrix holding the transformations prior to our translation of the first sphere.
        gl.glPopMatrix();

        gl.glFlush();

        // Increase the angle of rotation by 5 degrees.
        angle += 5;
        if (keys[KeyEvent.VK_RIGHT]) {
            heading -= 3.0f;
            yrot = heading;
        }

        if (keys[KeyEvent.VK_LEFT]) {
            heading += 3.0f;
            yrot = heading;
        }

        if (keys[KeyEvent.VK_UP]) {

            xpos -= (float) Math.sin(Math.toRadians(heading)) * 0.1f; // Move On The X-Plane Based On Player Direction
            zpos -= (float) Math.cos(Math.toRadians(heading)) * 0.1f; // Move On The Z-Plane Based On Player Direction

            if (walkbiasangle >= 359.0f)
                walkbiasangle = 0.0f;
            else
                walkbiasangle += 10.0f;

            walkbias = (float) Math.sin(Math.toRadians(walkbiasangle)) / 20.0f; // Causes The Player To Bounce
        }

        if (keys[KeyEvent.VK_DOWN]) {

            xpos += (float) Math.sin(Math.toRadians(heading)) * 0.1f; // Move On The X-Plane Based On Player Direction
            zpos += (float) Math.cos(Math.toRadians(heading)) * 0.1f; // Move On The Z-Plane Based On Player Direction

            if (walkbiasangle <= 1.0f)
                walkbiasangle = 359.0f;
            else
                walkbiasangle -= 10.0f;

            walkbias = (float) Math.sin(Math.toRadians(walkbiasangle)) / 20.0f; // Causes The Player To Bounce
        }

        if (keys[KeyEvent.VK_PAGE_UP]) {
            lookupdown += 2.0f;
        }

        if (keys[KeyEvent.VK_PAGE_DOWN]) {
            lookupdown -= 2.0f;
        }
    }
}
