import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.*;
import java.io.Serializable;

/**
 * This file was done by David Vu and Claire Hallock
 */
public class Compress {
	
  /**
	 * Asks user for input file (filename), and calls compress(text) on the file as a String, and outputs this to output file (filename2)
	 */
	public void compressFile(String filename, String outputFilename) {
		String fileText = "";
    //read the uncompressed text and store them in a string filetext
    try {
			int current;
			FileInputStream inStream = new FileInputStream(filename);

			current = inStream.read();
			while (current != -1) {
				fileText += (char) current;
				current = inStream.read();
			}
			inStream.close();

			fileText += (char)(003);

		} catch (IOException e) {
			e.printStackTrace();
		}

		compress(fileText, outputFilename);
    
	}

	/**
	 * Call buildhuffmantree() and encodeText()
	 * CLAIRE: complete this after finishing encodeText and generateEncodingMap
	 * @param text
	 */
	public void compress(String fileText, String outputFilename) {
		//create a huffman tree using the file text
    EncodingTreeNode huffmanTree = buildHuffmanTree(fileText);
    //call encode text and send the root, the text, and the target file name
		encodeText(huffmanTree, fileText, outputFilename);
	}

	/**
	 * Calls generateFrequencyMap and then does the huffman thing
	 */
	public EncodingTreeNode buildHuffmanTree(String fileText) {
		Map<Character, Integer> freqMap = generateFrequencyMap(fileText);
		Set<Map.Entry<Character, Integer>> entrySet = freqMap.entrySet();
		
		PriorityQueue<EncodingTreeNode> huffmanPQ = new PriorityQueue<>();
		EncodingTreeNode treeNode;
		for (Map.Entry<Character, Integer> e: entrySet) {
			treeNode = new EncodingTreeNode(e.getKey(), e.getValue());
			huffmanPQ.offer(treeNode);
		}
		
		EncodingTreeNode left, right, combinedTree;
		while (huffmanPQ.size() > 1) {
			left = huffmanPQ.poll();
			right = huffmanPQ.poll();
			combinedTree = new EncodingTreeNode(left, right);
			huffmanPQ.offer(combinedTree);
		}
		
		combinedTree = huffmanPQ.poll();
		return combinedTree;
	}

	/**
	 * Reads through all the characters of a string and build a map (can be an array) of all unique characters
	 * and their frequencies
	 */
	public Map<Character, Integer> generateFrequencyMap(String fileText) {
		Map<Character, Integer> freqMap = new HashMap<>();
		for (int i = 0; i < fileText.length(); i++) {
			char current = fileText.charAt(i);
			if (freqMap.containsKey(current)) {
				freqMap.put(current, freqMap.get(current) + 1);
			} else {
				freqMap.put(current, 1);
			}
		}
		
		return freqMap;
	}
	
	/**
	 * CLAIRE: Write encoded text in binary into a file by referencing the huffman tree. Calls generateEncodingMap
	 */
	public void encodeText(EncodingTreeNode tree, String fileText, String outputFilename) {
		Map<Character, String> encodingMap = new HashMap<>();
		generateEncodingMap(tree, encodingMap, "");

		String encodedText = "";
        for(int i = 0; i < fileText.length(); i++){
          encodedText += encodingMap.get(fileText.charAt(i));
        }

        //This is the part that actually encodes the data in the output file
        try{
            FileOutputStream fileOut = new FileOutputStream(outputFilename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            BitOutputStream bitOut = new BitOutputStream(fileOut);
            
            objectOut.writeObject(tree);
            bitOut.writeBit(encodedText);
            bitOut.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
	
	/**
	 * CLAIRE: Create a map (saved in encodingMap) that matches each character to the path it takes to get to it on the huffman tree. Use recusion probably
	 */
	public void generateEncodingMap(EncodingTreeNode tree, Map<Character, String> encodingMap, String path) {
		if(tree == null) {
			return;
		}
		
		//When at a leaf node (node with a character), add its path to the encoding map, 
		if (tree.left == null && tree.right == null) {
			encodingMap.put(tree.element, path);
		}
		
		generateEncodingMap(tree.left, encodingMap, path + "0");
		generateEncodingMap(tree.right, encodingMap, path + "1");
	}

    public class BitOutputStream {
        private FileOutputStream output;
        int num=0;
        int pos=0;
        
        public BitOutputStream(File file) throws IOException {
            this.output = new FileOutputStream(file);
        }

        public BitOutputStream(FileOutputStream FOS) throws IOException {
            this.output = FOS;
        }

        public void writeBit(String bitString) throws IOException {
        	
        	for (int i = 0; i < bitString.length(); i++) {
            	writeBit(bitString.charAt(i));
            }
        }
      
        //bit can only be '0' or '1'
        public void writeBit(char bit) throws IOException {
            num = (num << 1);
            if (bit == '1'){
              num +=1;
            }
      
            pos +=1;

            if (pos == 8){//resets for next byte
              output.write(num);
              num = 0;
              pos = 0;
            }
        }   

        public void close( ) throws IOException {
            if (pos > 0){ // if 0 then program is already complete
                output.write(num);
            }
            output.close();
        }
    }

}
