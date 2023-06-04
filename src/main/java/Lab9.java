//package Lab4;
//
//import javax.swing.*;
//import java.awt.*;
//
//import com.jogamp.opengl.*;
//import com.jogamp.opengl.awt.GLCanvas;
//import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
//import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.util.Animator;
//
//public class Lab9 extends JFrame implements GLEventListener {
//
//    private GLCanvas canvas;
//    private Lab9 model;
//
//    private TextureHandler texture3DS;
//    private double camCx;
//    private double camCy;
//    private double camCz;
//
//    // Billboard angle, speed, direction and distance from the viewport.
//    private double bbAngle = 0;
//    private double bbAngleDirection = 1;
//    private double bbAngleSpeed = 0.5;
//    private double bbDistance = 25;
//
//    private double bbCx;
//    private double bbCy;
//    private double bbCz;
//
//    private double[] bbV1;
//    private double[] bbV2;
//    private double[] bbV3;
//    private double[] bbV4;
//    private Animator animator;
//    double equation[] = {1f, 1f, 1f, 1f};
//
//    public Lab9() {
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
//
//    @Override
//    public void display(GLAutoDrawable glAutoDrawable) {
//        GL2 gl = canvas.getGL().getGL2();
//
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
//        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
//
//        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
//        gl.glLoadIdentity();
//
//        gl.glTranslated(0, 0, -4);
//
//        gl.glPushMatrix();
//
//        gl.glScaled(1.0 / 300, 1.0 / 300, 1.0 / 300);
//
//        gl.glRotated(this.angle * 1.1, 1, 0, 0);
//        gl.glRotated(this.angle * 1.2, 0, 1, 0);
//        gl.glRotated(this.angle * 1.3, 0, 0, 1);
//
//        //This method has to be implemented by the developer when using JOGLUtils.
//        this.renderObject(gl, this.model);
//
//
//        gl.glPopMatrix();
//
//        gl.glFlush();
//
//        this.angle += 1;
//    }
//
//    private void updateCoordinates() {
//        // The lines between BEGIN and END are for moving the billboard. For fixed objects ignore them and just set bbCx, bbCy, bbCz to some values
//        // BEGIN
//        this.bbAngle += this.bbAngleDirection * this.bbAngleSpeed;
//
//        if (this.bbAngle > 13)
//            this.bbAngleDirection = -1;
//        if (this.bbAngle < -13)
//            this.bbAngleDirection = 1;
//
//        this.bbDistance = 10;
//
//        this.bbCx = Math.cos(Math.toRadians(90 + this.bbAngle)) * this.bbDistance;
//        this.bbCy = 0;
//        this.bbCz = Math.sin(Math.toRadians(90 + this.bbAngle)) * this.bbDistance;
//        // END
//
//        this.camCx = 0;
//        this.camCy = 0;
//        this.camCz = 0;
//
//        // Compute the billboard vertices based on its center (bbCx, bbCy, bbCz)
//        this.bbV1 = new double[]{this.bbCx - 1, this.bbCy - 1, this.bbCz};
//        this.bbV2 = new double[]{this.bbCx + 1, this.bbCy - 1, this.bbCz};
//        this.bbV3 = new double[]{this.bbCx + 1, this.bbCy + 1, this.bbCz};
//        this.bbV4 = new double[]{this.bbCx - 1, this.bbCy + 1, this.bbCz};
//
//        // Compute the billboard-camera vector. Its x,y,z components will be used to get the angles of rotation
//        double deltaX = this.camCx - this.bbCx;
//        double deltaY = this.camCy - this.bbCy;
//        double deltaZ = this.camCz - this.bbCz;
//
//        // Convert cartesian coordinates to polar coordinates, i.e., get the angles of rotation
//        double alpha = Math.atan2(deltaZ, deltaX) - Math.PI / 2.0;
//        double beta = Math.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ));
//
//        // Perform rotation
//        this.bbV1 = this.rotate(this.bbV1, alpha, beta);
//        this.bbV2 = this.rotate(this.bbV2, alpha, beta);
//        this.bbV3 = this.rotate(this.bbV3, alpha, beta);
//        this.bbV4 = this.rotate(this.bbV4, alpha, beta);
//    }
//
//    // Method for rotating a point represented by a vertex given its alpha (latitude) and beta (longitude) angles
//    private double[] rotate(double[] v, double alpha, double beta) {
//        double y = v[1] * Math.cos(beta) + v[2] * Math.sin(beta);
//        double z = -v[1] * Math.sin(beta) + v[2] * Math.cos(beta);
//        double x = v[0] * Math.cos(alpha) - z * Math.sin(alpha);
//        z = v[0] * Math.sin(alpha) + z * Math.cos(alpha);
//        return (new double[]{x, y, z});
//    }
//
//    @Override
//    public void reshape(GLAutoDrawable glAutoDrawable, int left, int top, int width, int height) {
//        GL2 gl = canvas.getGL().getGL2();
//
//        gl.glViewport(0, 0, width, height);
//
//        double ratio = (double) width / (double) height;
//
//        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
//
//        gl.glLoadIdentity();
//
//        if (ratio < 1) {
//            gl.glOrtho(0, 1, 0, 1 / ratio, -1, 1);
//        } else {
//            gl.glOrtho(0, 15, 0, 15, -1, 1);
//        }
//
//        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
//    }
//}