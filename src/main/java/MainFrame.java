
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import Lab4.TextureReader;
import Lab5.astro.PolarProjectionMap;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

public class MainFrame extends JFrame implements GLEventListener {

    private GLCanvas canvas;
    private Animator animator;
    double equation[] = {1f, 1f, 1f, 1f};

    private GLUT glut;

    private PolarProjectionMap ppm = null;
    // Used to identify the display list.
    private int ppm_list;

    public MainFrame() {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    private final int NO_TEXTURES = 1;

    private int texture[] = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];

    // GLU object used for mipmapping.
    private GLU glu;

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

    // We use this method for creating the display list.
    private void makePPM(GL2 gl) {
        final ArrayList<PolarProjectionMap.ConstellationLine> clLines = this.ppm.getConLines();
        // Add here the rest of the ArrayLists.

        gl.glColor3f(0.0f, 1.0f, 0.0f);

        gl.glBegin(GL2.GL_LINES);
        for (PolarProjectionMap.ConstellationLine cl : clLines) {
            if (cl.isVisible()) {
                gl.glVertex2d(cl.getPosX1(), cl.getPosY1());
                gl.glVertex2d(cl.getPosX2(), cl.getPosY2());
            }
        }
        gl.glEnd();

        // Add here the rest of the code for rendering constellation boundaries (use GL_LINES),
        // names (use glutBitmapString), stars (use GL_POINTS) and cardinal points (use glutBitmapString).
        for (PolarProjectionMap.ConstellationName cl : ppm.getConNames()) {
            if (cl.isVisible()) {
                gl.glRasterPos2d(cl.getPosX(), cl.getPosY());
                // Render the text in the scene.
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, cl.getName());

            }
        }
        gl.glBegin(GL2.GL_POINTS);
        for (PolarProjectionMap.ConstellationStar cl : ppm.getConStars()) {
            if (cl.isVisible()) {
                gl.glVertex2d(cl.getPosX(), cl.getPosY());

            }
        }
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        for (PolarProjectionMap.ConstellationBoundaryLine cl : ppm.getConBoundaryLines()) {
            if (cl.isVisible()) {
                gl.glVertex3d(cl.getPosX1(), cl.getPosY1(),cl.getPosZ1());
                gl.glVertex3d(cl.getPosX2(), cl.getPosY2(),cl.getPosZ2());

            }
        }
        gl.glEnd();

        for (PolarProjectionMap.MessierData cl : ppm.getMessierObjects()) {
            if (cl.isVisible()) {
                gl.glRasterPos2d(cl.getX(), cl.getY());
                // Render the text in the scene.
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, cl.getName());
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, String.valueOf(cl.getMagnitude()));
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, String.valueOf(cl.getDec()));
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, String.valueOf(cl.getRA()));

            }
        }
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


        // Bind (select) the texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);


        glut = new GLUT();

        // Initialize the object.
        this.ppm = new PolarProjectionMap(21.53, 45.17);
        // Set the separator for the line fields.
        this.ppm.setFileSep(",");
        // Read the file and compute the coordinates.
        this.ppm.initializeConstellationLines("data/conlines.dat");
        this.ppm.initializeConstellationStars("data/beyer.dat");
        this.ppm.initializeConstellationNames("data/cnames.dat");
        this.ppm.initializeConstellationBoundaries("data/cbounds.dat");
        this.ppm.initializeMessierObjects("data/messier.dat");
        // Initialize here the rest of the elements from the remaining files using the corresponding methods.

        // Create the display list.
        this.ppm_list = gl.glGenLists(1);
        gl.glNewList(this.ppm_list, gl.GL_COMPILE);
        this.makePPM(gl);
        gl.glEndList();



    }


    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = canvas.getGL().getGL2();

// Specify the raster position.
        gl.glRasterPos2d(0.5, 0.5);
        // Render the text in the scene.
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Hello World");

        gl.glCallList(this.ppm_list);

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
            gl.glOrtho(0, 1, 0, 1, -1, 1);
        }

        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }
}