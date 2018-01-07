package com.zyl.Entity;

import javafx.scene.image.Image;

public abstract class FlyingObject {
	protected int x;// 在子类及同一包内访问
	protected int y;// x,y的坐标
	protected int width;// 飞行物的宽度和高度
	protected int height;
	protected int speed=3;
	protected Image image;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	/** 判断是否出界 */
	public abstract boolean outOfBound();

	/** 飞行物的移动 */
	public abstract void step();
	
	public boolean shootBy(Bullet bullet){
		int x = bullet.x;  //子弹横坐标
		int y = bullet.y;  //子弹纵坐标
		return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
	}
	public void setSpeed(int score) {
		if(score%100==0&&score!=0) {
			this.speed=this.speed+score/100;
		}
	}
}
