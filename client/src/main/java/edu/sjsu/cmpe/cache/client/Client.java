package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final static List listServersCache = new ArrayList();

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");

        listServersCache.add("http://localhost:3000");
        listServersCache.add("http://localhost:3001");
        listServersCache.add("http://localhost:3002");

        ConsistentHash consistentHash =new ConsistentHash(listServersCache.size(), listServersCache);

        System.out.println("--------Putting into the cache  (Consistent Hashing)--------" + "\n");

        for(int i = 1; i < 11; i++) {
            String cacheString = consistentHash.get(i).toString();
            CacheServiceInterface cache = new DistributedCacheService(cacheString);
            cache.put(i,String.valueOf((char) (i + 96)));
            System.out.println("PUT: " + i + " => " + String.valueOf((char) (i + 96)));
        }

        System.out.println("--------Getting cache from the servers (Consistent Hashing)-------- " + "\n");

        for(int i = 1; i < 11; i++) {
            String cacheString = consistentHash.get(i).toString();
            CacheServiceInterface cache = new DistributedCacheService(cacheString);
            String value = cache.get(i);
            System.out.println("GET: " + i + " => "+ value);
            System.out.println("Cache retrieved : " + value);
        }

//        RendezvousHash rendezvousHash = new RendezvousHash(listServersCache.size(), listServersCache);
//
//        System.out.println("--------Putting into the cache servers (Rendezvous Hashing)--------" + "\n");
//
//        for(int i = 1; i < 11; i++) {
//            String cacheString = rendezvousHash.get(i).toString();
//            CacheServiceInterface cache = new DistributedCacheService(cacheString);
//            cache.put(i,String.valueOf((char) (i + 96)));
//            System.out.println("PUT: " + i + " => " + String.valueOf((char) (i + 96)));
//        }
//
//        System.out.println("-------Getting cache from the servers (Rendezvous Hashing)------- " + "\n");
//
//        for(int i = 1; i < 11; i++) {
//            String cacheString = rendezvousHash.get(i).toString();
//            CacheServiceInterface cache = new DistributedCacheService(cacheString);
//            String value = cache.get(i);
//            System.out.println("GET: " + i + " => "+ value);
//            System.out.println("Cache retrieved : " + value);
//        }

        System.out.println("Existing Cache Client...");
    }
}
