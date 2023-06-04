//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//
//import Lab4.TextureHandler;
//import Lab4.TextureReader;
//import com.jogamp.opengl.*;
//import com.jogamp.opengl.awt.GLCanvas;
//import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
//import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.glu.GLUquadric;
//import com.jogamp.opengl.util.Animator;
//
//public class Lab6 extends JFrame implements GLEventListener {
//
//    private GLCanvas canvas;
//    private Animator animator;
//    private GLU glu;
//    double equation[] = {1f, 1f, 1f, 1f};
//    private TextureHandler texture1, texture2;
//
//    public Lab6() {
//        super("Java OpenGL");
//
//        this.setLayout(new BorderLayout());
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        this.setSize(800, 600);
//
//        // This method will be explained later
//        this.initializeJogl();
//
//        this.setVisible(true);
//    }
//
//    private final int NO_TEXTURES = 1;
//
//    private int texture[] = new int[NO_TEXTURES];
//    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];
//
//    // GLU object used for mipmapping.
//    private GLU glu;
//
//    private void initializeJogl() {
//        GLProfile glProfile = GLProfile.getDefault();
//        GLCapabilities capabilities = new GLCapabilities(glProfile);
//
//        this.glu = new GLU();
//
//        capabilities.setHardwareAccelerated(true);
//        capabilities.setDoubleBuffered(true);
//
//        this.canvas = new GLCanvas();
//
//        this.getContentPane().add(this.canvas);
//
//        this.canvas.addGLEventListener(this);
//
//        this.animator = new Animator();
//
//        this.animator.add(this.canvas);
//
//        this.animator.start();
//    }
//
//    @Override
//    public void init(GLAutoDrawable glAutoDrawable) {
//        GL2 gl = canvas.getGL().getGL2();
//
//        gl.glClearColor(0, 0, 0, 0);
//
//        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
//
//        gl.glLoadIdentity();
//        int height = 600, width = 800;
//        gl.glOrtho(0, width, 0, height, -1, 1);
//
//        gl.glEnable(GL2.GL_CLIP_PLANE1);
//
//        gl.glClipPlane(GL2.GL_CLIP_PLANE1, equation, 0);
//
//
//        // Bind (select) the texture.
//        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);
//
//
//        texture1 = new TextureHandler(gl, glu, "texture1.jpg", true);
//        texture2 = new TextureHandler(gl, glu, "texture2.jpg", true);
//
//        gl.glShadeModel(gl.GL_SMOOTH);
//
//        // Activate the depth test and set the depth function.
//        gl.glEnable(GL.GL_DEPTH_TEST);
//        gl.glDepthFunc(GL.GL_LESS);
//
//        // Set The Texture Generation Mode For S To Sphere Mapping (NEW)
//        gl.glTexGeni(gl.GL_S, gl.GL_TEXTURE_GEN_MODE, gl.GL_SPHERE_MAP);
//        // Set The Texture Generation Mode For T To Sphere Mapping (NEW)
//        gl.glTexGeni(gl.GL_T, gl.GL_TEXTURE_GEN_MODE, gl.GL_SPHERE_MAP);
//
//        gl.glEnable(GL2.GL_LIGHTING);
//        gl.glEnable(GL2.GL_LIGHT0);
//        gl.glEnable(GL2.GL_LIGHT1);
//
//        gl.glEnable(GL2.GL_COLOR_MATERIAL);
//        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
//
//    }
//
//
//    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
//        if (mipmapped) {
//            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
//        } else {
//            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
//        }
//    }
//
//    @Override
//    public void dispose(GLAutoDrawable glAutoDrawable) {
//
//    }
//
//    public void afisarePoza(String path) {
//
//        GL2 gl = canvas.getGL().getGL2();
//        gl.glEnable(GL.GL_BLEND);
//
//        glu = GLU.createGLU();
//
//        // Generate a name (id) for the texture.
//        // This is called once in init no matter how many textures we want to generate in the texture vector
//        gl.glGenTextures(NO_TEXTURES, texture, 0);
//
//        // Define the filters used when the texture is scaled.
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
//
//        // Do not forget to enable texturing.
//        gl.glEnable(GL.GL_TEXTURE_2D);
//        try {
//            tex[0] = TextureReader.readTexture(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//        // Construct the texture and use mipmapping in the process.
//        this.makeRGBTexture(gl, glu, tex[0], GL.GL_TEXTURE_2D, true);
//
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
//    }
//
//
//    @Override
//    public void display(GLAutoDrawable canvas) {
//        GL gl = canvas.getGL();
//        texture1.bind();
//        texture1.enable();
//        // Draw the object you wish to apply the first texture to.
//
//        texture2.bind();
//        texture2.enable();
//
//        float sun_glow[] = {1.7f, 1.5f, 1.0f, 1.0f};
//        gl.glPushAttrib(GL2.GL_CURRENT_BIT);
//        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, sun_glow, 0);
//        GLUquadric q = glu.gluNewQuadric();
//        glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
//        glu.gluQuadricNormals(q, GLU.GLU_SMOOTH);
//        glu.gluSphere(q, 34, 40, 40);
//        glu.gluDeleteQuadric(q);
//        gl.glPopAttrib();
//        // The vector arguments represent the R, G, B, A values.
//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, new float [] {0.2f, 0.0f, 0.0f, 1f}, 0);
//
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float [] {0.9f, 0.9f, 0.9f, 1f}, 0);
//        // The vector arguments represent the x, y, z, w values of the position.
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float [] {-10, 0, 0, 1f}, 0);
//
//        GLUquadric sphere = glu.gluNewQuadric();
//        // Enabling texturing on the quadric.
//        glu.gluQuadricTexture(sphere, true);
//        glu.gluSphere(sphere, 0.5, 64, 64);
//        glu.gluDeleteQuadric(sphere);
//
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
//        // Clear the depth buffer.
//        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
//
//        GLUquadric sun = glu.gluNewQuadric ();
//        glu.gluSphere (sun, 0.3, 32, 32);
//        glu.gluDeleteQuadric (sun);
//        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
//        // Enable Texture Coord Generation For T (NEW)
//        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
//        //draw the object
//
//        gl.glLightf( GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 60.0F );
//        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, spotDirection );
//
//
//        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float [] {0.8f, 0.8f, 0.0f, 1f}, 0);
//        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float [] {0.8f, 0.8f, 0.0f, 1f}, 0);
//        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float [] {0.8f, 0.8f, 0.0f, 1f}, 0);
//        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float [] {0.5f, 0.5f, 0f, 1f}, 0);
//
//    }
//
//    @Override
//    public void reshape(GLAutoDrawable glAutoDrawable, int left, int top, int width, int height) {
//        GL2 gl = canvas.getGL().getGL2();
//
//        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
//
//
//        gl.glLoadIdentity();
//
//        double ratio = (double) width / (double) height;
//        glu.gluPerspective(38, ratio, 0.1, 100);
//
//        gl.glViewport(0, 0, width, height);
//
//        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
//    }
//}