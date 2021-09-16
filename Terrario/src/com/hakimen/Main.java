package com.hakimen;

import com.hakimen.gfx.Window;

import java.awt.*;

public class Main {
    static class Terrario extends Window{

        public Terrario(String title, int width, int height) {
            super(title, width, height);
        }

        @Override
        public void Draw(Graphics2D g) {

        }

        @Override
        public void Update(double delta) {

        }
    }
    public static void main(String[] args) {
        Terrario t = new Terrario("Terrario",800,600);
        t.start();
    }
}
