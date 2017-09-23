package finalProject;



import com.jogamp.opengl.GL2;

public class Building {
	
	public String modelPath;
	public String texPath;
	public float xTrans;
	public float zTrans;
	public float rotate;
	
	public GLModel model = null;
	
	Building(){
		modelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Building/Building.obj";
		texPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Building/Building.mtl";
		xTrans = 0;
		zTrans = 0;
		rotate = 0;
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
		//System.out.println(xTrans + " " + zTrans + " b1");
		gl.glTranslatef(xTrans,90,zTrans);
		gl.glRotatef(rotate,0,1,0);
		gl.glScalef(3f,3f,3f);
		model.opengldraw(gl);
		gl.glPopMatrix();
	}
	
	public void setXTrans(float x){
		xTrans = x;
	}
	public void setZTrans(float z){
		zTrans = z;
	}
	public void setRot(float r){
		rotate = r;
	}

}