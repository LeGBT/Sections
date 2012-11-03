package fr.legbt.sections;

import org.junit.*;
import static org.junit.Assert.*;

public class AideUIItemTest {
	private Sections sect;
	private AideUIItem item1;
	private AideUIItem item2;
	private AideUIItem item3;

	@Before
		public void setUp(){
			sect = new Sections();
			item1 = new AideUIItem(sect,0,0,1280,720,222,223);
			item2 = new AideUIItem(sect,1280,720,128,72,222,223);
			item3 = new AideUIItem(sect,640,360,0,0,222,223);
		}

	@Test
		public void test_getFX(){
			assertEquals(item1.getFX(),-1,0.001);
			assertEquals(item2.getFX(),1,0.001);
			assertEquals(item3.getFX(),0,0.001);
		}

	@Test
		public void test_getFY(){
			assertEquals(item1.getFY(),-1,0.001);
			assertEquals(item2.getFY(),1,0.001);
			assertEquals(item3.getFY(),0,0.001);
		}

}
