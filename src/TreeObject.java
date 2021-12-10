import com.sun.source.tree.Tree;

/**
 * A class representing the objects stored in the B-Tree.
 * Holds a Generic object of type T.
 * @param <T> Generic Objects being held
 * @author Alayne Rice
 * @Semester Fall 2021
 */
public class TreeObject<T extends Comparable<T>>{
  private T key;
  private int frequencycount; //it looks like we need to track frequency of objects so perhaps it is best that It is done here?


    /**
     * Creates an object with a generic key object inside.
     * It is considered new so it starts with a frequency of 0 as it has never been encountered/seen.
     * @param Key The object contained in the TreeObject.
     */
  public TreeObject(T Key) {
      this.key = Key;
      frequencycount = 0; //Always starts with a frequency of zero
  }

    /**
     * Creates an object with a generic Key object and a set time this object has been encountered.
     * @param item The object stored in the the tree object
     * @param freq the frequency this object has been encountered.
     */
  public TreeObject(T item, int freq) { //this is in case an object needs to be deleted and an identical copy needs to be inserted, I am not sure if this is needed yet
      this.key = item;
      frequencycount = freq;
      }

    /**
     * Sets the frequency of the object.
     * @param newFrequency The new frequency of the object.
     */
    public void setFrequencyCount(int newFrequency) {
      frequencycount= newFrequency;
    }

    /**
     * increments the frequency of the object, so it can be accounted for.
     */
    public void  incrementFrequency(){
        frequencycount ++;
    }


    /**
     * gets the frequency count.
     * @return The frequency count.
     */
    public int getFrequencyCount() {
        return frequencycount;
    }

    /**
     * returns the key inside the TreeObject
     * @return the Key.
     */
    public T getKey(){
        return key;
  }

    /**
     * Compares the key inside TreeObject with a different key.
     * @param compareObject The key to be compared against the current Key.
     * @return 1 if object is greater, 0 if object is lesser
     */
    public int compare(T compareObject) {
        return key.compareTo(compareObject);
    }


    }

