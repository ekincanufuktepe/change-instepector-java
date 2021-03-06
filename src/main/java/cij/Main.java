package cij;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.ChangeRuleSet;
import cij.grammar.java.Java8Lexer;
import cij.grammar.java.Java8Parser;
import cij.grammar.java.JavaParseTree;

public class Main {

	//	public static long lexerTime = 0;
	public static boolean profile = false;
	public static boolean notree = false;
	public static boolean gui = false;
	public static boolean printTree = false;
	public static boolean SLL = false;
	public static boolean diag = false;
	public static boolean bail = false;
	public static boolean x2 = false;
	public static boolean threaded = false;
	public static boolean quiet = false;
	//	public static long parserStart;
	//	public static long parserStop;
	public static Worker[] workers = new Worker[3];
	static int windex = 0;
	private static String parseTree = "";
	private static ArrayList<String> parseTreeList = new ArrayList<>();

	public static CyclicBarrier barrier;

	public static volatile boolean firstPassDone = false;

	public static class Worker implements Runnable {
		public long parserStart;
		public long parserStop;
		List<String> files;
		public Worker(List<String> files) {
			this.files = files;
		}
		@Override
		public void run() {
			parserStart = System.currentTimeMillis();
			for (String f : files) {
				parseFile(f);
			}
			parserStop = System.currentTimeMillis();
			try {
				barrier.await();
			}
			catch (InterruptedException ex) {
				return;
			}
			catch (BrokenBarrierException ex) {
				return;
			}
		}
	}

