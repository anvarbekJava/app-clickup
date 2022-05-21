package uz.pdp.appclickup.controller;

import uz.pdp.appclickup.entity.Icon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/*
Berilgan matnning birinchi ikkita belgisini olib
 tashlangan holdagi yangi matnni qaytaradigan method tuzing.
  Istisno holatida agar birinchi belgi ‘a’ ga, ikkinchi belgi ‘b’ ga teng bo`lsa bu belgilar olib tashlanmasin.
   Maslan:
“saxiy” -> “xiy”,
“baxil”-> “xil”,
“askiya” -> “akiya”,
“obdon” -> “bdon”,
“about”-> “about”


 */
public class Main {
    public static void main(String[] args) {
        LinkedHashSet<Integer> integers = new LinkedHashSet<>(100);

        for (int i = 0; i < 100; i++) {
            integers.add((int)((Math.random()*200)+300));
        }
        Iterator<Integer> integerIterator = integers.iterator();
        int i=1;
        while (integerIterator.hasNext()){
            System.out.print(i+") "+integerIterator.next()+"  ");
            i++;
        }

        System.out.println();
        TreeSet<Integer> treeSet = new TreeSet<>(integers);
        System.out.println(treeSet.headSet(400));

    }
}