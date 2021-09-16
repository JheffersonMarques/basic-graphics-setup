package com.hakimen.gfx;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class Window extends Canvas implements Runnable {

    public abstract void Draw(Graphics2D g);
    public abstract void Update(double delta);

    private Thread thread;
    private JFrame frame;
    private static String title = "Application";
    private static int width = 800;
    private static int height = 600;
    private static boolean running;

    public Window(String title,int width,int height){

        this.title = title;
        this.width = width;
        this.height = height;

        this.frame = new JFrame();
        this.frame.setSize(this.width,this.height);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(3);
        this.frame.setTitle(title);
        this.frame.add(this);
        this.frame.setVisible(true);
    }

    public synchronized void start(){
        running = true;
        this.thread = new Thread(this, "Render");
        this.thread.start();
    }

    public synchronized void stop(){
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        Draw((Graphics2D)g);

        g.dispose();
        bs.show();
    }

    private void update(double delta){
        Update(delta);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0/60;
        double delta = 0;
        int frames = 0;

        while (running){
            long now = System.nanoTime();
            delta+=(now-lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update(delta);
                delta --;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                this.frame.setTitle(title + " | " + frames + " FPS ");
                frames = 0;
            }
        }
        stop();
    }
}
