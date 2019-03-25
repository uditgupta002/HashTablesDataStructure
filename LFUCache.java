package com.ud.learning;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.TreeMap;

public class LFUCache {

    public static void main(String[] args) {

	LFUC<Integer, String> lfuCache = new LFUC<Integer, String>(5);

	lfuCache.put(1, "Udit");
	lfuCache.put(2, "Chirag");
	lfuCache.put(3, "Shasha");
	lfuCache.put(4, "Adi");
	lfuCache.put(5, "Sandeep");

	lfuCache.printCurrentState();

	lfuCache.put(6, "Sharadhi");

	lfuCache.printCurrentState();

	lfuCache.get(3);
	lfuCache.get(3);
	lfuCache.get(2);
	lfuCache.get(2);
	lfuCache.get(5);

	lfuCache.printCurrentState();

	lfuCache.put(1, "Udit");

	lfuCache.printCurrentState();

    }

    private static class LFUC<Key, Value> {

	private int size;
	private HashMap<Key, Value> values;
	private HashMap<Key, Integer> counts;
	private TreeMap<Integer, LinkedHashSet<Key>> frequencies;

	public LFUC(int size) {
	    this.size = size;
	    this.values = new HashMap<Key, Value>(size);
	    this.counts = new HashMap<Key, Integer>();
	    this.frequencies = new TreeMap<Integer, LinkedHashSet<Key>>();
	}

	public Value get(Key key) {
	    if (this.values.get(key) != null) {

		int frequency = this.counts.get(key);
		LinkedHashSet<Key> freqMap = this.frequencies.get(frequency);
		freqMap.remove(key);
		if (freqMap.size() == 0) {
		    this.frequencies.remove(frequency);
		}

		LinkedHashSet<Key> newFreqMap = this.frequencies.get(frequency + 1);
		if (newFreqMap != null) {
		    newFreqMap.add(key);
		} else {
		    this.frequencies.put(frequency + 1, new LinkedHashSet<Key>());
		    this.frequencies.get(frequency + 1).add(key);
		}
		this.counts.put(key, frequency + 1);
		return this.values.get(key);
	    } else {
		return null;
	    }
	}

	public void put(Key key, Value value) {

	    if (this.values.get(key) != null) {
		this.get(key);
		this.values.put(key, value);
	    } else {
		if (this.values.size() == this.size) {
		    Entry<Integer, LinkedHashSet<Key>> entry = this.frequencies.firstEntry();
		    if (entry != null) {
			Integer frequency = entry.getKey();
			LinkedHashSet<Key> lowestEntryList = entry.getValue();
			Key lowestEntry = lowestEntryList.iterator().next();
			lowestEntryList.remove(lowestEntry);
			if (lowestEntryList.size() == 0) {
			    this.frequencies.remove(frequency);
			}
			this.counts.remove(lowestEntry);
			this.values.remove(lowestEntry);
		    }
		}

		this.values.put(key, value);
		this.counts.put(key, 1);
		LinkedHashSet<Key> freqMap = this.frequencies.get(1);
		if (freqMap == null) {
		    this.frequencies.put(1, new LinkedHashSet<Key>());
		    this.frequencies.get(1).add(key);
		} else {
		    freqMap.add(key);
		}
	    }
	}

	public void printCurrentState() {
	    System.out.println("----------------------------------------------");
	    System.out.println("Current Keys and Values : ");
	    System.out.println(this.values);
	    System.out.println("Current Counts : ");
	    System.out.println(this.counts);
	    System.out.println("Current Frequencies : ");
	    System.out.println(this.frequencies);
	}

    }
}
