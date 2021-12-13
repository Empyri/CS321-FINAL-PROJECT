****************
* Lab4 - Bioinformatics
* CS 321
* 12/12/2021
* Alayne Rice
* Michael Alberda
* Jordan Whyte
****************
OVERVIEW:

This program takes a .gbk input file, and inputs the gene sequences into a BTree that is then stored as a binary file on the disk.
The Program then can access and search the BTree from the disk, either utilizing a cache or not utilizing cache as requested or needed.
The program will increment the frequency of the different genetic sequences in the search. The program searches with key-value pairs, by converting the string into a distinct long value that is simply
the string converted into said value.
*************************
INCLUDED FILES:
* README.md - This file
* BTree.java - Stores a Btree
* Cache.java - Creates a cache with a set size
* CacheInterface.java - Used to implement the methods required for a cache
* DLLNode.java - The nodes used in the cache's doubly linked list
* GeneBankCreateBTree.java - Takes a GBK file and outputs a Btree file
* GeneBankSearch.java - Takes a file from GeneBankCreateBTree and can search it
*************************
COMPILING AND RUNNING:

IN src:
to compile: javac *.java

to run createBTree: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]
degree has to be greater than 3 and even. gbk file must contain route to file, for the test files it would be data/test1.gbk.
sequence length must be less than 32. cache size is optional, as is debug level, which creates a dump file from the tree.


to run BTreeSearch: java GeneBankSearch  <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]
btree file must contain the local path, which would be (for example) output/test3.gbk.btree.data.6.8 
query file must contain local path as well, such as queries/query6
cache size and debug level control the size of cache if used and where prints occur respectively.


*************************
PROGRAM DESIGN AND IMPORTANT CONCEPTS:

There are some limitations when it comes to the creation of the B-Tree. first off, the degree must be at least 4, and it must be even. 
This program utilizes recursion heavily to produce the effects needed for adding and subtracting, by starting at the height value and recursing down to height=0 from insert to add,
depending on the case of internal to external nodes. The program is also able to time itself in operation of creation of the tree 
with or without a cache as needed.

The cache is Alayne Rice's old implemented cache design with some modifications to better suit the project as needed, and only level one cache was ever
crated in the case of this project, never a 2 level cache system, as that was unneeded.

GeneBankCreateBTree.java utilizes serialization to implement and  store the BTree on disk so that it can be accessed without having to be recreated
which would have removed the point of having stored it in a file to begin with. It also takes in the .GBK file, sterilizes it of unnecessary characters, and creates one
long string, which it then splits into an array of set size K, which is input in the program arguments. That array of strings is then converted into the
keys, which is simply  that array of strings, iterated over and converted into long values as needed.

In the case of GeneBankSearch.java it takes in the string being searched for, converts that into a long, and inputs that into get() for BTree.java, which
performs a recursive search to locate the item in the BTree.
*************************
TESTING

Testing this project was difficult with multiple people working on it at the same time, but we were able to copy the files to a local machine and test them in a different IDE.
We had to write a fair bit before being able to test it but once the baseline was there we could write a bit more and test its functionality.
The ability to test it after adding each part allowed us to see if each part of the project broke it.
What also benefited us was the different command line arguments that could be inputted. Seeing if one scenario i.e. not using a cache was working clued us in to the Btree being functional but the cache was not.
*************************
DISCUSSION

One of the biggest initial hurdles for us was finding a way to output a properly made Btree in a comprehensible format. we used a layered ToString intiially in our
tests that simulated showing the parents and children of the nodes, output total number of objects in the Tree, and the height.
We could never fully create a 100% easy to understand test, but that was what allowed us to understand we made the foundation of everything, the BTree correctly.

from there it was a case of utilizing that tester to ensure that on a larger scale it worked, and then past that we fixed the ToString() to properly output as needed
in the case of the dump file, and utilized the dump file for testing. One large error that hampered us in testing was a simple 1, where we had mistakenly set our recursive search to use
compareTo < 1, instead of < 0, so it was searching for equal or less than, instead of less than, and thus returning nulls. After that
was solved, everything else came together easily.









RUNTIMES:
|arguments                                         | gbk file  |   t   |   k   | cache   |   size    |   time    |
|-----------------------------------------------------------------------------------------------------------------
|java GeneBankCreateBTree 0 8 data/test3.gbk 6     |test3.gbk   |  8    |   6   |disabled|    0      |   103 ms  |
|java GeneBankCreateBTree 1 8 data/test3.gbk 6 100 |test3.gbk   |  8    |   6   |enabled |    100    |   113 ms  |
|java GeneBankCreateBTree 1 8 data/test3.gbk 6 500 |test3.gbk   |  8    |   6   |enabled |    500    |   135 ms  |

|arguments                                                                  | query file | cache    | size |  time  |
|--------------------------------------------------------------------------------------------------------------------
|java GeneBankSearch 0 output/test3.gbk.btree.data.6.8 queries/query6       | query6     | disabled | 0    | 110 ms |
|java GeneBankSearch 1 output/test3.gbk.btree.data.6.8 queries/query6 100   | query6     | enabled  | 100  | 136 ms |
|java GeneBankSearch 1 output/test3.gbk.btree.data.6.8 queries/query6 500   | query6     | enabled  | 500  | 85  ms |



