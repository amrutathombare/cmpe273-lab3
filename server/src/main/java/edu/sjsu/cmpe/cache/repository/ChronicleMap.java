package edu.sjsu.cmpe.cache.repository;

import edu.sjsu.cmpe.cache.domain.Entry;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Amruta on 5/16/2015.
 */
public class ChronicleMap implements CacheInterface {

    private final Map<Long, Entry> memoryMap;

    public ChronicleMap() throws IOException {
        File file = new File("ChronicleMapFileC.dat");
        memoryMap = ChronicleMapBuilder.of(Long.class, Entry.class).createPersistedTo(file);
    }

    @Override
    public Entry save(Entry newEntry) {
        checkNotNull(newEntry, "newEntry instance should not be null");
        memoryMap.putIfAbsent(newEntry.getKey(), newEntry);
        return newEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        return memoryMap.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(memoryMap.values());
    }
}