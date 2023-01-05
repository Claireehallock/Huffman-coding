
public class Main {
	//command line related - done by Abdurrahman Kazi
  public static void main(String[] args) {
		
    if (args.length != 3) {
		 	System.out.println("Usage: java Main.java sourceFile compressedFile targetFile");
		 	System.exit(1);
		 }
    

		String sourceFileName = args[0];//source.txt
    	String compressedFileName = args[1];//compressedFile.txt
    	String targetFileName = args[2];//target.txt

        Compress compressor = new Compress();
		compressor.compressFile(sourceFileName, compressedFileName); 

        Decompress decompressor = new Decompress();
        decompressor.decompressFile(compressedFileName, targetFileName);

        System.out.println("Done!");
	}
}
