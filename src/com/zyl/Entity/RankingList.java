package com.zyl.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RankingList{
	private File file=new File("D:\\eclipse\\ShootPlane\\score.txt");
	private Label label1,label2,label3,label4;
	private Label label5,label6,label7,label8,label9;
	private Text text1,text2,text3;
	//private ArrayList<Node> nodes=new ArrayList<>();
	private LinkList nodes=new LinkList();
	private Stage stage=new Stage();
	private FileReader fileReader;
	public RankingList(){
		BorderPane pane=new BorderPane();
		GridPane gridPane=new GridPane();
		HBox hbox=new HBox();
		Text text=new Text("英雄榜");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(20);
		gridPane.setHgap(50);
		
		hbox.setStyle("-fx-border-width:5;-fx-border-color:blue");
		text.setFont(Font.font(30));
		hbox.getChildren().add(text);
		hbox.setAlignment(Pos.CENTER);
		pane.setTop(hbox);
		pane.setCenter(gridPane);
		
		label4=new Label("排名");
		label5=new Label("姓名");
		label6=new Label("成绩");
		label7=new Label("第一名");
		label8=new Label("第二名");
		label9=new Label("第三名");
		gridPane.add(label4,0,0);
		gridPane.add(label5,1,0);
		gridPane.add(label6,2,0);
		gridPane.add(label7,0,1);
		gridPane.add(label8,0,2);
		gridPane.add(label9,0,3);
		
		try {
			fileReader = new FileReader(file);
			BufferedReader r = new BufferedReader(fileReader);
			String s1=null;
			int h=0;
			while((s1=r.readLine())!=null) {
				h=s1.indexOf(" ");
				nodes.addNode(s1.substring(0, h), s1.substring(h+1, s1.length()));
			}
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(nodes.length()==1) {
			label1=new Label(nodes.getName(1));
			text1=new Text(nodes.getScore(1));
			gridPane.add(label1, 1, 1);
			gridPane.add(text1, 2, 1);
			text1.setFont(Font.font(20));
			label1.setFont(Font.font("Times",20));
		}	
		else if(nodes.length()<=2) {
			label1=new Label(nodes.getName(1));
			label2=new Label(nodes.getName(2));
			text1=new Text(nodes.getScore(1));
			text2=new Text(nodes.getScore(2));
			gridPane.add(label1, 1, 1);
			gridPane.add(text1, 2, 1);
			gridPane.add(label2, 1, 2);
			gridPane.add(text2, 2, 2);
			text1.setFont(Font.font(20));
			label1.setFont(Font.font("Times",20));
			text2.setFont(Font.font(20));
			label2.setFont(Font.font("Times",20));
		}
		else {
			label1=new Label(nodes.getName(1));
			label2=new Label(nodes.getName(2));
			label3=new Label(nodes.getName(3));
			text1=new Text(nodes.getScore(1));
			text2=new Text(nodes.getScore(2));
			text3=new Text(nodes.getScore(3));
			gridPane.add(label1, 1, 1);
			gridPane.add(text1, 2, 1);
			gridPane.add(label2, 1, 2);
			gridPane.add(text2, 2, 2);
			gridPane.add(label3, 1, 3);
			gridPane.add(text3, 2, 3);
			text1.setFont(Font.font(20));
			label1.setFont(Font.font("Times",20));
			text2.setFont(Font.font(20));
			label2.setFont(Font.font("Times",20));
			text3.setFont(Font.font(20));
			label3.setFont(Font.font("Times",20));
		}

		Scene scene=new Scene(pane,400,300);
		stage.setTitle("排行榜");
		stage.setScene(scene);
		stage.show();
	}
	public int charToInt(String h) {
		int len=Integer.parseInt(h);
		return len;
	}
}
