package finalProject;

import com.jogamp.opengl.GL2;

public class ChryslerBuilding {
	
	public String modelPath;
	public String texPath;
	
	public GLModel model = null;
	
	ChryslerBuilding(){
		modelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ChryslerBuilding/triangulatedChrys.obj";
		texPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ChryslerBuilding/triangulatedChrys.mtl";
	}
	
	public boolean loadModels(GL2 gl) {
		model = ModelLoaderOBJ.LoadModel(modelPath, texPath, gl);
		if (model == null) {
			return false;
		}
		
	
		return true;
	}
	
	public void draw(GL2 gl){
		gl.glPushMatrix();
		gl.glTranslatef(216.6f,165,0);
		gl.glScalef(3f,3f,3f);
		model.opengldraw(gl);
		gl.glPopMatrix();
	}

}
