package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Amruta on 5/6/2015.
 */
public class RendezvousHash<T> {

    private final HashFunction myhash;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle =
            new TreeMap<Integer, T>();

    public RendezvousHash(int numberOfReplicas, Collection<T> nodes) {

        this.myhash = Hashing.md5();
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(myhash.hashString(node.toString() + i, Charset.defaultCharset()).asInt(),
                    node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(myhash.hashString(node.toString() + i, Charset.defaultCharset()).asInt());
        }
    }

    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        Integer maxHash = Integer.MIN_VALUE;
        T maxNode = null;

        for (Map.Entry<Integer, T> node: circle.entrySet()) {
            int hash = myhash.newHasher()
                    .putString(key.toString(),Charset.defaultCharset())
                    .putString(node.getValue().toString(),Charset.defaultCharset()).hash().asInt();

            if(hash>maxHash) {
                maxHash = hash;
                maxNode = node.getValue();
            }
        }
        System.out.println("Node: " + maxNode);
        return maxNode;
    }
}
