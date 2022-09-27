public class Compress_a_File {
  //command line related - done by Abdurrahman Kazi
  public static void main(String[] args) {
		//if the argument length is not two(sourceFile & targetFile), print a line with the format
    if (args.length != 2) {
		 	System.out.println("Usage: java Compress_a_File.java sourceFile targetFile");
		 	System.exit(1);
		 }
    
    //get the source and target file names from shell
		String sourceFileName = args[0];//source.txt
    String compressedFileName = args[1];//compressedFile.txt

        Compress compressor = new Compress();
		compressor.compressFile(sourceFileName, compressedFileName); 

        System.out.println("Done!");
	}
}
