/*
 * Infinitest, a Continuous Test Runner.
 *
 * Copyright (C) 2010-2013
 * "Ben Rady" <benrady@gmail.com>,
 * "Rod Coffin" <rfciii@gmail.com>,
 * "Ryan Breidenbach" <ryan.breidenbach@gmail.com>
 * "David Gageot" <david@gageot.net>, et al.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.infinitest.eclipse.markers;

import java.util.*;

import org.eclipse.core.resources.*;
import org.infinitest.eclipse.workspace.*;
import org.infinitest.testrunner.*;

public class TestNamePlacementStrategy implements MarkerPlacementStrategy {
	private final ResourceLookup lookup;

	public TestNamePlacementStrategy(ResourceLookup lookup) {
		this.lookup = lookup;
	}

	@Override
	public MarkerPlacement getPlacement(TestEvent event) {
		List<IResource> resources = lookup.findResourcesForClassName(event.getTestName());
		if (resources.isEmpty()) {
			return null;
		}
		int lineNumber = findLineNumberOfMethodDefinition(event.getTestName(), event.getTestMethod());
		return new MarkerPlacement(resources.get(0), lineNumber);
	}

	private int findLineNumberOfMethodDefinition(String testName, String testMethod) {
		int lineNumber = -1;
		// TODO: e.g. use javassist (with correct classpath
		// ClassPool classPool = new ClassPool();
		// classPool.appendClassPath(cp);
		// try {
		// CtClass cc = classPool.get(testName);
		// CtMethod methodX = cc.getDeclaredMethod(testMethod);
		// lineNumber = methodX.getMethodInfo().getLineNumber(0);
		// } catch (NotFoundException e) {
		// // TODO: really swallow?
		// }
		return lineNumber >= 0 ? lineNumber : 1;
	}

}
