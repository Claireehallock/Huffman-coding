import java.io.*;

public class Decompress {
  
  //Asks user for the file and calls decompress()
    //Done mostly by Claire Hallock and David Vu
	public void decompressFile(String filename, String target) {
		String encodedText = "";
		try {
			int current;
			FileInputStream inStream = new FileInputStream(filename);
			ObjectInputStream objInp = new ObjectInputStream(inStream);
      //^read throught the compressed file
			EncodingTreeNode root = (EncodingTreeNode) objInp.readObject();
      //^ get the tree root

			String curBits = "";
			current = inStream.read();
			//turn the compressed file into binary
      while (current != -1) {
				curBits = Integer.toBinaryString(current);
				while(curBits.length() < 8 && inStream.available() > 0){
					curBits = "0" + curBits;
				}

				encodedText += curBits;
				curBits = "";
				current = inStream.read();
			}
			inStream.close();
			//call decompress
			decompress(root, encodedText, target);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//sending to target file - done by Abdurrahman Kazi
  //Uses the huffman tree to look at the sequence of bits and convert them to characters
	public void decompress(EncodingTreeNode huffmanTree, String encodedText, String target) {
		//turn the binary string encodedText into a decompressed string
    String decodedText = decodeText(huffmanTree, encodedText);
		//write the decoded text into another file: target
		try {
			FileWriter writer = new FileWriter(target);
			BufferedWriter bwr = new BufferedWriter(writer);
			bwr.write(decodedText);
			bwr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//Writes the decoded text into a file. Called in decompress
	public String decodeText(EncodingTreeNode huffmanTree, String encodedText) {
    //traversing tree to gain leafs - dne by Abdurrahman Kazi
		String decodedText = "";
    //turn the binary text into a char array
		char[] bits = encodedText.toCharArray(); 
		StringBuffer sb = new StringBuffer();
		EncodingTreeNode current = huffmanTree; 

    //go through the char array. everytime you hit a leaf, add it to the stringbuffer sb. if it is a node, go down either the left or right depending on whether it is a 0 or a 1
		for(int i = 0; i < bits.length; i++){
			if(current.left == null && current.right == null){
				sb.append(current.element);
				current = huffmanTree;
			}
			
			char currentChar = encodedText.charAt(i);
			if(currentChar == '1'){
				current = current.right;
			}else{
				current = current.left;
			}
		}
		
		//Add last character
		//sb.append(current.element);
		//Last character is now set to "End of text" character to delineate where the code stops, so should nont be added to the decompress
		
		decodedText = sb.toString();
		return decodedText;
	}
}
