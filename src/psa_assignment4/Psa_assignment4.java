/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psa_assignment4;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author root
 */

public class Psa_assignment4 {
    public static void main(String[] args) throws InterruptedException{
        
        int table_size = 100000;
        HashMap<Integer,ArrayList<Float>> hm = new HashMap<Integer,ArrayList<Float>>();
        double percent = 1.0/100.0;
        int counter = 0;
        while(counter<5){
        for(double k=1;k<=100;k+=1){
            ArrayList<Long> avg=new ArrayList<Long>();
            HashTableWithAsyncRehash m = new HashTableWithAsyncRehash(table_size);
            Random rand = new Random();
            percent = k;
            int rand_int[];
            int total_values = (int) (table_size*percent/100);
            rand_int = new int[total_values];
            for(int i=0;i<total_values;i++){
                rand_int[i] = rand.nextInt(1000000000);
            }
            for(int i=0;i<total_values;i++){
                m.put(rand_int[i]);
            }
//            Thread.sleep(300);
            while(m.threadstatus==true) Thread.sleep(200);
            Instant start = Instant.now();
            for(int i=0;i<total_values;i++){
                
                m.get(rand_int[i]);
                
//                avg.add(timeElapsed);
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            long sum =0;
            for(Long kk: avg) sum+=kk;
            float avgtime = ((float)timeElapsed/(float)total_values);
            
            if(hm.containsKey((int)(k))){
                ArrayList<Float> temp = hm.get((int)(k));
                temp.add(avgtime);
                hm.put((int)(k),temp);
            }
            else {
                ArrayList<Float> temp = new ArrayList<Float>();
                temp.add(avgtime);
                hm.put((int)(k),temp);
            }
        }
        counter++;
        System.out.println(counter);
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for(int l=1;l<100;l++){
            float sum=0;
            for(float f:hm.get(l)){
                sum+=f;
            }
//            System.out.println(l+"\t"+String.format("%.12f", (float)sum/(float)hm.get(l).size()));
            System.out.println(String.format("%.12f", (float)sum/(float)hm.get(l).size()));
        }
    }
}