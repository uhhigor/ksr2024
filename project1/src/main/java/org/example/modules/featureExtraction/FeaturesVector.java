package org.example.modules.featureExtraction;

public class FeaturesVector {

    private String country;
    private String c1; // Nazwa kraju w tytule artykułu
    private String c2; // Nazwa stolicy w tekście
    private String c3; // Nazwa kontynentu w tekście

    private boolean c4; // Czy w tekście występuje waluta dolar

    private float c5; // Liczba imion i (lub) nazwisk polityków w tekście

    private float c6; // Średnia długość wyrazów w tekście

    private float c7; // Liczba wyrazów w tekście

    private float c8; // Liczba wspomnień o ważnych wydarzeniach i świętach w tekście

    private float c9; // Liczba unikalnych wyrazów w tekście

    private String c10; // Najczęściej występujące słowo w tekście

    public FeaturesVector() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public boolean isC4() {
        return c4;
    }

    public void setC4(boolean c4) {
        this.c4 = c4;
    }

    public float getC5() {
        return c5;
    }

    public void setC5(int c5) {
        this.c5 = c5;
    }

    public float getC6() {
        return c6;
    }

    public void setC6(float c6) {
        this.c6 = c6;
    }

    public float getC7() {
        return c7;
    }

    public void setC7(float c7) {
        this.c7 = c7;
    }

    public float getC8() {
        return c8;
    }

    public void setC8(float c8) {
        this.c8 = c8;
    }

    public float getC9() {
        return c9;
    }

    public void setC9(int c9) {
        this.c9 = c9;
    }

    public String getC10() {
        return c10;
    }

    public void setC10(String c10) {
        this.c10 = c10;
    }

    public String toString() {
        return "FeaturesVector{" +
                "c1='" + c1 + '\'' +
                ", c2='" + c2 + '\'' +
                ", c3='" + c3 + '\'' +
                ", c4=" + c4 +
                ", c5=" + c5 +
                ", c6=" + c6 +
                ", c7=" + c7 +
                ", c8=" + c8 +
                ", c9=" + c9 +
                ", c10='" + c10 + '\'' +
                '}';
    }

    public void normalize() {
        float max = Math.max(c5, Math.max(c6, Math.max(c7, Math.max(c8, c9))));
        float min = Math.min(c5, Math.min(c6, Math.min(c7, Math.min(c8, c9))));
        c5 = (c5 - min) / (max - min);
        c6 = (c6 - min) / (max - min);
        c7 = (c7 - min) / (max - min);
        c8 = (c8 - min) / (max - min);
        c9 = (c9 - min) / (max - min);
    }
}
