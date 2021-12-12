/**
 * class to create a java class that simulates hardware cache and it's behavior.
 * The program creates one cache.
 * To determine the size of caches, the program will have the functionality to track number of cache hits,
 * the ratio of hits between the caches, and the total number of cache queries.
 * @author Alayne Rice
 * @Author Jordan Whyte
 * @Semester Fall 2021
 */

import java.util.LinkedList;
public class Cache<T> implements CacheInterface<T> {
    private int size;
    private double hits;
    private double references;
    private LinkedList list;
    private double misses;

    /**
     * the main constructor of the class.
     * It instantiates all references, hits, and misses at 0.
     * It makes a linked list.
     * The cache will manage references to itself, and track how often items are inside or not inside the cache where it is applicable.
     * if an item added would make the cache too big, the last item will be removed.
     * @param x the size of the cache./
     */
    public Cache(int x){
        size = x;
        list = new LinkedList();
        references = 0;
        misses = 0;
        hits = 0;


    }

    @Override
    /**
     * adds an item to the cache.
     * @param data the item to be added.
     */
    public void add(T data) {
        list.addFirst(data);
        if (list.size() > size){
            removeLast();
        }

    }

    @Override
    /**
     * Removes the last item from the cache.
     */
    public void removeLast() {
        list.removeLast();
    }

    @Override
    /**
     * Removes a specific item from the cache.
     * @param data the item to be removed
     */
    public void remove(T data) {
        list.remove(data);
    }

    @Override
    /**
     * checks to see if a specific item exists in the cache.
     * @param data  The item to be checked for existence.
     * @return True or False.
     */
    public boolean exists(T data) {
        int determiner = list.indexOf(data);
        switch (determiner) {
            default:
                return true;

            case -1:
                return false;

        }
    }
    @Override
    /**
     * Will search for the item in the cache.
     * if It can find it, it will increment number of hits and return the item.
     * if it misses, it returns null and adds the item to the front of the cache. It also accumulates misses.
     * @param data The item  to be searched for
     * @return A node from the Cache with the data.
     * @return Null if the item is not in the list.
     */
    public DLLNode<T> find(T data) {
        int location = list.indexOf(data);
        references ++;
        switch (location){
            default :
                hits ++;
                move(data);
                DLLNode<T> retVal = new DLLNode<T>(data);
                return retVal;

            case -1:
                misses++;
                add(data);
                return null;
        }
    }

    @Override
    /**
     * moves an item to the front of the list
     * @param data the item to be moved
     */
    public void move(T data) {
        T dataItem = data;
        if(list.indexOf(data) == -1){
            add(data);
        }
        list.remove(data);
        add(data);

    }

    @Override
    /**
     * gets the ratio of hits to references from the cache
     * A hit is when the cache is referenced and a specific item is found.
     * @return the hit ratio in the cache.
     */
    public double getHitRate() {
        return hits/references;
    }

    @Override
    /**
     * Gets the ratio of misses to references from the cache.
     * A miss is when the cache is referenced and a specific item isn't found.
     * @return the miss ratio in the cache.
     */
    public double getMissRate() {
        return misses/references;
    }

    @Override
    /**
     *get the total amount of misses from the cache.
     * A miss is when the cache is referenced and a specific item isn't found.
     * @return the number of misses in the cache.
     */
    public double getMisses() {
        return misses;
    }

    @Override
    /**
     * gets the amount of hits from the cache
     * A hit is when the cache is referenced and a specific item is found.
     * @return the number of positive hits in the cache
     */
    public double getReferences(){
        return references;
    }

    @Override
    /**
     * Returns the total number of references to the cache.
     * A reference is when the cache is looked at.
     * @return the number of references.
     */
    public double getHits() {
        return  hits;
    }





}
