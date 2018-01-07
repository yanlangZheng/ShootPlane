package com.zyl.Entity;

import java.util.Random;

import com.zyl.Config.Image_Config;

import Main.MainCanvas;

public class AddFire extends FlyingObject{
	private int xSpeed=1;//x坐标移动速度
	private int ySpeed=2;//y坐标移动速度
	private int awardType;//奖励类型
	/**初始化数据*/
	public AddFire() {
		this.image=Image_Config.fire;//获得图片
		width=(int) image.getWidth();//图片的高、宽
		height=(int) image.getHeight();
		y=-height;//蜜蜂先不出现在场景内
		Random rand=new Random();
		x=rand.nextInt(MainCanvas.WIDTH-width);
		awardType=rand.nextInt(2);//初始化奖励[0，2)
	}
	public boolean outOfBound() {//越界处理
		return this.y>MainCanvas.HEIGHT;//蜜蜂的高度越界是返回true
	}
	public void step() {//斜着移动
		this.x+=xSpeed;
		this.y+=ySpeed;
		if(x>MainCanvas.WIDTH-width) {//能使蜜蜂碰到边框后返回
			xSpeed=-1;
		}
		if(x<0) {
			xSpeed=1;
		}
	}
	/**获得奖励类型*/
	public int getType() {
		return awardType;
	}
}
