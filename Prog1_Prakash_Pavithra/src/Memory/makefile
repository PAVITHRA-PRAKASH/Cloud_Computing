CC=gcc
#define the target file
all:memory
memory: Memory.o
	$(CC) Memory.o -pthread -o memory
	$(CC) -c Memory.c
clean :
	rm Memory.o