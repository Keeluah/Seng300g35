package SENG300;
import static org.junit.Assert.*;

import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MainTest{
	private String baseDirectory = "/home/ugb/brandt.davis/Desktop/SENG300/Group1";
	private ASTParser parser = ASTParser.newParser(AST.JLS8);
	
	@Before
	public void initialize() {
		
	}
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test//(expected = NullPointerException.class)//
	public void testInvalidPath() {	
		exception.expect(NullPointerException.class);
		Main.sourceToString(null, null);
	}	
	
	@Test//(expected = IllegalArgumentException.class)
	public void testInvalidPath1() {
		
		Main.sourceToString("1","1");	
	}
	
	@Test 
	public void testMainNotNull() {
		Main m = new Main();
		assertNotNull(m);	
	}
	
	@Test 
	public void searchFilesTest() {
		//exception.expect(NullPointerException.class);
		Main.searchFiles(baseDirectory, "nothing.java");		
	}
	  
    @Test
    public void searchFilesTest1() {
    	assertNotNull(Main.searchFiles(baseDirectory, "nothing"));
    }
	
	 @Test
	 public void testCreateParserForJLS8() {
	    assertNotNull(ASTParser.newParser(AST.JLS8));
     }

	
	 @Test 
     public void varNotNull() {
	    exception.expect(NullPointerException.class);
	   	Main.main(new String[] {null, null});
	 }
	  

}
