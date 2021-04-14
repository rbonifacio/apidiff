package apidiff;

import org.junit.Assert;
import org.junit.Test;

import apidiff.enums.Classifier;

public class APIDiffTest {
	
	@Test
	public void testMethodBreakingChanges() {
		try {
			String r1rv60 = "52b0902592e770b8116f80f2eab7a4048b589d7d"; // commit id for revision r1rv60
			String r1rv59 = "6de1c17dda8ffdb19431ffcadbce1836867a27a9"; // commit id for revision r1rv59
			
			String out = getClass().getResource("/").getFile();
			
			APIDiff diff = new APIDiff("bc", "https://github.com/bcgit/bc-java.git");
			
			diff.setPath(out);
			
			Result res = diff.detectChangeBetweenRevisions(r1rv60, r1rv59, Classifier.API);
			
			long methodBreakingChanges = res.getChangeMethod().stream().filter(c -> c.isBreakingChange()).count();
			
			Assert.assertEquals(40, methodBreakingChanges);
			
		}
		catch(Exception ex) {
			Assert.fail();
		}
	}
	
	@Test
	public void detectChangeParticularRevision() {
		try {
			String out = getClass().getResource("/").getFile();

			APIDiff diff = new APIDiff("mockito", "https://github.com/mockito/mockito.git");

			diff.setPath(out);			
			
			Result res = diff.detectChangeAtCommit("4ad5fdc14ca4b979155d10dcea0182c82380aefa", Classifier.API);
			
			for(Change c: res.getChangeMethod()) {
				System.out.print(c.getCategory());
			}
			
		}
		catch(Exception ex) {
			Assert.fail();
		}
	}

}
