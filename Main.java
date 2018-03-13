import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;

public class Main {
	public static void main (String args[]){
		
		Scanner input = new Scanner(System.in);
		
		//System.out.print("Please enter the file path: ");
		//String baseDir = input.nextLine();
		String baseDir = "/home/ugb/walker.sakatch/Desktop/SENG 300/GRPAssign1/";
		File folder = new File(baseDir);
		File[] files = folder.listFiles();
		
		for(int i = 0; i < files.length; i++) {
			
			if(files[i].getName().endsWith(".java")) {
				String fileName = files[i].getName();
				String path = baseDir + fileName;
				String content = null;
				System.out.println(fileName);
				
				try {
					byte[] encoded = Files.readAllBytes(Paths.get(path));
					content = new String(encoded);
				} catch (IOException e) {
					System.out.println("An error occured accessing:" + fileName);
				}
				
				System.out.print(content);
			}
			
			
			/*if(files[i].getName().endsWith(".java")) {
			
				parse(files[i]);
			}*/
			
			

			//parse("public class Bar {}");
		}
	}
	
	
	public static void parse(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source.toCharArray());
		
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			// set up hash maps and sets
			Map<String, Integer> declareAmount = new HashMap<>();
			Set<String> declareNames = new HashSet<String>();
			Map<String, Integer> refAmount = new HashMap<>();
			
			/*check if the node's Java type can be added to the set
			 * (i.e. not already listed before)
			 * if so, add it to both the set and hash map
			 * otherwise, increment the value in the hash map
			*/
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				if(this.declareNames.add(name.getIdentifier())) {
					this.declareNames.add(name.getIdentifier());
					declareAmount.put(name.getIdentifier(), 1);
					refAmount.put(name.getIdentifier(), 0);
				}
				else {
					declareAmount.put(name.getIdentifier(), declareAmount.get(name.getIdentifier())+1);
				}
				// do not continue through this node
				return false;
			}
			
			/* Check if the simple name node exists in the set of declared java types
			 * increment reference by 1 if so.
			 */
			public boolean visit(SimpleName node) {
				if(this.declareNames.contains(node.getIdentifier())) {
					declareAmount.put(node.getIdentifier(), refAmount.get(node.getIdentifier())+1);
					System.out.println(node.getIdentifier() + ". Declaration"
							+ "found: " + declareAmount.get(node.getIdentifier())
							+ "; references found: "+  refAmount.get(node.getIdentifier()));
				}
				return true;
			}
			
			
		});
		
	}

}
