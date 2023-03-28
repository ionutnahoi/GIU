
import javax.swing.*;
import java.awt.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;

import static java.lang.Math.PI;

public class Lab2 extends JFrame implements GLEventListener {

    private GLCanvas canvas;
    private Animator animator;
    double equation[] = {1f, 1f, 1f, 1f};

    public Lab2() {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        this.canvas = new GLCanvas();

        this.getContentPane().add(this.canvas);

        this.canvas.addGLEventListener(this);

        this.animator = new Animator();

        this.animator.add(this.canvas);

        this.animator.start();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClearColor(0, 0, 0, 0);

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();
        int height = 600, width = 800;
        gl.glOrtho(0, width, 0, height, -1, 1);

        gl.glEnable(GL2.GL_CLIP_PLANE1);

        gl.glClipPlane(GL2.GL_CLIP_PLANE1, equation, 0);

//        gl.glEnable(GL.GL_LINE_SMOOTH);

        // Activate the GL_BLEND state variable. Means activating blending.
        gl.glEnable(GL.GL_BLEND);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void test() {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(0.5f);

        gl.glLineWidth(0.5f);

        gl.glLineStipple(1, (short) 0x3f07);
        gl.glEnable(GL2.GL_LINE_STIPPLE);

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2f(0.2f, 0.2f);

        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2f(0.4f, 0.2f);

        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex2f(0.2f, 0.4f);

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glEnd();

        gl.glDisable(GL2.GL_LINE_STIPPLE);

        //gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glFlush();
    }

    public void square1() {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(0.5f);

        gl.glLineWidth(0.5f);

        //  |
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(0.1f, 0.5f, 0.0f);
        gl.glVertex3f(0.1f, 0.1f, 0.0f);
        gl.glColor3f(1.f, 0.f, 0.f);
        gl.glEnd();

        // |_
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(0.5f, 0.1f, 0.0f);
        gl.glVertex3f(0.1f, 0.1f, 0.0f);
        gl.glEnd();

        // |=
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(0.1f, 0.5f, 0.0f);
        gl.glVertex3f(0.5f, 0.5f, 0.0f);
        gl.glEnd();

        // |=|
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(0.5f, 0.5f, 0.0f);
        gl.glVertex3f(0.5f, 0.1f, 0.0f);
        gl.glEnd();


    }

    public void square2() {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(0.5f);

        gl.glLineWidth(0.5f);

        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glVertex3f(0.1f, 0.5f, 0.0f);
        gl.glVertex3f(0.1f, 0.1f, 0.0f);
        gl.glVertex3f(0.5f, 0.1f, 0.0f);
        gl.glVertex3f(0.5f, 0.5f, 0.0f);
        gl.glVertex3f(0.1f, 0.5f, 0.0f);
        gl.glEnd();
    }

    public void square3() {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(0.5f);

        gl.glLineWidth(0.5f);

        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex3f(0.1f, 0.1f, 0.0f);
        gl.glVertex3f(0.5f, 0.1f, 0.0f);
        gl.glVertex3f(0.5f, 0.5f, 0.0f);
        gl.glVertex3f(0.1f, 0.5f, 0.0f);
        gl.glEnd();
    }

    void drawCircle() {
        int circle_points = 360;
        GL2 gl = canvas.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(0.1f);

        gl.glLineWidth(0.1f);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        double angle = 2 * PI / circle_points;
        gl.glBegin(GL2.GL_LINE_LOOP);
        double angle1 = 0.0;
        gl.glVertex2d(Math.cos(0.0), Math.sin(0.0));
        int i;
        for (i = 0; i < circle_points; i++) {
//            printf( "angle = %f \n" , angle1);
            gl.glVertex2d(Math.cos(angle1), Math.sin(angle1));
            angle1 += angle;
        }
        gl.glEnd();
    }

    void drawCircle(float cx, float cy, float r, int num_segments) {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(0.1f);

        gl.glLineWidth(0.1f);

        float theta = (float) 3.1415926 * 2 / num_segments;

        float tangetial_factor = (float) Math.tan(theta);//calculate the tangential factor

        float radial_factor = (float) Math.cos(theta);//calculate the radial factor

        float x = r;//we start at angle = 0

        float y = 0;
        gl.glLineWidth(2);
        gl.glBegin(GL2.GL_LINE_LOOP);
        for (int ii = 0; ii < num_segments; ii++) {
            gl.glVertex2f(x + cx, y + cy);//output vertex

            //calculate the tangential vector
            //remember, the radial vector is (x, y)
            //to get the tangential vector we flip those coordinates and negate one of them

            float tx = -y;
            float ty = x;

            //add the tangential vector

            x += tx * tangetial_factor;
            y += ty * tangetial_factor;

            //correct using the radial factor

            x *= radial_factor;
            y *= radial_factor;
        }
        gl.glEnd();
    }

    public void polygon() {
        GL2 gl = canvas.getGL().getGL2();
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(1.f, 0.f, 0.f);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glColor3f(0.f, 1.f, 0.f);
        gl.glVertex2f(0.2f, 0.4f);
        gl.glColor3f(0.f, 0.f, 1.f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glColor3f(1.f, 1.f, 1.f);
        gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
//        test();
//        square1();
//        square2();
//        square3();
//        drawCircle();
//        drawCircle(250, 250, 100, 360);
        polygon();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glViewport(0, 0, width, height);

        double ratio = (double) width / (double) height;

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();

        if (ratio < 1) {
            gl.glOrtho(0, 1, 0, 1 / ratio, -1, 1);
        } else {
            gl.glOrtho(0, 5 , 0, 5, -1, 1);
        }

        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }
}