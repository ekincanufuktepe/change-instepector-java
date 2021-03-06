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

package org.apache.commons.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;

import junit.framework.TestCase;

/** 
 * Test case for the HelpFormatter class 
 *
 * @author Slawek Zachcial
 * @author John Keyes ( john at integralsource.com )
 * @author brianegge
 **/
public class HelpFormatterTest extends TestCase
{
   private static final String EOL = System.getProperty("line.separator");

   public static void main( String[] args )
   {
      String[] testName = { HelpFormatterTest.class.getName() };
      junit.textui.TestRunner.main(testName);
   }

   public void testFindWrapPos() throws Exception
   {
      HelpFormatter hf = new HelpFormatter();

      String text = "This is a test.";
      //text width should be max 8; the wrap postition is 7
      assertEquals("wrap position", 7, hf.findWrapPos(text, 8, 0));
      //starting from 8 must give -1 - the wrap pos is after end
      assertEquals("wrap position 2", -1, hf.findWrapPos(text, 8, 8));
      //if there is no a good position before width to make a wrapping look for the next one
      text = "aaaa aa";
      assertEquals("wrap position 3", 4, hf.findWrapPos(text, 3, 0));
   }

   public void testPrintWrapped() throws Exception
   {
      StringBuffer sb = new StringBuffer();
      HelpFormatter hf = new HelpFormatter();

      String text = "This is a test.";
      String expected;

      expected = "This is a" + hf.getNewLine() + "test.";
      hf.renderWrappedText(sb, 12, 0, text);
      assertEquals("single line text", expected, sb.toString());

      sb.setLength(0);
      expected = "This is a" + hf.getNewLine() + "    test.";
      hf.renderWrappedText(sb, 12, 4, text);
      assertEquals("single line padded text", expected, sb.toString());

      text = "  -p,--period <PERIOD>  PERIOD is time duration of form " +
          "DATE[-DATE] where DATE has form YYYY[MM[DD]]";

      sb.setLength(0);
      expected = "  -p,--period <PERIOD>  PERIOD is time duration of" +
                 hf.getNewLine() +
                 "                        form DATE[-DATE] where DATE" +
                 hf.getNewLine() +
                 "                        has form YYYY[MM[DD]]";
      hf.renderWrappedText(sb, 53, 24, text);
      assertEquals("single line padded text 2", expected, sb.toString());

      text =
         "aaaa aaaa aaaa" + hf.getNewLine() +
         "aaaaaa" + hf.getNewLine() +
         "aaaaa";

      expected = text;
      sb.setLength(0);
      hf.renderWrappedText(sb, 16, 0, text);
      assertEquals("multi line text", expected, sb.toString());

      expected =
         "aaaa aaaa aaaa" + hf.getNewLine() +
         "    aaaaaa" + hf.getNewLine() +
         "    aaaaa";
      sb.setLength(0);
      hf.renderWrappedText(sb, 16, 4, text);
      assertEquals("multi-line padded text", expected, sb.toString());
   }

   public void testPrintOptions() throws Exception
   {
       StringBuffer sb = new StringBuffer();
       HelpFormatter hf = new HelpFormatter();
       final int leftPad = 1;
       final int descPad = 3;
       final String lpad = hf.createPadding(leftPad);
       final String dpad = hf.createPadding(descPad);
       Options options = null;
       String expected = null;

       options = new Options().addOption("a", false, "aaaa aaaa aaaa aaaa aaaa");
       expected = lpad + "-a" + dpad + "aaaa aaaa aaaa aaaa aaaa";
       hf.renderOptions(sb, 60, options, leftPad, descPad);
       assertEquals("simple non-wrapped option", expected, sb.toString());

       int nextLineTabStop = leftPad+descPad+"-a".length();
       expected =
           lpad + "-a" + dpad + "aaaa aaaa aaaa" + hf.getNewLine() +
           hf.createPadding(nextLineTabStop) + "aaaa aaaa";
       sb.setLength(0);
       hf.renderOptions(sb, nextLineTabStop+17, options, leftPad, descPad);
       assertEquals("simple wrapped option", expected, sb.toString());


       options = new Options().addOption("a", "aaa", false, "dddd dddd dddd dddd");
       expected = lpad + "-a,--aaa" + dpad + "dddd dddd dddd dddd";
       sb.setLength(0);
       hf.renderOptions(sb, 60, options, leftPad, descPad);
       assertEquals("long non-wrapped option", expected, sb.toString());

       nextLineTabStop = leftPad+descPad+"-a,--aaa".length();
       expected =
           lpad + "-a,--aaa" + dpad + "dddd dddd" + hf.getNewLine() +
           hf.createPadding(nextLineTabStop) + "dddd dddd";
       sb.setLength(0);
       hf.renderOptions(sb, 25, options, leftPad, descPad);
       assertEquals("long wrapped option", expected, sb.toString());

       options = new Options().
           addOption("a", "aaa", false, "dddd dddd dddd dddd").
           addOption("b", false, "feeee eeee eeee eeee");
       expected =
           lpad + "-a,--aaa" + dpad + "dddd dddd" + hf.getNewLine() +
           hf.createPadding(nextLineTabStop) + "dddd dddd" + hf.getNewLine() +
           lpad + "-b      " + dpad + "feeee eeee" + hf.getNewLine() +
           hf.createPadding(nextLineTabStop) + "eeee eeee";
       sb.setLength(0);
       hf.renderOptions(sb, 25, options, leftPad, descPad);
       assertEquals("multiple wrapped options", expected, sb.toString());
   }

