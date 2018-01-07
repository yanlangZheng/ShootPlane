package com.zyl.Config;

import java.io.File;

import javafx.scene.media.Media;

public class AddMusic {
	public static Media background_Music=new Media(
			new File("D:\\eclipse\\ShootPlane\\src\\com\\muc\\background.mp3").toURI().toString());
	public static Media fire_bullet=new Media(
			new File("D:\\eclipse\\ShootPlane\\src\\com\\muc\\fire_bullet.wav").toURI().toString());
	public static Media small_plane_killed=new Media(
			new File("D:\\eclipse\\ShootPlane\\src\\com\\muc\\small_plane_killed.wav").toURI().toString());
	public static Media game_over=new Media(
			new File("D:\\eclipse\\ShootPlane\\src\\com\\muc\\game_over.wav").toURI().toString());
	public static Media lose_game=new Media(
			new File("D:\\eclipse\\ShootPlane\\src\\com\\muc\\lose_game.mp3").toURI().toString());
	public static Media update=new Media(
			new File("D:\\eclipse\\ShootPlane\\src\\com\\muc\\update.wav").toURI().toString());
}
