package com.ljs.learn.pattern.facade.improve;

public class HomeFacade {
    // 定义各个子系统对象
    private TheaterLight theaterLight;
    private Popcorn popcorn;
    private Stereo stereo;
    private Projector projector;
    private Screen screen;
    private DVDPlayer dvd;

    public HomeFacade() {
        this.theaterLight = TheaterLight.getInstance();
        this.popcorn = Popcorn.getInstance();
        this.stereo = Stereo.getInstance();
        this.projector = Projector.getInstance();
        this.screen = Screen.getInstance();
        this.dvd = DVDPlayer.getInstance();
    }

    // 操作分成4步
    public void ready(){
        // 2. 开爆米花机
        popcorn.on();
        popcorn.pop();
        // 3. 放下屏幕
        screen.down();
        // 4. 开投影仪
        projector.on();
        // 5. 开音响
        stereo.on();
        // 6. 开DVD、选DVD
        dvd.on();
        // 7. 去拿爆米花
        // 8. 调暗灯光
        theaterLight.dim();
        // 9. 播放DVD
    }

    public void play(){
        dvd.play();
    }

    public void pause(){
        dvd.pause();
    }

    public void end(){
        // 关闭爆米花机
        popcorn.off();
        // 调亮灯光
        theaterLight.bright();
        // 升起屏幕
        screen.down();
        // 关闭影仪
        projector.off();
        // 关闭音响
        stereo.off();
        // 关闭dvd
        dvd.off();
    }
}