   public void testAutomaticUsage() throws Exception
   {
       HelpFormatter hf = new HelpFormatter();
       Options options = null;
       String expected = "usage: app [-a]";
       ByteArrayOutputStream out = new ByteArrayOutputStream( );
       PrintWriter pw = new PrintWriter( out );

       options = new Options().addOption("a", false, "aaaa aaaa aaaa aaaa aaaa");
       hf.printUsage( pw, 60, "app", options );
       pw.flush();
       assertEquals("simple auto usage", expected, out.toString().trim());
       out.reset();

       expected = "usage: app [-a] [-b]";
       options = new Options().addOption("a", false, "aaaa aaaa aaaa aaaa aaaa")
       .addOption("b", false, "bbb" );
       hf.printUsage( pw, 60, "app", options );
       pw.flush();
       assertEquals("simple auto usage", expected, out.toString().trim());
       out.reset();
   }

    // This test ensures the options are properly sorted
    // See https://issues.apache.org/jira/browse/CLI-131
    public void testPrintUsage() {
        Option optionA = new Option("a", "first");
        Option optionB = new Option("b", "second");
        Option optionC = new Option("c", "third");
        Options opts = new Options();
        opts.addOption(optionA);
        opts.addOption(optionB);
        opts.addOption(optionC);
        HelpFormatter helpFormatter = new HelpFormatter();
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(bytesOut);
        helpFormatter.printUsage(printWriter, 80, "app", opts);
        printWriter.close();
        assertEquals("usage: app [-a] [-b] [-c]" + EOL, bytesOut.toString());
    }

    // uses the test for CLI-131 to implement CLI-155
    public void testPrintSortedUsage() {
        Option optionA = new Option("a", "first");
        Option optionB = new Option("b", "second");
        Option optionC = new Option("c", "third");
        Options opts = new Options();
        opts.addOption(optionA);
        opts.addOption(optionB);
        opts.addOption(optionC);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setOptionComparator(
            new Comparator() { 
                public int compare(Object o1, Object o2) {
                    // reverses the fuctionality of the default comparator
                    Option opt1 = (Option)o1;
                    Option opt2 = (Option)o2;
                    return opt2.getKey().compareToIgnoreCase(opt1.getKey());
                }
            } );
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(bytesOut);
        helpFormatter.printUsage(printWriter, 80, "app", opts);
        printWriter.close();
        assertEquals("usage: app [-c] [-b] [-a]" + EOL, bytesOut.toString());
    }

    public void testPrintOptionGroupUsage() {
        OptionGroup group = new OptionGroup();
        group.addOption(OptionBuilder.create("a"));
        group.addOption(OptionBuilder.create("b"));
        group.addOption(OptionBuilder.create("c"));

        Options options = new Options();
        options.addOptionGroup(group);

        StringWriter out = new StringWriter();

        HelpFormatter formatter = new HelpFormatter();
        formatter.printUsage(new PrintWriter(out), 80, "app", options);

        assertEquals("usage: app [-a | -b | -c]" + EOL, out.toString());
    }

    public void testPrintRequiredOptionGroupUsage() {
        OptionGroup group = new OptionGroup();
        group.addOption(OptionBuilder.create("a"));
        group.addOption(OptionBuilder.create("b"));
        group.addOption(OptionBuilder.create("c"));
        group.setRequired(true);

        Options options = new Options();
        options.addOptionGroup(group);

        StringWriter out = new StringWriter();

        HelpFormatter formatter = new HelpFormatter();
        formatter.printUsage(new PrintWriter(out), 80, "app", options);

        assertEquals("usage: app -a | -b | -c" + EOL, out.toString());
    }

}
