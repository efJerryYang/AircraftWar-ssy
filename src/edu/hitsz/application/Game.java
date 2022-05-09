package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.EnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    protected final List<AbstractEnemy> enemyAircrafts;
    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;
    private final List<AbstractProp> props;

    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;

    /**
     * 周期(ms)均为数组，第一位为循环时间，第二位为循环周期
     * 指示子弹的发射、敌机的产生频率
     */
    protected int[] enemyCycle = {0, MainDataController.getEnemyCycleDuration()};
    protected int[] enemyShootCycle = {0, MainDataController.getEnemyShootDuration()};
    protected int[] heroAircraftShootCycle = {0, MainDataController.getHeroAircraftShootDuration()};
    private int[] difficultyCycle = {0, 8000};
    //两个周期的最小值
    protected int enemyCycleMin = MainDataController.getEnemyCycleMin();
    protected int enemyShootMin = MainDataController.getEnemyShootMin();
    //boss机阈值
    private int bossThreshold = MainDataController.getBossThreshold();
    //敌机最大数量
    protected int enemyMaxNumber = MainDataController.getEnemyMaxNumber();
    //精英概率和最大值
    protected double eliteChance = MainDataController.getEliteChance();
    protected double eliteChanceMax = MainDataController.getEliteChanceMax();
    //召唤boss机数
    protected int bossNum = 0;
    protected boolean bossFlag = false;
    protected boolean bossExist = false;

    private CycleMusicThread mainBGM;
    protected CycleMusicThread bossBGM;
    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        //Scheduled 线程池，用于定时任务调度
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        //音乐
        mainBGM = new CycleMusicThread("src/videos/bgm.wav");
        mainBGM.start();
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            //检查boss机是否存在
            checkBoss();

            //周期性创建敌机
            if (timeCountAndNewCycleJudge(enemyCycle)) {
                // 新敌机产生
                createEnemy();
            }

            //敌机周期性射击
            if (timeCountAndNewCycleJudge(enemyShootCycle)) {
                enemyShootAction();
            }

            //英雄机周期性射击
            if (timeCountAndNewCycleJudge(heroAircraftShootCycle)) {
                heroAircraftShootAction();
            }

            //周期性增加难度
            if (timeCountAndNewCycleJudge(difficultyCycle)) {
                raiseDifficulty();
            }
            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                //设置最后得分
                MainDataController.setScore(score);
                //停止背景音乐
                mainBGM.closeMusic();
                //停止boss音乐
                if (bossBGM != null) {
                    bossBGM.closeMusic();
                }
                //播放游戏结束音乐
                MusicThread gameOverBGM = new MusicThread("src/videos/game_over.wav");
                gameOverBGM.start();
                System.out.println("Game Over!");
                //唤醒主线程
                synchronized (Main.class) {
                    (Main.class).notify();
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    /**
     * 该放法传入循环时间和周期，判断是否到新的周期
     * @param cycle
     * @return
     */
    private boolean timeCountAndNewCycleJudge(int[] cycle) {
        cycle[0] += timeInterval;
        if (cycle[0] >= cycle[1] && cycle[1] - timeInterval < cycle[0]) {
            // 跨越到新的周期
            cycle[0] %= cycle[1];
            return true;
        } else {
            return false;
        }
    }

    /**
     * 敌机射击，与英雄机错开周期
     */
    private void enemyShootAction() {
        // TODO 敌机射击
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
    }

    /**
     * 英雄机射击
     */
    private void heroAircraftShootAction() {
        // 英雄机射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 创建敌机
     */
    protected abstract void createEnemy();

    /**
     * 检测是否存在boss
     */
    private void checkBoss() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof BossEnemy) {
                bossExist = true;
                return;
            }
        }
        if (bossBGM != null) {
            bossBGM.closeMusic();
        }
        bossExist = false;
    }

    /**
     * 提升难度，减短敌机刷新周期，增加敌机射击周期,敌机属性增加
     */
    protected abstract void raiseDifficulty();

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.notValid()) {
                // 已被其他子弹击毁的英雄机，不再检测
                // 避免多个子弹重复击毁英雄机的判定
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                //产生对应音效
                MusicThread bullet_hit = new MusicThread("src/videos/bullet_hit.wav");
                bullet_hit.start();
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    //产生对应音效
                    MusicThread bullet_hit = new MusicThread("src/videos/bullet_hit.wav");
                    bullet_hit.start();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        props.addAll(enemyAircraft.formProp());
                        int prescore = score;
                        score += enemyAircraft.getScore();
                        if (prescore / bossThreshold  < score / bossThreshold) {
                            bossFlag = true;
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            //道具和英雄机相撞，英雄机根据种类获得效果，礼包消失
            if (prop.crash(heroAircraft) || heroAircraft.crash(prop)) {
                if (prop instanceof BombProp) {
                    BombProp bombProp = (BombProp) prop;
                    for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                        if (!(enemyAircraft instanceof BossEnemy)) {
                            //非boss敌机加入观察者列表并且获得相应得分
                            bombProp.addExplosive(enemyAircraft);
                            int prescore = score;
                            score += enemyAircraft.getScore();
                            if (prescore / bossThreshold  < score / bossThreshold) {
                                bossFlag = true;
                            }
                        }
                    }
                    for (AbstractBullet bullet : enemyBullets) {
                        bombProp.addExplosive(bullet);
                    }
                }
                prop.work(heroAircraft);
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * 4. 删除无效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, props);//道具也在飞机之前绘制
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
