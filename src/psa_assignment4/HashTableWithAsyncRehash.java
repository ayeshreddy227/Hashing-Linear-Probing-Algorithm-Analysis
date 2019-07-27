/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psa_assignment4;

/**
 *
 * @author root
 */

public class HashTableWithAsyncRehash {
    private int TABLE_SIZE = 128;
    private int size = 0;
    public boolean threadstatus = false;
    public int getSize() {
        return size;
    }
      private double DEFAULT_LOAD_FACTOR=0.75;
      HashEntry[] table;
      HashEntry[] temp;
    public void setSize(int size) {
        this.size = size;
    }
 
      HashTableWithAsyncRehash(int table_size) {
          TABLE_SIZE = table_size;
            table = new HashEntry[TABLE_SIZE];
            for (int i = 0; i < TABLE_SIZE; i++)
                  table[i] = null;
      }
 
      public int get(int key) {
            int hash = (key % TABLE_SIZE);
            while (table[hash] != null && table[hash].getKey() != key)
                  hash = (hash + 1) % TABLE_SIZE;
            if (table[hash] == null){
                  if( threadstatus==true){
                        hash = (key % (TABLE_SIZE/2));
                        while (temp[hash] != null && temp[hash].getKey() != key)
                              hash = (hash + 1) % TABLE_SIZE;
                        if(temp[hash] == null) return -1;
                        else return key;
                  }
                  return -1;
            }
            else
                  return key;
      }
 
      public void put(int key) {
            int hash = (key % TABLE_SIZE);
            
            while (table[hash] != null && table[hash].getKey() != key)
                  if(hash==TABLE_SIZE-1) hash=0;
                  else hash = (hash + 1) % TABLE_SIZE;
            table[hash] = new HashEntry(key);
            size++;
            double loadFactor = (1.0 * size) / TABLE_SIZE; 
            
            if (loadFactor > DEFAULT_LOAD_FACTOR) { 
                populateAndSwitch();
            }
        }

    public void populateAndSwitch( ) {
    threadstatus=true;
    temp = table;
    TABLE_SIZE = 2*TABLE_SIZE;
    table = new HashEntry[TABLE_SIZE];
    for (int i = 0; i < TABLE_SIZE; i++)
          table[i] = null;
    
    new Thread(new Runnable() {
        
        public void run(){
//                System.out.println("New thread is created ");

                for(int i=0;i<temp.length;i++){
                     if(temp[i]!=null){
                          put(temp[i].getKey());
                     }
                }
//                System.out.println("New thread is switched as primary thread");
                threadstatus = false;
            }
            
           
    }).start();
}
}

