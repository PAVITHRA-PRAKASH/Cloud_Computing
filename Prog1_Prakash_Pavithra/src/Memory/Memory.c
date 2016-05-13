#include<stdio.h>
#include<string.h>
#include<time.h>
#include<sys/time.h>
#include<stdlib.h>
#include<pthread.h>

#define BLOCK1 1;
#define BLOCK2 1024;
#define BLOCK3 1048576;


int i,p;

double duration;
struct timeval start,stop;
double throughput;
int randombyte;

double throughputrwbs[2],throughputrwkbs[2],throughputrwmbs[2],throughputrwbr[2],throughputrwkbr[2],throughputrwmbr[2];
double latencyrwbs[2],latencyrwkbs[2],latencyrwmbs[2],latencyrwbr[2],latencyrwkbr[2],latencyrwmbr[2];
static int a = 0,b = 0;

void *threadone(){

char *filecontent = (char *)malloc(10485760);
char *array1 = (char *) malloc(10485760);
char *array2 = (char *) malloc(10485760);
char *array3 = (char *) malloc(10485760);

printf("Inside threadone\n");
gettimeofday(&start,NULL);
for(i=0;i<500;i++){
memcpy(&filecontent[i],&array1[i],1);
}
gettimeofday(&stop,NULL);
latencyrwbs[b] = stop.tv_usec - start.tv_usec;
throughputrwbs[a] = (500.0*1000000)/(latencyrwbs[b]*1048576.0);

gettimeofday(&start,NULL);
for(i=0;i<11;i++){
memcpy(&filecontent[i],&array2[i],1024);
}
gettimeofday(&stop,NULL);
latencyrwkbs[b] = stop.tv_usec - start.tv_usec;
throughputrwkbs[a] = (11.0*1000000)/(latencyrwkbs[b]*1024.0);

//printf("The value of latency and throughput is %lf %lf",latencyrwkbs[b],throughputrwkbs[b]);

gettimeofday(&start,NULL);
for(i=0;i<5;i++){
memcpy(&filecontent[i],&array3[i],1048576);
}
gettimeofday(&stop,NULL);
latencyrwmbs[b] = stop.tv_usec - start.tv_usec;
throughputrwmbs[a] = (5.0*1000000)/latencyrwmbs[b];

randombyte = rand()%999999;

gettimeofday(&start,NULL);
for(i=0;i<500;i++){
memcpy(&filecontent[randombyte],&array1[randombyte],1);
}
gettimeofday(&stop,NULL);
latencyrwbr[b] = stop.tv_usec - start.tv_usec;
throughputrwbr[a] = (500.0*1000000)/(latencyrwbr[b]*1048576.0);


gettimeofday(&start,NULL);
for(i=0;i<11;i++){
memcpy(&filecontent[randombyte],&array2[randombyte],1024);
}
gettimeofday(&stop,NULL);
latencyrwkbr[b] = stop.tv_usec - start.tv_usec;
throughputrwkbr[a] = (11.0*1000000)/(latencyrwkbr[b]*1024.0);


gettimeofday(&start,NULL);
for(i=0;i<5;i++){
memcpy(&filecontent[randombyte],&array3[randombyte],1048576);
}
gettimeofday(&stop,NULL);
latencyrwmbr[b] = stop.tv_usec - start.tv_usec;
throughputrwmbr[a] = (5.0*1000000)/latencyrwmbr[b];


a++;
b++;

}

int main(){

pthread_t t[2];

threadone();
printf("\n>>>>>>>>>>>>>>>>>>>>SINGLE THREAD<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
printf("Sequential 1B memcopy - Time elapsed : %lf microseconds\n",latencyrwbs[0]);
printf("Sequential 1B memcopy - Throughput : %lf MBps\n\n",throughputrwbs[0]);

printf("Sequential 1KB memcopy - Time elapsed : %lf microseconds\n",latencyrwkbs[0]);
printf("Sequential 1KB memcopy - Throughput : %lf MBps\n\n",throughputrwkbs[0]);

printf("Sequential 1MB memcopy - Time elapsed : %lf microseconds\n",latencyrwmbs[0]);
printf("Sequential 1MB memcopy - Throughput : %lf MBps\n\n",throughputrwmbs[0]);

printf("Random 1B memcopy - Time elapsed : %lf microseconds\n",latencyrwbr[0]);
printf("Random 1B memcopy - Throughput  : %lf MBps\n\n",throughputrwbr[0]);

printf("Random 1KB memcopy - Time elapsed : %lf microseconds\n",latencyrwkbr[0]);
printf("Random 1KB memcopy - Throughput : %lf MBps\n\n",throughputrwkbr[0]);

printf("Random 1MB memcopy - Time elapsed : %lf microseconds\n",latencyrwmbr[0]);
printf("Random 1MB memcopy - Throughput : %lf MBps\n\n",throughputrwmbr[0]);


printf("\n>>>>>>>>>>>>>>>>>>>>DOUBLE THREAD<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");

pthread_create(&t[0],NULL,threadone,NULL);
pthread_create(&t[1],NULL,threadone,NULL);

pthread_join(t[0],NULL);
pthread_join(t[1],NULL);

double avglatencyrwbs = (latencyrwbs[2]+latencyrwbs[1])/2;
printf("Sequential 1B memcopy - Time elapsed : %lf microseconds\n",avglatencyrwbs);
double avgthroughputrwbs = (throughputrwbs[2]+throughputrwbs[1])/2;
printf("Sequential 1B memcopy - Throughput : %lf MBps\n\n",avgthroughputrwbs);

double avglatencyrwkbs = (latencyrwkbs[2]+latencyrwkbs[1])/2;
printf("Sequential 1KB memcopy - Time elapsed : %lf microseconds\n",avglatencyrwkbs);
double avgthroughputrwkbs = (throughputrwkbs[2]+throughputrwkbs[1])/2;
printf("Sequential 1KB memcopy - Throughput  : %lf MBPs\n\n",avgthroughputrwkbs);

double avglatencyrwmbs = (latencyrwmbs[2]+latencyrwmbs[1])/2;
printf("Sequential 1MB memcopy - Time elapsed : %lf microseconds\n",avglatencyrwmbs);
double avgthroughputrwmbs = (throughputrwmbs[2]+throughputrwmbs[1])/2;
printf("Sequential 1MB memcopy - Throughput  : %lf MBps\n\n",avgthroughputrwmbs);



double avglatencyrwbr = (latencyrwbr[2]+latencyrwbr[1])/2;
printf("Random 1B memcopy - Time elapsed : %lf microseconds\n",avglatencyrwbr);
double avgthroughputrwbr = (throughputrwbr[2]+throughputrwbr[1])/2;
printf("Random 1B memcopy - Throughput  : %lf MBps\n\n",avgthroughputrwbr);

double avglatencyrwkbr = (latencyrwkbr[2]+latencyrwkbr[1])/2;
printf("Random 1KB memcopy - Time elapsed : %lf microseconds\n",avglatencyrwkbr);
double avgthroughputrwkbr = (throughputrwkbr[2]+throughputrwkbr[1])/2;
printf("Random 1KB memcopy - Throughput  : %lf MBps\n\n",avgthroughputrwkbr);

double avglatencyrwmbr = (latencyrwmbr[2]+latencyrwmbr[1])/2;
printf("Random 1MB memcopy - Time elapsed : %lf microseconds\n",avglatencyrwmbr);
double avgthroughputrwmbr = (throughputrwmbr[2]+throughputrwmbr[1])/2;
printf("Random 1MB memcopy - Throughput  : %lf MBps\n\n",avgthroughputrwmbr);

return 0;

}

