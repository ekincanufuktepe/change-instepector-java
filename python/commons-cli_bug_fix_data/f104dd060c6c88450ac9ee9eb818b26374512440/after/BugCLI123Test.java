/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.cli2.bug;

import junit.framework.TestCase;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;

/**
 * Group options are not added to the command line when child elements are
 * detected. This causes the validation of maximum and minimum to fail.
 *
 * @author Oliver Heger
 * @version $Id$
 */
public class BugCLI123Test extends TestCase {
    /** An option of the parent group. */
    private Option parentOption;

    /** An option of the child group. */
    private Option childOption1;

    /** Another option of the child group. */
    private Option childOption2;

    /** The parent group. */
    private Group parentGroup;

    /** The child group. */
    private Group childGroup;

    /** The parser. */
    private Parser parser;

    protected void setUp() throws Exception {
        super.setUp();
        final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
        final ArgumentBuilder abuilder = new ArgumentBuilder();
        final GroupBuilder gbuilder = new GroupBuilder();
        parentOption = obuilder.withLongName("parent").withShortName("p")
                .withArgument(abuilder.withName("name").create()).create();
        childOption1 = obuilder.withLongName("child").withShortName("c")
                .withArgument(abuilder.withName("c").create()).create();
        childOption2 = obuilder.withLongName("sub").withShortName("s")
                .withArgument(abuilder.withName("s").create()).create();
        childGroup = gbuilder.withName("childOptions").withMinimum(0)
                .withMaximum(2).withOption(childOption1).withOption(
                        childOption2).create();
        parentGroup = gbuilder.withName("parentOptions").withMinimum(1)
                .withMaximum(1).withOption(parentOption).withOption(childGroup)
                .create();
        parser = new Parser();
        parser.setGroup(parentGroup);
    }

    /**
     * A single option of the child group is specified.
     */
    public void testSingleChildOption() throws OptionException {
        CommandLine cl = parser.parse(new String[] { "--child", "test" });
        assertTrue("Child option not found", cl.hasOption(childOption1));
        assertEquals("Wrong value for option", "test", cl
                .getValue(childOption1));
        assertTrue("Child group not found", cl.hasOption(childGroup));
    }

    /**
     * Two options of the child group are specified.
     */
    public void testMultipleChildOptions() throws OptionException {
        CommandLine cl = parser.parse(new String[] { "--child", "test",
                "--sub", "anotherTest" });
        assertTrue("Child option not found", cl.hasOption(childOption1));
        assertEquals("Wrong value for option", "test", cl
                .getValue(childOption1));
        assertTrue("Sub option not found", cl.hasOption(childOption2));
        assertEquals("Wrong value for sub option", "anotherTest", cl
                .getValue(childOption2));
        assertTrue("Child group not found", cl.hasOption(childGroup));
    }

    /**
     * The option defined for the parent group is specified.
     */
    public void testSingleParentOption() throws OptionException {
        CommandLine cl = parser.parse(new String[] { "--parent", "yes" });
        assertTrue("Parent option not found", cl.hasOption(parentOption));
        assertEquals("Wrong value for option", "yes", cl.getValue(parentOption));
        assertFalse("Found child group", cl.hasOption(childGroup));
    }

    /**
     * The parent option and an option of the child group is specified. This
     * should cause an exception.
     */
    public void testParentOptionAndChildOption() throws OptionException {
        try {
            parser.parse(new String[] { "--parent", "error", "--child",
                    "exception" });
            fail("Maximum restriction for parent not verified!");
        } catch (OptionException oex) {
            // ok
        }
    }
}
