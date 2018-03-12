import java.io.*;
import java.util.Scanner;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;

public class Main {
	public static void main (String args[]){
		
		Scanner input = new Scanner(System.in);
		
		//System.out.print("Please enter the file path: ");
		//String baseDir = input.nextLine();
		String baseDir = "/home/ugb/walker.sakatch/Desktop/SENG 300/GRPAssign1";
		File folder = new File(baseDir);
		File[] files = folder.listFiles();
		
		for(int i = 0; i < files.length; i++) {
			/*
			if(files[i].getName().endsWith(".java")) {
				System.out.println(files[i].getName());
			}
			
			
			if(files[i].getName().endsWith(".java")) {
				parse(files[i]);
			}
			
			*/

			parse("public class Bar {}");
		}
	}
	
	
	public static void parse(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source.toCharArray());
		
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		cu.accept(new ASTVisitor() {});
		
	}

}
