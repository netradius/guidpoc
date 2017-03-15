import com.netradius.sqlserver.GuidPocApp;
import com.netradius.sqlserver.data.Record;
import com.netradius.sqlserver.data.RecordRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Abhinav Nahar
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GuidPocApp.class}, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RecordRepositoryTest {
	@Autowired
	private RecordRepository recordRepository;

	@Rollback
	@Test
	public void create() {
		Record r1 = new Record();
		UUID id = UUID.randomUUID();
		r1.setId(id);
		r1.setName("test");
		recordRepository.save(r1);

		Record r2 = recordRepository.findOne(id);
		Assert.assertTrue(r1.getName().equals(r2.getName()));
	}

	@Rollback
	@Test
	public void update() {
		Record r1 = new Record();
		UUID id = UUID.randomUUID();
		r1.setId(id);
		r1.setName("test");
		recordRepository.save(r1);

		Record r2 = recordRepository.findOne(id);
		String testName = "new test record";
		r2.setName("new test record");
		recordRepository.save(r2);

		Assert.assertTrue(testName.equals(r2.getName()));
	}

	@Rollback
	@Test
	public void delete() {
		Record r1 = new Record();
		UUID id = UUID.randomUUID();
		r1.setId(id);
		r1.setName("test");
		recordRepository.save(r1);

		Record r2 = recordRepository.findOne(id);
		recordRepository.delete(r2);
		Assert.assertTrue(recordRepository.findOne(id) == null);
	}

	@Rollback
	@Test
	public void autoGenerateGuidCreate() {
		String name = "test";
		recordRepository.insertRecord(name);
		Record r = recordRepository.findByName(name);
		UUID id = r.getId();


		Record r1 = recordRepository.findByName(name);
		Record r2 = recordRepository.findOne(id);
		Assert.assertTrue(r1.getName().equals(r2.getName()));
		Assert.assertTrue(r1.getId().equals(r2.getId()));
		recordRepository.delete(r2);
	}

	@Rollback
	@Test
	public void autoGenerateGuidUpdate() {
		String name = "test";
		recordRepository.insertRecord(name);
		Record r = recordRepository.findByName(name);
		UUID id = r.getId();

		Record r2 = recordRepository.findOne(id);
		String testName = "new test record";
		r2.setName("new test record");
		recordRepository.save(r2);

		Record r3 = recordRepository.findOne(id);

		Assert.assertTrue(testName.equals(r3.getName()));
		recordRepository.delete(r3);
	}

	@Rollback
	@Test
	public void autoGenerateGuidDelete() {
		String name = "test";
		recordRepository.insertRecord(name);
		Record r = recordRepository.findByName(name);
		UUID id = r.getId();
		recordRepository.delete(r);
		Assert.assertTrue(recordRepository.findOne(id) == null);
		Assert.assertTrue(recordRepository.findByName(name) == null);

	}

	@Rollback
	@Test
	public void guidToString() {
		String name = "test";
		recordRepository.insertRecord(name);
		String r1 =recordRepository.selectRecord(name);
		Record r2 = recordRepository.findByName(name);
		Assert.assertTrue(r1.equalsIgnoreCase(r2.getId().toString()));
		recordRepository.delete(r2);
	}

}
