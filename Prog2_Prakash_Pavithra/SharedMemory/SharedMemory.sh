#!/bin/bash
javac SharedMem.java smt2.java smt4.java smt8.java
java SharedMem
rm /mnt/raid/Blockfile0 /mnt/raid/Blockfile1 /mnt/raid/Blockfile2 /mnt/raid/Blockfile3 /mnt/raid/Blockfile4 /mnt/raid/Blockfile5 /mnt/raid/Blockfile6 /mnt/raid/Blockfile7 /mnt/raid/Blockfile8 /mnt/raid/Blockfile9
java smt2
rm /mnt/raid/Blockfile0 /mnt/raid/Blockfile1 /mnt/raid/Blockfile2 /mnt/raid/Blockfile3 /mnt/raid/Blockfile4 /mnt/raid/Blockfile5 /mnt/raid/Blockfile6 /mnt/raid/Blockfile7 /mnt/raid/Blockfile8 /mnt/raid/Blockfile9
java smt4
rm /mnt/raid/Blockfile0 /mnt/raid/Blockfile1 /mnt/raid/Blockfile2 /mnt/raid/Blockfile3 /mnt/raid/Blockfile4 /mnt/raid/Blockfile5 /mnt/raid/Blockfile6 /mnt/raid/Blockfile7 /mnt/raid/Blockfile8 /mnt/raid/Blockfile9
java smt8
rm /mnt/raid/Blockfile0 /mnt/raid/Blockfile1 /mnt/raid/Blockfile2 /mnt/raid/Blockfile3 /mnt/raid/Blockfile4 /mnt/raid/Blockfile5 /mnt/raid/Blockfile6 /mnt/raid/Blockfile7 /mnt/raid/Blockfile8 /mnt/raid/Blockfile9

