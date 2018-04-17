package sajor.com.shootgame.comp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import sajor.com.shootgame.Constant;
import sajor.com.shootgame.ViewManager;
import sajor.com.game.Graphics;

public class Player {
    private int default_x = 0;
    private int default_y = 0;
    private int playerX;
    private int playerY;
    // 保存当前状态
    private int action = 0;
    // 定义玩家图片大小
    private int heroHeight = 0;
    private int heroWidth = 0;

    private final List<Bullet> bulletList = new ArrayList<>();
    public Player(){

    }
    public void init(Bitmap hero){
        heroWidth = hero.getWidth();
        heroHeight = hero.getHeight();
        playerX = (ViewManager.SCREEN_WIDTH - heroWidth)/ 2;
        playerY = ViewManager.SCREEN_HEIGHT - heroHeight;
        default_x = playerX;
        default_y = playerY;
    }
    public void drawMe(Canvas canvas){
        if (canvas == null){
            return ;
        }
        switch(action){
            case 0:
                drawAni(canvas, ViewManager.playerImage[0]);
                break;
            case 1:
                drawAni(canvas, ViewManager.playerImage[1]);
                break;
        }
        action+=1;
        if (action == 2){
            addBullet();
            action = 0;
        }
    }
    public void drawAni(Canvas canvas, Bitmap hero){
        if (canvas == null){
            return ;
        }
        // 画出玩家
        Graphics.drawImage(canvas, hero, playerX, playerY, 0, 0, hero.getWidth(), hero.getHeight());
        drawBullet(canvas);
    }

    // 画子弹
    public void drawBullet(Canvas canvas) {
        List<Bullet> deleteList = new ArrayList<>();
        Bullet bullet;

        // 遍历角色发射的所有子弹
        for (int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);
            if (bullet == null) {
                continue;
            }
            // 将所有越界的子弹收集到deleteList集合中
            if (bullet.getBulletY() < 0) {
                deleteList.add(bullet);
            }
        }
        Bitmap bitmap;
        // 清除所有越界的子弹
        bulletList.removeAll(deleteList);
        // 遍历用户发射的所有子弹
        for (int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);

            if (bullet == null) {
                continue;
            }
            // 获取子弹对应的位图
            bitmap = bullet.getBitmap();
            if (bitmap == null) {
                continue;
            }
            // 子弹移动
            bullet.move();
            // 画子弹
            Graphics.drawImage(canvas, bitmap, bullet.getBulletX(), bullet.getBulletY(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    // 发射子弹的方法
    public void addBullet() {
        // 计算子弹的初始X坐标
        int bulletX = playerX + heroWidth/2;
        int bulletY = playerY;
        // 创建子弹对象
        Bullet bullet = new Bullet(bulletX, bulletY, Constant.BULLET_TYPE_1);
        // 将子弹添加到用户发射的子弹集合中
        bulletList.add(bullet);
    }
}