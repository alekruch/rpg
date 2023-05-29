package MAain;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Настройки Экрана
    final int originalTitleSize = 16;// 16/16
    final int scale = 3;
    public  final int tileSize = originalTitleSize *scale;
    public final int maxScreenCol = 16;//настройки высоты
    public final int maxScreenRow = 12;//настройки ширины
    public final int screenWidth = tileSize * maxScreenCol;//768 pixl
    public final int screenHeight = tileSize * maxScreenRow;//576 pixl

    //  ПАРАМЕТРЫ КАРТЫ МИРА

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;




    //  FPS
    int FPS = 60;
    // Система
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionCheker cChecker = new CollisionCheker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameTread;



// Сущность и объекты
   public Player player = new Player(this,keyH );
   public SuperObject[] obj = new SuperObject[10];


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public  void  setupGame(){
        aSetter.setObject();
        playMusic(0);
    }
    public void startGameTread(){
        gameTread = new Thread(this);
        gameTread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameTread!= null){

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime<0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public  void update(){
        player.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // ПЛИТКА
        tileM.draw(g2);
        //ОБЪЕКТЫ
        for (int i = 0; i <obj.length ; i++) {
            if(obj[i]!=null){
                obj[i].draw(g2,this);
            }
            
        }
        // ИГРОК
        player.draw(g2);
        //ИНТЕРФЕЙС
        ui.draw(g2);
        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
