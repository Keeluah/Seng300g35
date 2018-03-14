import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;

public class Main {
	static int targetDeclare = 0;
	static int targetRef = 0;
	public static void main (String args[]){
		
		/*
		 * Command line arguments. 
		 * args[0] is the directory we are looking for
		 * args[1] is the type that we are looking for
		*/
		String baseDir = args[0];
		String typeSearched = args[1];
		searchFiles(baseDir, typeSearched);
	}	

	
	public static void searchFiles(String baseDir, String typeSearched) {
		//files is a File array that contains each file inside the directory specified.
				File folder = new File(baseDir);
				File[] files = folder.listFiles();
				
				try {
					//Loop that checks each file in the files array.
					for(int i = 0; i < files.length; i++) {
						
						//We only want to parse java files.
						if(files[i].getName().endsWith(".java")) {
							String fileName = files[i].getName();
							String path = baseDir + '/' + fileName;
							String source = sourceToString(path, fileName);
							//Parse the given code for the type searched
							parse(source, typeSearched);
						}
					}
				} catch (NullPointerException e) {
					System.out.println("That directory is empty or does not exist.");
				}
				
				//Print out message displaying all declarations and references. (End of program)
				System.out.println(typeSearched + ". Declarations found: " + targetDeclare + ". References found: " + targetRef);
	}
	
	public static String sourceToString(String path, String fileName) {
		String source = null;
		//Take the contents of the file and store it into a String called source.
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			source = new String(encoded);
		//Print error message if something goes wrong.
		} catch (IOException e) {
			System.out.println("An error occured accessing:" + fileName);
		}
		return source;
	}
	
	//Method used to parse the code for each file.
	public static void parse(String source, String typeSearched) {
		
		//Basic setup of the parser.
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 
		//Begin parsing
		cu.accept(new ASTVisitor() {
		
			//Simple type represents a named class type.
			public boolean visit(SimpleType node) {
				String name = node.getName().getFullyQualifiedName();
				if(name.equals(typeSearched)){
					targetRef += 1;
				}
				//Continue to children nodes.
				return true;
			}
			
			//Primitive Type represents primitives.
			public boolean visit(PrimitiveType node) {
				String name = node.getPrimitiveTypeCode().toString();
				if(name.equals(typeSearched)){
					targetRef += 1;
				}
				//Continue to children nodes.
				return true;
			}
			
			//Type Declaration represents the declaration of a class or interface
			public boolean visit(TypeDeclaration node) {
				String name = node.getName().getFullyQualifiedName();
				if(name.equals(typeSearched)){
					targetDeclare += 1;
				}
				//Continue to children nodes.
				return true;
			}
			
			public boolean visit(EnumDeclaration node) {
				System.out.println(node.getName());
				String name = node.getName().getFullyQualifiedName();
				if(name.equals(typeSearched)) {
					targetDeclare += 1;
				}
				return true;
			}
			
			
		});
		
	}

}
