package fr.legbt.sections;

import org.junit.*;
import static org.junit.Assert.*;

public class CylinderEdgeTest {
	private CylinderEdge edge;
	Vecteur x = CylinderEdge.x;
	Vecteur y = CylinderEdge.y;
	Vecteur z = CylinderEdge.z;

	@Before
		public void setUp(){
			edge = new CylinderEdge(x,y,z);
		}

	@Test
		public void test_getH(){
			assertTrue(edge.getH()<2);
			assertTrue(edge.getH()>-2);
		}
}
