package Main;

import com.zyl.Entity.RankingList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFrame extends Application{
	MainCanvas canvas=new MainCanvas();
	public void start(Stage stage) throws Exception {
		MenuBar menuBar=new MenuBar();
		Menu exit=new Menu("退出");
		exit.setMnemonicParsing(true);/*热键*/
		Menu help=new Menu("帮助");
		help.setMnemonicParsing(true);
		Menu ranking_List=new Menu("排名");
		ranking_List.setMnemonicParsing(true);
		menuBar.getMenus().addAll(help,ranking_List,exit);
		
		MenuItem miExit=new MenuItem("退出");
		MenuItem miHelp=new MenuItem("游戏玩法");
		MenuItem miRank=new MenuItem("排行榜");
		help.getItems().add(miHelp);
		exit.getItems().add(miExit);
		ranking_List.getItems().add(miRank);
		miExit.setOnAction(e->{
			stage.close();
		});
		miHelp.setOnAction(e->{
			Illustrate illustrate=new Illustrate();
		});
		miRank.setOnAction(e->{
			RankingList list=new RankingList();
		});
		BorderPane pane=new BorderPane();
		pane.setCenter(canvas);
		pane.setTop(menuBar);
		
		Scene scene=new Scene(pane,MainCanvas.WIDTH,MainCanvas.HEIGHT);
		stage.setScene(scene);
		stage.setTitle("打飞机小游戏");
		stage.show();
	}
}
