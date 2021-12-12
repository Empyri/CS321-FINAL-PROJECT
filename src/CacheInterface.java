/**
 * class to create a java class that simulates hardware cache and it's behavior.
 * The program creates one cache.
 * To determine the size of caches, the program will have the functionality to track number of cache hits,
 * the ratio of hits between the caches, and the total number of cache queries.
 * @author Alayne Rice
 * @Semester Fall 2021
 */


public interface CacheInterface<T> {

    //Linked List behavior

    /**
     * adds an item to the cache.
     * @param data the item to be added.
     */
    public void add(T data);

    /**
     * Removes the last item from the cache.
     */
    public void removeLast();

    /**
     * Removes a specific item from the cache.
     * @param data the item to be removed
     */
    public void remove(T data);

    /**
     * checks to see if a specific item exists in the cache.
     * @param data  The item to be checked for existence.
     * @return True or False.
     */
    public boolean exists(T data);

    /**
     * Will search for the item in the cache.
     * @param data The item  to be searched for
     * @return A node from the Cache with the data.
     */
    public DLLNode<T> find(T data);

    /**
     * moves an item to the front of the list
     * @param data the item to be moved
     */
    public void move(T data);


    //Counting Methods

    /**
     * gets the ratio of hits to references from the cache
     * A hit is when the cache is referenced and a specific item is found.
     * @return the hit ratio in the cache.
     */
    public double getHitRate();

    /**
     * Gets the ratio of misses to references from the cache.
     * A miss is when the cache is referenced and a specific item isn't found.
     * @return the miss ratio in the cache.
     */
    public double getMissRate();

    /**
     *get the total amount of misses from the cache.
     * A miss is when the cache is referenced and a specific item isn't found.
     * @return the number of misses in the cache.
     */
    public double getMisses();

    /**
     * gets the amount of hits from the cache
     * A hit is when the cache is referenced and a specific item is found.
     * @return the number of positive hits in the cache
     */
    public double getHits();

    /**
     * Returns the total number of references to the cache.
     * A reference is when the cache is looked at.
     * @return the number of references.
     */
    public double getReferences();



}