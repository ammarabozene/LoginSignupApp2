package com.example.loginsignupapp;

enum Ccategory{
    Dodge,Bmw,mercedes,audi,porsche,ferrari,lamborghini,toyota,mitsubishi,nissan
}



    public class Carscategory {
        private int color;
        private int releaseyear;
        private int engine;
        private int size;
        private String photo;

        public Carscategory(){
        }




        public void carscategory(int color, int releaseyear, int engine, int size, String photo) {


            this.color = color;
            this.releaseyear = releaseyear;
            this.engine = engine;
            this.size = size;
            this.photo = photo;
        }

        @Override
        public String toString() {
            return "carscategory{" +
                    "color=" + color +
                    ", releaseyear=" + releaseyear +
                    ", engine=" + engine +
                    ", size=" + size +
                    ", photo='" + photo + '\'' +
                    '}';
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getReleaseyear() {
            return releaseyear;
        }

        public void setReleaseyear(int releaseyear) {
            this.releaseyear = releaseyear;
        }

        public int getEngine() {
            return engine;
        }

        public void setEngine(int engine) {
            this.engine = engine;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }


