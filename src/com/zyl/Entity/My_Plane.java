package com.zyl.Entity;

import com.zyl.Config.Image_Config;

import javafx.scene.image.Image;

public class My_Plane extends FlyingObject{
	private Image[] images= {};
	private int index=0;//图片索引切换
	private int doublefire;//双倍火力
	private int life;//玩家的生命
	
	public My_Plane() {
		this.life=3;//初始有三条命
		this.doublefire=0;
		images=new Image[] {
				Image_Config.my_plane0,
				Image_Config.my_plane1
		};//飞机图片组
		image=Image_Config.my_plane0;//初始化图片
		width=(int) image.getWidth();//得到图片的长和高
		height=(int) image.getHeight();
		x=150;//设置初始位置
		y=400;
	}
	/**越界处理*/
	public boolean outOfBound() {
		return false;
	}
	/**获得双倍火力*/
	public int getDoublefire() {
		return doublefire;
	}
	/**设置双倍火力*/
	public void setDoublefire(int doublefire) {
		this.doublefire = doublefire;
	}
	/**增加火力*/
	public void addDoublefire() {
		doublefire=40;
	}
	/**增加生命*/
	public void addLife() {
		life++;
	}
	public void substractLife() {
		life--;
	}
	public int getLife() {//获得生命
		return life;
	}
	//使得鼠标在飞机的正中心
	//width和height是飞机的尺寸,x,y为鼠标的位置
	public void MoveTo(int x,int y) {
		this.x=x-width/2;
		this.y=y-height/2;
	}
	//飞机的子弹发射
/*	public ArrayList<Bullet> shoot() {
		int xStep=width/4;
		int yStep=20;
		if(doublefire>0) {//这里的x,y是飞机的左上角坐标
			ArrayList<Bullet> bullets=new ArrayList<>();
			bullets.add(0,new Bullet(x+xStep,y-yStep));
			bullets.add(1,new Bullet(x+3*xStep,y-yStep));//yStep为子弹距离飞机的位置
			return bullets;
		}
		else {
			ArrayList<Bullet> bullets=new ArrayList<>();
			bullets.add(0, new Bullet(super.x+2*xStep,super.y-yStep));
			return bullets;
			}
	}*/
	/**产生飞机飞行的动态效果**/
	public void step() {
		if(images.length>0)
			image=images[index++%images.length];
	}
	/**碰撞算法*/
	public boolean hit(FlyingObject other) {
		//飞机的中心位置
		int my_planex=this.x+this.width/2;
		int my_planey=this.y+this.height/2;
		
		int miniX=other.x-this.width/2;//x坐标的最小值，从刚好碰到
		int maxX=other.x+other.width+this.width/2;//x坐标的最大值，在右边刚好碰到
		int miniY=other.y-this.height/2;
		int maxY=other.y+other.height+this.height/2;
		//飞机中心坐标在这个范围内就是撞上了
		return my_planex>miniX&&my_planex<maxX&&my_planey>miniY&&my_planey<maxY;
	}
	public Bullet[] shoot(){   
		int xStep = width/4;      //4半
		int yStep = 20;  //步
		if(doublefire>0){  //双倍火力
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(x+xStep,y-yStep);  //y-yStep(子弹距飞机的位置)
			bullets[1] = new Bullet(x+3*xStep,y-yStep);
			return bullets;
		}else{      //单倍火力
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(x+2*xStep,y-yStep);  
			return bullets;
		}
	}
}
