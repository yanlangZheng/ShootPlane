package com.zyl.Entity;

import java.util.Random;

import com.zyl.Config.Enemy;
import com.zyl.Config.Image_Config;

import Main.MainCanvas;

public class Enemy_Plane extends FlyingObject implements Enemy{

	private int speed =3;//移動速度為3
	/**初始化*/
	public Enemy_Plane(){
		super.image=Image_Config.enemy_plane;
		
		super.width=(int) image.getWidth();
		super.height=(int) image.getHeight();
		
		y=-height;//先不出現
		Random rand=new Random();
		x=Math.abs(rand.nextInt(MainCanvas.WIDTH-width));
	}

	/**越界处理*/
	public boolean outOfBound() {
		return y>MainCanvas.HEIGHT;
	}

	/**移动*/
	public void step() {
		y+=speed;
	}

	/**获得分数*/
	public int getScore() {//打死一个加五分
		return 5;
	}
}
