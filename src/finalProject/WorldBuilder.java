package finalProject;

import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;

public class WorldBuilder {
	ChryslerBuilding chrys;
	
	Vertex v1;
	Vertex v2;
	Vertex v3;
	Vertex v4;
	Vertex v5;
	Vertex v6;
	Vertex v7;
	Vertex v8;
	Vertex v9;
	Vertex v10;
	Vertex v11;
	Vertex v12;
	Vertex v13;
	Vertex v14;
	Vertex v15;
	Vertex v16;
	Vertex v17;
	Vertex v18;
	Vertex v19;
	Vertex v20;
	Vertex v21;
	Vertex v22;
	Vertex v23;
	Vertex v24;
	
	ArrayList<Vertex> locations;
	ArrayList<Building> b1;
	ArrayList<Building2> b2;
	ArrayList<Building3> b3;
	ArrayList<Building4> b4;
	ArrayList<Building5> b5;
	Random rand;
	
	
	 
	public WorldBuilder(){
		 v1 = new Vertex(-433.2f,0,433.2f);
		 v2 = new Vertex(-216.6f,0,433.2f);
		 v3 = new Vertex(0,0,433.2f);
		// v4 = new Vertex(216.6f,0,433.2f);
		 v5 = new Vertex(433.2f,0,433.2f);
		 
		 
		 v6 = new Vertex(-433.2f,0,216.6f);
		 //v7 = new Vertex(0,0,216.6f);
		 v8 = new Vertex(216.6f,0,216.6f);
		 v9 = new Vertex(433.2f,0,216.6f);
		 
		 
		// v10 = new Vertex(-433.2f,0,0);
		 v11 = new Vertex(-216.6f,0,0);
		 //v12 = new Vertex(216.6f,0,0);
		// v13 = new Vertex(433.2f,0,0);
		 
		 
		 v14 = new Vertex(-433.2f,0,-216.6f);
		// v15 = new Vertex(-216.6f,0,-216.6f);
		 v16 = new Vertex(0,0,-216.6f);
		 //v17 = new Vertex(216.6f,0,-216.6f);
		 v18 = new Vertex(433.2f,0,-216.6f);
		 
		 
		// v19 = new Vertex(-433.2f,0,-433.2f);
		 v20 = new Vertex(-216.6f,0,-433.2f);
		 v21 = new Vertex(0,0,-433.2f);
		 v22 = new Vertex(216.6f,0,-433.2f);
		// v23 = new Vertex(433.2f,0,-433.2f);
		 
		rand = new Random();
		chrys = new ChryslerBuilding();
		
		locations = new ArrayList<Vertex>();
		locations.add(v1);
		//locations.add(v2);
		locations.add(v3);
		//locations.add(v4);
		locations.add(v5);
		locations.add(v6);
		//locations.add(v7);
		locations.add(v8);
		locations.add(v9);
		//locations.add(v10);
		locations.add(v11);
		//locations.add(v12);
		//locations.add(v13);
		locations.add(v14);
		//locations.add(v15);
		locations.add(v16);
		//locations.add(v17);
		locations.add(v18);
		//locations.add(v19);
		locations.add(v20);
		//locations.add(v21);
		locations.add(v22);
		//locations.add(v23);
		//locations.add(v24);
		
		b1 = new ArrayList<Building>();
		b2 = new ArrayList<Building2>();
		b3 = new ArrayList<Building3>();
		b4 = new ArrayList<Building4>();
		b5 = new ArrayList<Building5>();
		
		
	}
	
	public float randomRotate(){
		int rotate = 0;
		rotate = rand.nextInt(5);
		if(rotate == 0){;
			return rotate;
		}
		else if(rotate == 3){
			return 270;
		}
		else{
			return 360/rotate;
		}
	}
	
	public boolean createPark(GL2 gl){

		
		return true;
	}
	
	public boolean initializeWorld(GL2 gl){
		
		createPark(gl);
		
		if(false ==  chrys.loadModels(gl)){
			return false;
		}
		for(int i = 0; i < locations.size()-1; i++){
			int index = rand.nextInt(4);
			if(index ==0){
				Building build1 = new Building();
				b1.add(build1);
				build1.setZTrans(locations.get(i).getZ());
				build1.setXTrans(locations.get(i).getX());
				build1.setRot(randomRotate());
				if(!build1.loadModels(gl)){
					System.out.println("Model Not Loaded");
					return false;
				}
			}
			else if(index == 1){
				Building2 build2 = new Building2();
				b2.add(build2);
				build2.setZTrans(locations.get(i).getZ());
				build2.setXTrans(locations.get(i).getX());
				build2.setRot(randomRotate());
				if(!build2.loadModels(gl)){
					System.out.println("Model Not Loaded");
					return false;
				}
			}
			else if(index ==2){
				Building3 build3 = new Building3();
				b3.add(build3);
				build3.setZTrans(locations.get(i).getZ());
				build3.setXTrans(locations.get(i).getX());
				build3.setRot(randomRotate());
				if(!build3.loadModels(gl)){
					System.out.println("Model Not Loaded");
					return false;
				}
			}
			else if(index == 3){
				Building4 build4 = new Building4();
				b4.add(build4);
				build4.setZTrans(locations.get(i).getZ());
				build4.setXTrans(locations.get(i).getX());
				build4.setRot(randomRotate());
				if(!build4.loadModels(gl)){
					System.out.println("Model Not Loaded");
					return false;
				}
			}
			/*
			if(index ==4){
				Building5 build5 = new Building5();
				b5.add(build5);
				build5.setZTrans(locations.get(i).getZ());
				build5.setXTrans(locations.get(i).getX());
				build5.setRot(randomRotate());
				if(!build5.loadModels(gl)){
					System.out.println("Model Not Loaded");
					return false;
				}
				
			}*/
		}
		return true;
	}
	
	public void drawWorld(GL2 gl){
		gl.glPushMatrix();
		gl.glRotated(90, 1, 0, 0);
		
		chrys.draw(gl);
		
		for(int i = 0; i<b1.size(); i++){
			b1.get(i).draw(gl);
		}
		for(int i = 0; i<b2.size(); i++){
			b2.get(i).draw(gl);
		}
		for(int i = 0; i<b3.size(); i++){
			b3.get(i).draw(gl);
		}
		for(int i = 0; i<b4.size(); i++){
			b4.get(i).draw(gl);
		}
		for(int i = 0; i<b5.size(); i++){
			b5.get(i).draw(gl);
		}
		
		gl.glPopMatrix();
				

			
	}
	

		

	
	
}
