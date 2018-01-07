package Main;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import com.zyl.Entity.Bullet;
import com.zyl.Entity.Enemy_Plane;
import com.zyl.Entity.FlyingObject;
import com.zyl.Entity.My_Plane;
import com.zyl.Entity.New_Life;
import com.zyl.Config.*;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MainCanvas extends Canvas{
	public static final int WIDTH = 400;// 全局静态常量
	public static final int HEIGHT = 675;
	/** 游戏当前的状态：START，PAUSED，RUNNING or GAME_OVER */
	private int STATE;
	private static final int START = 0;// 设置常量
	private static final int RUNNING = 1;// static
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	
	public static int SCORE = 0;// 得分
	private FlyingObject[] flyings= {};
	private Bullet[] bullets= {};
	private My_Plane planes = new My_Plane();// 控制的飞机
	public MediaPlayer mediaPlayer=new MediaPlayer(AddMusic.background_Music);
	private int index=-1;
	private int first=0;
	
	private File file=new File("D:\\eclipse\\ShootPlane\\score.txt");
	private FileOutputStream output;
	public GraphicsContext g = this.getGraphicsContext2D();
	
	MainCanvas() {
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);
		this.paint();
 
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(20);
		mediaPlayer.play();	
 
		new Thread(()-> {
		 this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					if (STATE == RUNNING) {
						mediaPlayer.pause();
						STATE = PAUSE;
					}
				}
			});
			this.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					if (STATE == RUNNING) {
						int x = (int) e.getX();
						int y = (int) e.getY();
						planes.MoveTo(x, y);
					}
				}
			});
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					switch (STATE) {
					case START:
						if(first==0) {
							first=1;
							outputScore();
						}	
						STATE = RUNNING;
						break;
					case GAME_OVER:
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("确认");
						alert.setHeaderText("你最后的得分是："+MainCanvas.SCORE);
						alert.setContentText("你确定要再玩一局吗?");
						Optional<ButtonType> reasult =alert.showAndWait();
						if(reasult.get()==ButtonType.OK) {
							outputScore();
							flyings = new FlyingObject[0];
							bullets=new Bullet[0];
							planes = new My_Plane();
							SCORE = 0;
							STATE = START;
							mediaPlayer.play();
							break;
						}
						else if(reasult.get()==ButtonType.CLOSE) {
								STATE=GAME_OVER;
								break;	
						}		
					}
				}
			});
			this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					if (STATE == PAUSE) {
						mediaPlayer.play();
						STATE = RUNNING;
					}
				}
			});	
		}).start();
		
		new Thread(()->{
			try {
				while(true) {
					if (STATE == RUNNING) { // 运行状态
						enterAction(); // 飞行物入场
						shootAction(); // 英雄机射击
						stepAction(); // 走一步
						bangAction(); // 子弹打飞行物
						outOfBoundsAction(); // 删除越界飞行物及子弹
						checkGameOverAction(); // 检查游戏结束
					}
					paint();
					Thread.sleep(10);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}).start();

	}
	
	public void paint() {
		g.drawImage(Image_Config.background, 0, 0);// 画背景图
		paintMy_Plane();
		paintBullets();
		paintFlyingObject();
		paintScore();
		paintState();
	}
	
	/** 画自己控制的飞机 */
	public void paintMy_Plane() {
		g.drawImage(planes.getImage(), planes.getX(), planes.getY());
	}
	
	/** 画子弹 */
	public void paintBullets() {
		for (int i = 0; i < bullets.length; i++) {
			g.drawImage(bullets[i].getImage(), bullets[i].getX() - bullets[i].getWidth() / 2, bullets[i].getY());
		}
	}
	
	/** 画飞行物 */
	public void paintFlyingObject() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY());
		}
	}
	
	/** 画分数 */
	public void paintScore() {
		int x = 10;
		int y = 25;// x,y的坐标
		Font font = Font.font("New Times Roman", FontWeight.BOLD, FontPosture.ITALIC, 22);
		g.setFont(font);
		g.setFill(Color.RED);
		g.fillText("SCORE:" + SCORE, x, y);// 显示分数
		y = y + 20;
		g.fillText("LIFE:" + planes.getLife(), x, y);
	}
	
	/** 画游戏状态 */
	public void paintState() {
		switch (STATE) {
		case START:
			g.drawImage(Image_Config.start, 0, 0);
			break;
		case PAUSE:
			g.drawImage(Image_Config.pause, 0, 0);
			break;
		case GAME_OVER:
			g.drawImage(Image_Config.gameOver, 0, 0);
			break;
		}
	}
	
	/** 飞行物入场 */
	int flyEnteredIndex = 0;
	public void enterAction() {
		flyEnteredIndex++;
		if (flyEnteredIndex %30== 0) {// 300毫秒产生一个飞行器
			FlyingObject obj = nextOne();
			if(obj instanceof Enemy) {
				obj.setSpeed(SCORE);//加快速度
			}	
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}
	
	/**
	 * 随机产生飞行物
	 * 
	 * @return 飞行对象
	 */
	public static FlyingObject nextOne() {
		Random random = new Random();
		int type =Math.abs( random.nextInt(20));// [0,20)
		if (type < 2) {// 2以下就产生生命
			return new New_Life();
		} else {
			return new Enemy_Plane();
		}
	}
	
	/** ��һ�� */
	public void stepAction() {
		for (int i = 0; i < flyings.length; i++) { // ��������һ��
			FlyingObject f = flyings[i];
			f.step();
		}

		for (int i = 0; i < bullets.length; i++) { // �ӵ���һ��
			Bullet b = bullets[i];
			b.step();
		}
		planes.step(); // Ӣ�ۻ���һ��
	}
	
	/** ��������һ�� */
	public void flyingStepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}
	int shootIndex = 0;
	/** ��� */
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 40 == 0) { // 300���뷢һ��
			playMusic(AddMusic.fire_bullet);//发送子弹的音效
			Bullet[] bs = planes.shoot(); // Ӣ�۴���ӵ�
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
					bs.length); // ׷������
		}
	}
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
			Bullet b = bullets[i];
			bang(b); // �ӵ��ͷ�����֮�����ײ���
		}
	}
	
	public void bang(Bullet bullet) {
		index = -1; 
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if (obj.shootBy(bullet)) { // �ж��Ƿ����
				index = i; // ��¼�����еķ����������
				break;
			}
		}
		if (index != -1) { // �л��еķ�����
			FlyingObject one = flyings[index]; // ��¼�����еķ�����
			
			FlyingObject temp = flyings[index]; // �����еķ����������һ�������ｻ��
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;

			flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����һ��������(�������е�)
			// ���one������(���˼ӷ֣�������ȡ)
			if (one instanceof Enemy) { // ������ͣ��ǵ��ˣ���ӷ�
				Enemy e = (Enemy) one; // ǿ������ת��
				SCORE += e.getScore(); 
			} else { // ��Ϊ���������ý���
				Award a = (Award) one;
				playMusic(AddMusic.update);
				int type = a.getType(); // ��ȡ��������
				switch (type) {
				case com.zyl.Config.Award.DOUBLE_FIRE:
					planes.addDoublefire(); // ����˫������
					break;
				case com.zyl.Config.Award.LIFE:
					planes.addLife(); // ���ü���
					break;
				}
			}
		}
	}
	public void outOfBoundsAction() {
		int index = 0; // ����
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ���ŵķ�����
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (!f.outOfBound()) {
				flyingLives[index++] = f; // ��Խ�������
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // ����Խ��ķ����ﶼ����

		index = 0; // ��������Ϊ0
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBound()) {
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // ����Խ����ӵ�����
	}
	public void checkGameOverAction() {
		if (isGameOver()) {
			try {
				output=new FileOutputStream(file,true);
				String s=" "+(SCORE+"");
				output.write(s.getBytes());
				output.write("\r\n".getBytes());
				output.close();
				mediaPlayer.pause();
				playMusic(AddMusic.lose_game);
		
				STATE = GAME_OVER;// 改变状态
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public boolean isGameOver() {
		
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (planes.hit(obj)) { // ���Ӣ�ۻ���������Ƿ���ײ
				planes.substractLife(); // ����
				planes.setDoublefire(0); // ˫���������
				index = i; // ��¼���ϵķ���������
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t; // ���ϵ������һ�������ｻ��

				flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����ϵķ�����
			}
		}
		return planes.getLife() <= 0;
	}
	
	public void playMusic(Media h) {
		MediaPlayer mediaPlayer=new MediaPlayer(h);
		mediaPlayer.play();
	}
	
	public void outputScore(){
		try {
			output=new FileOutputStream(file,true);
			TextInputDialog dialog = new TextInputDialog(null);
			dialog.setTitle("Text Input Dialog");
			dialog.setHeaderText("在开始游戏前，请留下大侠你的名字吧！");
			dialog.setContentText("Please enter your name:");

			Optional<String> result = dialog.showAndWait();
			if(result.isPresent()) {
				output.write(result.get().getBytes());
			}
			else {
				output.write("Stranger".getBytes());
			}	
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
