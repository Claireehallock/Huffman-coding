import java.io.*;

public class Decompress_a_File {
  //command line related - done by Abdurrahman Kazi
  
  public static void main(String[] args) {
		//if the argument length is not two(sourceFile & targetFile), print a line with the format
    if (args.length != 2) {
		 	System.out.println("Usage: java Decompress.java sourceFile targetFile");
		 	System.exit(1);
		 }
    
    //get the source and target file names from shell
		String compressedFileName = args[0];//compressedFile.txt
    String targetFileName = args[1];//target.txt

        Decompress decompressor = new Decompress();
        decompressor.decompressFile(compressedFileName, targetFileName);

        System.out.println("Done!");
	}
}
