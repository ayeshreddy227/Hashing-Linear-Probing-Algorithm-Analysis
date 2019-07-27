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
public class HashTableWithRehash {
      private static int TABLE_SIZE = 128;
      private static int size = 0;
      public boolean threadstatus = false;

    public static int getSize() {
        return size;
    }
      private static double DEFAULT_LOAD_FACTOR=0.75;
      HashEntry[] table;

    public static void setSize(int size) {
        HashTableWithRehash.size = size;
    }
 
    HashTableWithRehash(int table_size) {
          TABLE_SIZE = table_size;
            table = new HashEntry[TABLE_SIZE];
            for (int i = 0; i < TABLE_SIZE; i++)
                  table[i] = null;
      }
 
      public int get(int key) {
            int hash = (key % TABLE_SIZE);
            while (table[hash] != null && table[hash].getKey() != key)
                  hash = (hash + 1) % TABLE_SIZE;
            if (table[hash] == null)
                  return -1;
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
                rehash();
            }
        }
      
      public void rehash() { 
        TABLE_SIZE = 2*TABLE_SIZE;
        HashEntry[] temp = table; 
        size=0;
        table = new HashEntry[TABLE_SIZE];
  
        for (int i = 0; i < TABLE_SIZE; i++) { 
            table[i] = null; 
        } 
        for (int i = 0; i < temp.length; i++) { 
            if(temp[i]!=null) put(temp[i].getKey()); 
        } 
    } 
      
}