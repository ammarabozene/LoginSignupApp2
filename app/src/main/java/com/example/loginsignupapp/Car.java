package com.example.loginsignupapp;

import java.io.Serializable;

enum CarCategory{
    Dodge,Bmw,mercedes,audi,porsche,ferrari,lamborghini,toyota,mitsubishi,nissan
}

    public class Car implements Serializable {
        private int history;
        private int Description;
        private int name;

        private String photo;

        public Car(int parseInt1, int chasis, int i, int parseInt, int anInt, int i1, CarCategory carCategory) {
        }

        public int getHistory() {
            return history;
        }

        public void setHistory(int history) {
            this.history = history;
        }

        public int getDescription() {
            return Description;
        }

        public void setDescription(int description) {
            Description = description;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        public Car(int history , int Description, int name, int chasis, int color, int releaseyear, int engine, int size, String photo) {
            this.history = history;
            this.Description = Description;
            this.name = name;

            this.photo = photo;
        }



        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "history=" + history +
                    ", Description=" + Description +
                    ", name=" + name +

                    ", photo='" + photo + '\'' +
                    '}';
        }

    }

