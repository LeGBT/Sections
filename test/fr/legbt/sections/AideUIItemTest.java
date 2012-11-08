/* 
 *    This file is part of Sections.
 *    Copyright © 2012 Alban Avenant
 *
 *     Sections is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *     
 *     Sections is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     
 *     You should have received a copy of the GNU General Public License
 *     along with Sections.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package fr.legbt.sections;

import org.junit.*;
import static org.junit.Assert.*;

public class AideUIItemTest {
	private Sections sect;
	private AideUIItem item;

	@Before
		public void setUp(){
			sect = new Sections();
			item = new AideUIItem(sect,1280,720,0.8f,1f,222,223);
		}

	@Test
		public void test_getFX(){
			assertEquals(-1,item.getFX(0),0.001);
			assertEquals(1,item.getFX(1280),0.001);
			assertEquals(item.getFX(640),0,0.001);
		}

	@Test
		public void test_getFY(){
			assertEquals(-1,item.getFY(0),0.001);
			assertEquals(1,item.getFY(720),0.001);
			assertEquals(0,item.getFY(360),0.001);
		}

	@Test
		public void test_getFHeight(){
			assertEquals(0.089f,item.getFHeight(64),0.001f);
		}

	@Test
		public void test_getFWidth(){
			assertEquals(0.64f,item.getFWidth(820),0.001f);
		}
}
