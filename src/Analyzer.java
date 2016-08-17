import java.util.Scanner;

public class Analyzer {

	/**
	 * Construct an Analyzer.
	 */
	public Analyzer() {
	}

	/**
	 * Analyze a block and return it with indentation and scope info.
	 * 
	 * @param input
	 *            the block to analyze
	 */
	public String analyze(String input) {
		Scanner scan = new Scanner(input);
		ScopedMap<String, Integer> sm = new ScopedMap<String, Integer>();
		String answer = "";
		int decNumber = 1;
		boolean created = false;
		while (scan.hasNext()) {
			String current = scan.next();
			if (current.equals("begin")) {
				if (created && sm.getNestingLevel() == 0)
				{
					scan.close();
					throw new IllegalArgumentException("Incorrect Grammar");
				}
				created = true;
				for (int i = 0; i < sm.getNestingLevel(); i++) {
					answer = answer + "  ";
				}
				answer = answer + "begin\n";
				sm.enterScope();
			} else if (current.equals("pass") && sm.getNestingLevel() > 0) {
				for (int i = 0; i < sm.getNestingLevel(); i++) {
					answer = answer + "  ";
				}
				answer = answer + "pass\n";
			} else if (current.equals("declare") && sm.getNestingLevel() > 0) {
				String variable = scan.next();
				for (int i = 0; i < sm.getNestingLevel(); i++) {
					answer = answer + "  ";
				}
				answer = answer + "declare " + variable;
				if (sm.isLocal(variable)) {
					answer = answer + " {illegal redeclaration}\n";
				} else {
					answer = answer + " {declaration " + decNumber + "}\n";
					sm.put(variable, decNumber);
					decNumber++;
				}
			} else if (current.equals("use") && sm.getNestingLevel() > 0) {
				String variable = scan.next();
				for (int i = 0; i < sm.getNestingLevel(); i++) {
					answer = answer + "  ";
				}
				answer = answer + "use " + variable;
				if (sm.get(variable) == null) {
					answer = answer + " {illegal undeclared use}\n";
				} else {
					int value = sm.get(variable);
					answer = answer + " {references declaration " + value + "}\n";
				}

			} else if (current.equals("end") && sm.getNestingLevel() > 0) {
				if (sm.getNestingLevel() == 0) {
					scan.close();
					throw new IllegalArgumentException("Incorrect Grammar");
				}
				sm.exitScope();
				for (int i = 0; i < sm.getNestingLevel(); i++) {
					answer = answer + "  ";
				}
				answer = answer + "end\n";
			} else {
				scan.close();
				throw new IllegalArgumentException("Incorrect Grammar");
			}
		}
		scan.close();
		if (sm.getNestingLevel() == 0 && created) {
			return answer;
		} else {
			throw new IllegalArgumentException("Incorrect Grammar");
		}
	}

}
