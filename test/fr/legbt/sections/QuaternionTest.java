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

public class QuaternionTest {
	private Quaternion a;
	private Quaternion b;
	private Quaternion c;
	private Quaternion d;
	private Quaternion r;
	private Quaternion i;
	private Quaternion j;
	private Quaternion k;
	private Quaternion mi;
	private Quaternion mj;
	private Quaternion mk;
	private Quaternion ti;
	private Quaternion tj;
	private Quaternion tk;
	private Quaternion nul;
	private Quaternion apb;
	private Quaternion rpd;
	private Quaternion rot;

	@Before
		public void setUp(){
			nul = new Quaternion();
			r = new Quaternion(1,0,0,0,true);
			i = new Quaternion(0,1,0,0,true);
			j = new Quaternion(0,0,1,0,true);
			k = new Quaternion(0,0,0,1,true);
			mi = new Quaternion(0,-1,0,0,true);
			mj = new Quaternion(0,0,-1,0,true);
			mk = new Quaternion(0,0,0,-1,true);
			a = new Quaternion(1,1,1,1,true);
			b = new Quaternion(0,2,0,0,true);
			c = new Quaternion(0,0,3,0,true);
			d = new Quaternion(0,0,0,4,true);
			apb = new Quaternion(1,3,1,1,true);
			rpd = new Quaternion(1,0,0,4,true);
			rot = new Quaternion(30f,1,0,0);
		}

	@Test
		public void test_equals(){
			assertTrue(a.equals(a));
			assertTrue(b.equals(b));
			assertTrue(c.equals(c));
			assertTrue(d.equals(d));
			assertTrue(r.equals(r));
			assertTrue(nul.equals(nul));
		}

	@Test
		public void test_mult(){
			ti = new Quaternion(i);
			tj = new Quaternion(j);
			tk = new Quaternion(k);
			ti.mult(j);
			tj.mult(k);
			tk.mult(i);
			assertTrue(ti.equals(k));
			assertTrue(tj.equals(i));
			assertTrue(tk.equals(j));

			tj = new Quaternion(j);
			tk = new Quaternion(k);
			ti = new Quaternion(i);
			tj.mult(i);
			tk.mult(j);
			ti.mult(k);


			assertTrue(tj.equals(mk));
			assertTrue(tk.equals(mi));
			assertTrue(ti.equals(mj));
			assertFalse(ti.equals(j));
		}

	@Test
		public void test_angle(){
			assertEquals(0.9659f,rot.R(),0.0001f);
		}

	@Test
		public void test_getangle(){
			assertEquals(15,rot.getAngle(),0.00000001f);
		}


	@Test 
		public void test_conj(){
			Quaternion	apbc = new Quaternion(apb);
			Quaternion	tapb = new Quaternion(apb);
			apbc.conj();
			tapb.mult(apbc);
			assertEquals(12,tapb.R(),0.00000001);
		}

	@Test 
		public void test_norm(){
			assertEquals(2,a.norm(),0.00000001);
			assertEquals(3.46410161,apb.norm(),0.00000001);
		}


	@Test
		public void test_add(){
		Quaternion	ta = new Quaternion(a);
		Quaternion	ta2 = new Quaternion(a);
		Quaternion	tr = new Quaternion(r);
		ta.add(nul);
		ta2.add(b);
		tr.add(d);

			assertTrue(ta.equals(a));
			assertTrue(ta2.equals(apb));
			assertTrue(tr.equals(rpd));
		}

	@Test
		public void test_double(){
			assertEquals(1,((double)1)*1.,0.0000000000000001);
		}

	@Test
		public void test_scale(){
			Quaternion at = new Quaternion(a);
			Quaternion ct = new Quaternion(c);
			at.scale(1.);
			assertTrue(a.equals(at));

			ct.scale(2.1);
			assertEquals(ct.J(),6.3,0.00000001);
		}
}
