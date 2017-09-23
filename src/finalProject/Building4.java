package finalProject;



import com.jogamp.opengl.GL2;

public class Building4 {
	
	public String modelPath;
	public String texPath;
	
	float xTrans;
	float zTrans;
	public float rotate;
	
	public GLModel model = null;
	
	Building4(){
		modelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Building4/Building5.obj";
		texPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Building4/Building5.mtl";
		
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
		gl.glTranslatef(xTrans,45,zTrans);
		//System.out.println(xTrans + " " + zTrans + " b3");
		gl.glRotatef(rotate,0,1,0);
		gl.glScalef(6,6,6);
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
