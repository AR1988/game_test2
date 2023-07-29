package org.example;

import java.util.Objects;
import java.util.Random;

public class Card {

//    public static char[] icons = ("的一是不了人我在有他这个上们来到时大地为子中你说生国年着就那和要她出也得里后自以会家可下而过天去能" +
//            "对小多然于心学么之都好看起发当没成只如事把还用第样道想作种开美总从无情己面最女但现前些所同日手又行意动方期它头经长儿回位分爱" +
//            "老因很给名法间斯知世什两次使身者被高已亲其进此话常与活正感")
//            .toCharArray();

    public static char[] icons = "0123456789абвгдежзийклмнопрстуфхцчшщъыьэюяABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyz!\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~ Ёё€@".toCharArray();

    private final char symbol;

    public Card() {
        this.symbol = icons[new Random().nextInt(icons.length - 1)];
    }

    public Card(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return symbol == card.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
