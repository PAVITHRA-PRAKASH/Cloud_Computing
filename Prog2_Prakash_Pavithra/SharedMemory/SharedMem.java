import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SharedMem{

/*SharedMem Class sorts the dataset by reading chunks of data as an array and sorts the array block read using merge 
sort. The sorted array is written into a file which is added to a list of files, now the list of sorted files is again
sorted and merged into an output file*/

	static File dataset = new File("/mnt/raid/1gbdata");
	static int blocksize = (int)dataset.length()/20;
	static int numoffiles = 20;
	List<File> listofFiles = new ArrayList<File>();

/*readBlock method takes filereader as a parameter and reads lines from the input file untill specified length of the file*/
	public void readBlock(FileReader readdata) throws IOException{

		BufferedReader bfr = new BufferedReader(readdata);
		int i=0;
		String[] block = new String[blocksize/100];

		for(int j=0;j<numoffiles;j++){
			for(i=0; i<blocksize/100;i++){
				block[i] = bfr.readLine();
			}

			sortBlock(block);

			File blockfile = new File("/mnt/raid/blockfile"+j);
			FileWriter writedata = new FileWriter(blockfile);
			BufferedWriter bfw = new BufferedWriter(writedata);

			for (int m=0;m<blocksize/100;m++){
				bfw.write(block[m]);
				bfw.newLine();
			}
			bfw.close();
			listofFiles.add(blockfile);
		}
	}

/*sortBlock sorts the block read in the readBlock method and this function is implicitly called inside the readBlock Method, 
the sorting is done based on the merge sort algorithm where the content is divided into smallest unit and compared to further 
merge the units here the block array is divided till we have two lines */

	public void sortBlock(String[] data) throws IOException{

		if(data.length >= 2){

			int mid = data.length/2;
			String left[] = new String[mid];
			String right[] = new String[data.length - mid];
			for(int k=0;k<left.length;k++){
				left[k]=data[k];
			}
			for(int k=0;k<right.length;k++){
				right[k] = data[k+mid];
			}

			sortBlock(left); 
			sortBlock(right);
			mergeBlock(left,right,data);

		}
	}


/*mergeBlock method is implicitly called by the sortBlock method which divides the block till we are left with single line
the comparison is done to sort with the help of compareTo function, the lines in the file are 
sorted based on the ASCII value of the 10 byte key in each line of the dataset*/
	public void mergeBlock(String[] l,String[] r,String[] d){
		int ll = l.length;
		int rl = r.length;
		int i=0;
		int j=0;
		int m =0;

		while(m<d.length){
			if(j>=rl || (i<ll && l[i].substring(0,10).compareTo(r[j].substring(0,10))<0)){
				d[m]=l[i];
				i++;
			} 
			else{
				d[m]=r[j];
				j++;
			}

			m++;

		}
	}

/*mergefiles method takes list of files(sorted) as a parameter and this is generated once the sorting is done called inside the 
readBlock method, the list of files is iterated to take first lines from all the sorted files and one with minimum ASCII value is written into the file
in a similar way all the lines of the file is compared with all other files in the list and final sorted merged file is written into the output file*/

	public void mergefiles(List<File> lof) throws IOException{

		File output = new File("/mnt/raid/outputFile");
		FileWriter fos = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fos);

		String lines [] = new String[lof.size()];
		int p=0;

		List<BufferedReader> list_of_readers = new ArrayList<BufferedReader>();
		FileReader frf = null;
		for(File f : lof){
			frf = new FileReader(f);
			BufferedReader brf = new BufferedReader(frf);
			list_of_readers.add(brf);
			lines[p]= brf.readLine();
			p++;
		}

		for(int y=0; y < dataset.length()/100;y++){
			String first = lines[0];
			int minFileIndex = 0;
			for(int z=0;z<lof.size();z++){
				if(lines[z]!=null && first!=null && lines[z].substring(0, 10).compareTo(first.substring(0, 10)) < 0){
					first = lines[z];
					minFileIndex = z;
				}
			}
			if(first!=null){
				bw.write(first);
				bw.newLine();
			}
			lines[minFileIndex] = list_of_readers.get(minFileIndex).readLine(); 
		}

		for(int h=0;h<list_of_readers.size();h++){
			list_of_readers.get(h).close();
		}

		bw.close();
	}

	public static void main (String[] args) throws IOException{
		SharedMem obj = new SharedMem();
		FileReader readdata = new FileReader(dataset);
//Time taken before and after sorting is noted
		long start = System.nanoTime();
		obj.readBlock(readdata);
		obj.mergefiles(obj.listofFiles);
		long end = System.nanoTime();
		readdata.close();
		System.out.println("Start time: "+start);
		System.out.println("End time:"+end);
		System.out.println("Time Taken to Sort(Single Thread Used) is "+(end-start)+"nano seconds\n");

	}
}
