package MAain;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.BOLD,30);
        arial_80 = new Font("Arial", Font.BOLD,80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        if(gameFinished == true){
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            String text;
            int textLenght;
            int x;
            int y;

            text  = "Ты нашел сокровище!";
            textLenght = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLenght/2;
            y = gp.screenHeight/2 -(gp.tileSize*3);
            g2.drawString(text,x,y);

            g2.setFont(arial_80);
            g2.setColor(Color.orange);
            text  = "Поздравляю!";
            textLenght = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLenght/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text,x,y);
            gp.gameTread = null;


        }
        else
        {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2,gp.tileSize, gp.tileSize, null);
            g2.drawString("x = "+gp.player.hasKey,74, 65);
            // Сообщение пользователю
            if (messageOn == true){
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message, (int) (gp.tileSize/2.5), gp.tileSize*5);

                messageCounter++;

                if(messageCounter>120){
                    messageCounter = 0;
                    messageOn = false;
                }
            }

        }

    }
}
