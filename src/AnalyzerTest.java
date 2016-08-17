import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AnalyzerTest {

	@Test
	public void example() {
		Analyzer a = new Analyzer();
		assertEquals(
				"begin\n" +
				"  pass\n" +
				"  declare x {declaration 1}\n" +
				"  use y {illegal undeclared use}\n" +
				"  declare y {declaration 2}\n" +
				"  begin\n" +
				"    use x {references declaration 1}\n" +
				"    declare x {declaration 3}\n" +
				"    use x {references declaration 3}\n" +
				"    declare x {illegal redeclaration}\n" +
				"    use x {references declaration 3}\n" +
				"    declare y {declaration 4}\n" +
				"  end\n" +
				"  use x {references declaration 1}\n" +
				"end\n",
				a.analyze(
						"begin pass declare x use y declare y begin use x " + 
						"declare x use x declare x use x declare y end use x end")
				);
	}
}
