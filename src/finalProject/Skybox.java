package finalProject;

import com.jogamp.opengl.GL2;

public class Skybox {
	public static final int NUM_FACES = 6;
	public static final String PATH_TO_TEXTURES = "src/SkyBox/";
	public static final String[] SKYBOX_SUFFIXES = {
		"ThickCloudsWaterFront2048.png", "ThickCloudsWaterBack2048.png",
		"ThickCloudsWaterLeft2048.png", "ThickCloudsWaterRight2048.png",
		"ThickCloudsWaterUp2048.png", "ThickCloudsWaterDown2048.png"
	};
	protected TextureLoader texture_loader = null;
	private String skybox_name = null;
	private int[] textures = new int[ NUM_FACES ];
	
	public Skybox( TextureLoader texture_loader, String skybox_name ) {
		this.texture_loader = texture_loader;
	
		loadTextures();
	}
	
	protected void loadTextures() {
		
		
		for ( int i = 0; i < NUM_FACES; ++i ) {
			textures[ i ] = texture_loader.generateTexture();
			
			try {
				texture_loader.loadTexture( textures[ i ], PATH_TO_TEXTURES
					+ "/" + SKYBOX_SUFFIXES[ i ] );
			} catch ( Exception e ) {
				System.err.println( "Unable to load texture: " + e.getMessage() );
			}
		}
	}
	
	public String getSkyboxName() {
		return skybox_name;
	}
	
	public void draw( GL2 gl, float size ) {
		final float d = size / 2.0f;
		
		// Front
		gl.glBindTexture( GL2.GL_TEXTURE_2D, textures[ 0 ] );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glTexCoord2f( 0.0f, 1.0f );
		gl.glVertex3f( d, -d, d );
		
		gl.glTexCoord2f( 0.0f, 0.0f );
		gl.glVertex3f( d, -d, -d );
		
		gl.glTexCoord2f( 1.0f, 0.0f );
		gl.glVertex3f( d, d, -d );
		
		gl.glTexCoord2f( 1.0f, 1.0f );
		gl.glVertex3f( d, d, d );
		
		gl.glEnd();
		
		// Back
		gl.glBindTexture( GL2.GL_TEXTURE_2D, textures[ 1 ] );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glTexCoord2f( 0.0f, 1.0f );
		gl.glVertex3f( -d, d, d );
		
		gl.glTexCoord2f( 0.0f, 0.0f );
		gl.glVertex3f( -d, d, -d );
		
		gl.glTexCoord2f( 1.0f, 0.0f );
		gl.glVertex3f( -d, -d, -d );
		
		gl.glTexCoord2f( 1.0f, 1.0f );
		gl.glVertex3f( -d, -d, d );
		
		gl.glEnd();
		
		// Left
		gl.glBindTexture( GL2.GL_TEXTURE_2D, textures[ 2 ] );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glTexCoord2f( 0.0f, 1.0f );
		gl.glVertex3f( d, d, d );
		
		gl.glTexCoord2f( 0.0f, 0.0f );
		gl.glVertex3f( d, d, -d );
		
		gl.glTexCoord2f( 1.0f, 0.0f );
		gl.glVertex3f( -d, d, -d );
		
		gl.glTexCoord2f( 1.0f, 1.0f );
		gl.glVertex3f( -d, d, d );
		
		gl.glEnd();
		
		// Right
		gl.glBindTexture( GL2.GL_TEXTURE_2D, textures[ 3 ] );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glTexCoord2f( 0.0f, 1.0f );
		gl.glVertex3f( -d, -d, d );
		
		gl.glTexCoord2f( 0.0f, 0.0f );
		gl.glVertex3f( -d, -d, -d );
		
		gl.glTexCoord2f( 1.0f, 0.0f );
		gl.glVertex3f( d, -d, -d );
		
		gl.glTexCoord2f( 1.0f, 1.0f );
		gl.glVertex3f( d, -d, d );
		
		gl.glEnd();
		
		// Up
		gl.glBindTexture( GL2.GL_TEXTURE_2D, textures[ 4 ] );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glTexCoord2f( 0.0f, 1.0f );
		gl.glVertex3f( -d, -d, d );
		
		gl.glTexCoord2f( 0.0f, 0.0f );
		gl.glVertex3f( d, -d, d );
		
		gl.glTexCoord2f( 1.0f, 0.0f );
		gl.glVertex3f( d, d, d );
		
		gl.glTexCoord2f( 1.0f, 1.0f );
		gl.glVertex3f( -d, d, d );
		
		gl.glEnd();
		
		// Down
		gl.glBindTexture( GL2.GL_TEXTURE_2D, textures[ 5 ] );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glTexCoord2f( 0.0f, 1.0f );
		gl.glVertex3f( d, -d, -d );
		
		gl.glTexCoord2f( 0.0f, 0.0f );
		gl.glVertex3f( -d, -d, -d );
		
		gl.glTexCoord2f( 1.0f, 0.0f );
		gl.glVertex3f( -d, d, -d );
		
		gl.glTexCoord2f( 1.0f, 1.0f );
		gl.glVertex3f( d, d, -d );
		
		gl.glEnd();
	}
}
