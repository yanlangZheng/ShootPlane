package com.zyl.Entity;

import com.zyl.Config.Image_Config;
public class Bullet extends FlyingObject{
	private int speed =3;//初始移动速度
	private int crash=-1;
	/**初始化数据*/
	public Bullet(int x,int y) {
		this.x=x;
		this.y=y;
		this.image=Image_Config.bullet;
	}
	
	/**越界处理 */
	public boolean outOfBound() {
		return y<-height;
	}
	/**移动*/
	public void step() {//往上移动所以y减小
		y-=speed;	
	}
	
	public void setCrash() {
		this.crash=1;
	}
	public int getCrash() {
		return crash;
	}
}
