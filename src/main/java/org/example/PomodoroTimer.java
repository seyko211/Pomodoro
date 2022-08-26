package org.example;
/*
 * Консольное программа, реализующее таймер "Pomodoro"
 * @author seyko211
 */

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PomodoroTimer {
    public static int test = 0;
    public static void main(String[] args) throws InterruptedException, NumberFormatException, ArithmeticException{
        System.out.println("-- Таймер Pomodoro --\nВведите: -w <время работы> -b <врмемя отдыха> " +
                "-с <количество повторов>");
        String[] cmd = new Scanner(System.in).nextLine().split(" ");

        int work = 50; // время работы, мин
        int breake = 10; // время отдыха, мин
        int sizebreak = 30; // размер строки прогресса (работа)
        int sizework = 30; // размер строки прогресса (отдых)
        int help = 0; // режим справки
        int count = 1; // количество итератий работа-отдых

        //
        for(int i=0; i < cmd.length; i++){
            try {
                switch (cmd[i]) {
                    case "-help" -> {
                        System.out.println(
                                "\n\nPomodoro - сделай свое время более эффективным\n");
                        System.out.println(
                                "	-w <time>: время работы, сколько минут хочешь работать.");
                        System.out.println(
                                "	-b <time>: время отдыха, сколько минут хочешь отдыхать.");
                        System.out.println(
                                "	-c <count>: количество повторов.");
                        System.out.println(
                                "	-help: меню помощи.");
                        System.out.println(
                                "	-exit: закрыть таймер.\n\nПопробуйте еще раз.");
                        help = 1;
                    }
                    case "-w" -> work = Integer.parseInt(cmd[++i]);
                    case "-b" -> breake = Integer.parseInt(cmd[++i]);
                    case "-c" -> count = (Integer.parseInt(cmd[++i])) < 1 ? 1 : Integer.parseInt(cmd[++i]);
                    case "-exit" -> System.exit(0);
                    default -> myExeption();
                }
            } catch (NumberFormatException e) {
                myExeption();
            }
        }
        if(help == 0){
            long startTime = System.currentTimeMillis();
            try {
                for (int i = 1; i <= count; i++) {
                    timer(work, breake, sizebreak, sizework);
                }
            } catch (ArithmeticException e){
                myExeption();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Pomodoro таймер истек: " + (endTime-startTime)/(1000 * 60)+ " min");
        }
    }
    public static void timer(int work, int breake, int sizebreak, int sizework) throws InterruptedException{
        printProgress("Прогресс работы:  ", work, sizework);
        printProgress("Прогресс отдыха: ", breake, sizebreak);
    }
    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length;
        int rep;
        String str;
        length = 60 * time / size;
        rep = 60 * time / length;
        int stretchb = size /(3 * time);
        for(int i = 1; i <= rep; i++){
            double x = i;
            x = 1.0/3.0 *x;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            double w = time *stretchb;
            double percent = (x/w) *1000;
            x /=stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;
            str = process + percent+"% " + (" ").repeat(5 - (String.valueOf(percent).length())) +"[" + ("#").repeat(i) + ("-").repeat(rep - i)+"]    ( " + x +"min / " + time +"min )"+  "\r";
            System.out.print(str);
            if(test == 0){
                TimeUnit.SECONDS.sleep(length);
            }
        }
        System.out.println();
    }
    private static void myExeption() throws InterruptedException {
        System.out.println("Используйте целые числа больше нуля.\nНажмите Enter чтобы попробовать снова.");
        main(new Scanner(System.in).nextLine().split(" "));
    }
}