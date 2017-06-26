package font_finder;

import static org.junit.Assert.*;

import java.io.File;



public class FontFinderTest {
	
	//Test der neuen Methode

	//Testen ob ArrayList not Null ist
	@org.junit.Test
	public void newMethod1(){
		FontFinder obj = new FontFinder();
		File file = new File("/Users/Princess/Desktop/Beispiel/Fonts");
		obj.getFontFiles(file);
		assertNotNull(obj.fonts);	
	}
	
	//Im Ordner Fonts befinden sich 6 interessante Dateien (TTF, OTF,...)
	@org.junit.Test
	public void newMethod2(){
		FontFinder obj = new FontFinder();
		File file = new File("/Users/PrincesS/Desktop/Beispiel/Fonts");
		obj.getFontFiles(file);
		assertEquals(6, obj.fonts.size());	
	}
	
	//Im Ordner TestOrdner befinden sich keine für uns interessante Dateien
	@org.junit.Test
	public void newMethod3(){
		FontFinder obj = new FontFinder();
		File file = new File("/Users/Princess/Desktop/Beispiel/TestOrdner");
		obj.getFontFiles(file);
		assertEquals(0, obj.fonts.size());	
	}
	

}