	public static void main(String[] args) {

		try {
			// Set your change report file name
			BufferedWriter bw = new BufferedWriter(new FileWriter("changeReport.txt"));
			String[] arg = new String[3];
			// Set the commit directory from PyDriller 
			String dataFileName = "commons-csv_data";
			FileRetriver retriver = new FileRetriver(dataFileName);
			for(String commit : retriver.getCommitDirectories()) {
				bw.write("Commit: "+commit+"\n");
				for(String beforeChangeFile : retriver.getCommitBeforeChangeJavaFiles(commit)) {
					if(retriver.getCommitAfterChangeJavaFiles(commit).contains(beforeChangeFile)) {
						reset();
						arg[0] = "-ptree";
						arg[1] = "./python/" + dataFileName + "/" + commit + "/before/"+ beforeChangeFile;
						arg[2] = "./python/" + dataFileName + "/" + commit + "/after/"+ beforeChangeFile;
						doAll(arg);
						JavaParseTree beforeChangeTree = new JavaParseTree(parseTreeList.get(0));
						JavaParseTree afterChangeTree = new JavaParseTree(parseTreeList.get(1));
						beforeChangeTree.tokenizeParseTree();
						afterChangeTree.tokenizeParseTree();
						ChangeRuleSet ruleSet = new ChangeRuleSet(beforeChangeTree, afterChangeTree);
						// Write the detected changes in the source code to a file
						bw.write("\tFile:"+beforeChangeFile+"\n");
						for(ChangeRule rule : ruleSet.getChangeRuleSet()) {
							if(rule.getChangeCategory() != null)
								bw.write("\t\t"+rule.getChangeCategory()+"\n");
						}
						bw.flush();
					}
					if(!retriver.getCommitAfterChangeJavaFiles(commit).contains(beforeChangeFile)) {
						bw.write("\tFile:"+beforeChangeFile+"\n");
						bw.write("\t\t"+ChangeCategory.DC_DELETE_CLASS+"\n");
						bw.flush();
					}
				}
				for(String afterChangeFile : retriver.getCommitAfterChangeJavaFiles(commit)) {
					if(!retriver.getCommitAfterChangeJavaFiles(commit).contains(afterChangeFile)) {
						bw.write("\tFile:"+afterChangeFile+"\n");
						bw.write("\t\t"+ChangeCategory.AC_ADD_CLASS+"\n");
						bw.flush();
					}
				}
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		// Test it with a single file
//		String[] arg = new String[3];
//		arg[0] = "-ptree";
//		//				arg[1] = "./python/commons-csv_data/f7c2ca216608457071ab45d05223952a69f76d58/before/CSVPrinterTest.java";
//		//				arg[2] = "./python/commons-csv_data/f7c2ca216608457071ab45d05223952a69f76d58/after/CSVPrinterTest.java";
//
//		arg[1] = "./examples/before/Unicode.java";
//		arg[2] = "./examples/after/Unicode.java";
//
//		doAll(arg);
//		JavaParseTree beforeChangeTree = new JavaParseTree(parseTreeList.get(0));
//		JavaParseTree afterChangeTree = new JavaParseTree(parseTreeList.get(1));
//		beforeChangeTree.tokenizeParseTree();
//		afterChangeTree.tokenizeParseTree();
//
//		//				beforeChangeTree.printTree(beforeChangeTree.getRootNode(), 0);
//
//		// Test rules
//
//		ChangeRuleSet ruleSet = new ChangeRuleSet(beforeChangeTree, afterChangeTree);
//		ruleSet.printChangeTypes();


	}

	public static void doAll(String[] args) {
		List<String> inputFiles = new ArrayList<String>();
		long start = System.currentTimeMillis();
		try {
			if (args.length > 0 ) {
				// for each directory/file specified on the command line
				for(int i=0; i< args.length;i++) {
					if ( args[i].equals("-notree") ) notree = true;
					else if ( args[i].equals("-gui") ) gui = true;
					else if ( args[i].equals("-ptree") ) printTree = true;
					else if ( args[i].equals("-SLL") ) SLL = true;
					else if ( args[i].equals("-bail") ) bail = true;
					else if ( args[i].equals("-diag") ) diag = true;
					else if ( args[i].equals("-2x") ) x2 = true;
					else if ( args[i].equals("-threaded") ) threaded = true;
					else if ( args[i].equals("-quiet") ) quiet = true;
					if ( args[i].charAt(0)!='-' ) { // input file name
						inputFiles.add(args[i]);
					}
				}
				List<String> javaFiles = new ArrayList<String>();
				for (String fileName : inputFiles) {
					List<String> files = getFilenames(new File(fileName));
					javaFiles.addAll(files);
				}
				doFiles(javaFiles);

				//				DOTGenerator gen = new DOTGenerator(null);
				//				String dot = gen.getDOT(Java8Parser._decisionToDFA[112], false);
				//				System.out.println(dot);
				//				dot = gen.getDOT(Java8Parser._decisionToDFA[81], false);
				//				System.out.println(dot);

				if ( x2 ) {
					System.gc();
					System.out.println("waiting for 1st pass");
					if ( threaded ) while ( !firstPassDone ) { } // spin
					System.out.println("2nd pass");
					doFiles(javaFiles);
				}
			}
			else {
				System.err.println("Usage: java Main <directory or file name>");
			}
		}
		catch(Exception e) {
			System.err.println("exception: "+e);
			e.printStackTrace(System.err);   // so we can get stack trace
		}
		long stop = System.currentTimeMillis();
		//		System.out.println("Overall time " + (stop - start) + "ms.");
		System.gc();
	}

	public static void doFiles(List<String> files) throws Exception {
		long parserStart = System.currentTimeMillis();
		//		lexerTime = 0;
		if ( threaded ) {
			barrier = new CyclicBarrier(3,new Runnable() {
				public void run() {
					report(); firstPassDone = true;
				}
			});
			int chunkSize = files.size() / 3;  // 10/3 = 3
			int p1 = chunkSize; // 0..3
			int p2 = 2 * chunkSize; // 4..6, then 7..10
			workers[0] = new Worker(files.subList(0,p1+1));
			workers[1] = new Worker(files.subList(p1+1,p2+1));
			workers[2] = new Worker(files.subList(p2+1,files.size()));
			new Thread(workers[0], "worker-"+windex++).start();
			new Thread(workers[1], "worker-"+windex++).start();
			new Thread(workers[2], "worker-"+windex++).start();
		}
		else {
			for (String f : files) {
				parseFile(f);
			}
			long parserStop = System.currentTimeMillis();
			System.out.println("Total lexer+parser time " + (parserStop - parserStart) + "ms.");
		}
	}

	private static void report() {
		//		parserStop = System.currentTimeMillis();
		//		System.out.println("Lexer total time " + lexerTime + "ms.");
		long time = 0;
		if ( workers!=null ) {
			// compute max as it's overlapped time
			for (Worker w : workers) {
				long wtime = w.parserStop - w.parserStart;
				time = Math.max(time,wtime);
				System.out.println("worker time " + wtime + "ms.");
			}
		}
		System.out.println("Total lexer+parser time " + time + "ms.");

		System.out.println("finished parsing OK");
		//		System.out.println(ParserATNSimulator.predict_calls +" parser predict calls");
		//		System.out.println(ParserATNSimulator.retry_with_context +" retry_with_context after SLL conflict");
		//		System.out.println(ParserATNSimulator.retry_with_context_indicates_no_conflict +" retry sees no conflict");
		//		System.out.println(ParserATNSimulator.retry_with_context_predicts_same_alt +" retry predicts same alt as resolving conflict");
	}

	public static List<String> getFilenames(File f) throws Exception {
		List<String> files = new ArrayList<String>();
		getFilenames_(f, files);
		return files;
	}

	public static void getFilenames_(File f, List<String> files) throws Exception {
		// If this is a directory, walk each file/dir in that directory
		if (f.isDirectory()) {
			String flist[] = f.list();
			for(int i=0; i < flist.length; i++) {
				getFilenames_(new File(f, flist[i]), files);
			}
		}

		// otherwise, if this is a java file, parse it!
		else if ( ((f.getName().length()>5) &&
				f.getName().substring(f.getName().length()-5).equals(".java")) )
		{
			files.add(f.getAbsolutePath());
		}
	}

	public static void parseFile(String f) {
		try {
			if ( !quiet ) System.err.println(f);
			// Create a scanner that reads from the input stream passed to us
			Lexer lexer = new Java8Lexer(CharStreams.fromFileName(f));

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			// Create a parser that reads from the scanner
			Java8Parser parser = new Java8Parser(tokens);

			// start parsing at the compilationUnit rule
			ParserRuleContext t = parser.compilationUnit();
			if ( printTree ) {
				parseTree = t.toStringTree(parser);
				parseTreeList.add(parseTree);
				//				System.out.println("Parse Tree: " + parseTree);
				Token start = t.getStart();
				//				System.out.println("Start: "+start.getText());
				//				System.out.println("Rule context Text: " + t);
			}
		}
		catch (Exception e) {
			System.err.println("parser exception: "+e);
			e.printStackTrace();   // so we can get stack trace
		}
	}

	private static void reset() {
		profile = false;
		notree = false;
		gui = false;
		printTree = false;
		SLL = false;
		diag = false;
		bail = false;
		x2 = false;
		threaded = false;
		quiet = false;

		workers = new Worker[3];
		windex = 0;
		parseTree = "";
		parseTreeList = new ArrayList<>();

		firstPassDone = false;
	}
}
