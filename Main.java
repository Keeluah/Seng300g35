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
				String path = baseDir + '/' + fileName;
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
	
	
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 
		cu.accept(new ASTVisitor() {
			Set names = new HashSet();
			 
			public boolean visit(SimpleType node) {
				Name name = node.getName();
				System.out.println("Reference of '" + name.getFullyQualifiedName() + "' at line"
					+ cu.getLineNumber(name.getStartPosition()));
				return true; // do not continue 
			}
			
			public boolean visit(PrimitiveType node) {
				System.out.println("Reference of '" + node.getPrimitiveTypeCode() + "' at line"
					+ cu.getLineNumber(node.getStartPosition()));
				return true; // do not continue 
			}
			
			public boolean visit(TypeDeclaration node) {
				SimpleName name = node.getName();
					System.out.println("Declaration of '" + name + "' at line "
							+ cu.getLineNumber(node.getStartPosition()));
				return true;
			}
			
		});
		
	}

}
