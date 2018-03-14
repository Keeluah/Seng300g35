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
	static int targetDeclare = 0;
	static int targetRef = 0;
	public static void main (String args[]){
		
		String baseDir = args[0];
		String typeSearched = args[1];
		
		//System.out.println(baseDir);
		//System.out.println(typeSearched);
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
				
				parse(content, typeSearched);
			}
		}
	}
	
	public static void parse(String sauce, String target) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(sauce.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 
		cu.accept(new ASTVisitor() {
				
			public boolean visit(SimpleType node) {
				String name = node.getName().getFullyQualifiedName();
				if(name.equals(target)){
					targetRef += 1;
				}
				return true;
			}
			
			public boolean visit(PrimitiveType node) {
				String name = node.getPrimitiveTypeCode().toString();
				if(name.equals(target)){
					targetRef += 1;
				}
				return true;
			}
			
			public boolean visit(TypeDeclaration node) {
				String name = node.getName().getIdentifier();
				if(name.equals(target)){
					targetDeclare += 1;
				}
				return true;
			}
			
		});
		
		System.out.println(target + ". Declarations found: " + targetDeclare + "; references found: " + targetRef);
	}

}
