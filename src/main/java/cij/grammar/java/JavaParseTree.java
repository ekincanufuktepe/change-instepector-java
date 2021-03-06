package cij.grammar.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JavaParseTree {

	private String unfilteredParseTree;
	private CodeComponentNode rootNode;

	public JavaParseTree(String unfilteredParseTree) {
		this.unfilteredParseTree = unfilteredParseTree;
	}

	public CodeComponentNode tokenizeParseTree() {
		//		System.out.println("String: " + unfilteredParseTree);
		//		String[] result = unfilteredParseTree.split("\\s*[\\(\\)]+\\s*");
		String[] result = unfilteredParseTree.split(" ");

		CodeComponentNode root = new CodeComponentNode(result[0], null);
		CodeComponentNode node = root; 
		this.rootNode = root;
		for(int i=1; i<result.length; i++) {
			if(result[i].equals("(") || result[i].equals(")")) {
				node.getCodeList().add(result[i]);
			}
			else if(result[i].startsWith("(")) {
				CodeComponentNode childNode = new CodeComponentNode(result[i], node);
				node.getChildren().add(childNode);
				node = childNode;
			}
			else if(result[i].endsWith(")")) {
				node.getCodeList().add(result[i]);
				int firstIndex = result[i].indexOf(")");
				int lastIndex = result[i].lastIndexOf(")");
				int count = 0;
				if(firstIndex == 0)
					count = lastIndex - firstIndex;
				else
					count = lastIndex - firstIndex + 1;
				//				System.out.println(firstIndex);
				//				System.out.println(lastIndex);
				for(int j=0; j<count; j++) {
					if(node.getParent() != null)
						node = node.getParent();
				}
			}
			else {
				node.getCodeList().add(result[i]);
			}
		}
		return root;
	}

	public void printTree(CodeComponentNode node, int level) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt",true));

			for(int i=0; i<level; i++) {
				System.out.print("\t");
				bw.append("\t");
			}
			if(node.getParent() == null) {
				System.out.println(node.getType() + node.getCodeList());
				bw.append(node.getType() + node.getCodeList()+"\n");
			}

			else {

				bw.append(node.getType() + " " + node.getCodeList()+"\n");
				System.out.println(node.getType() + " " + node.getCodeList());
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(CodeComponentNode child : node.getChildren()) {
			printTree(child, level+1);
		}
	}

	public CodeComponentNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(CodeComponentNode rootNode) {
		this.rootNode = rootNode;
	}

}


