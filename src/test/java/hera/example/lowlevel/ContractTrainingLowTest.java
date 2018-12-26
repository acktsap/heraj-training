package hera.example.lowlevel;

import static hera.util.ValidationUtils.assertNotNull;
import static org.junit.Assert.assertEquals;

import hera.api.model.Account;
import hera.api.model.ContractInterface;
import hera.api.model.ContractTxReceipt;
import hera.example.AbstractTestCase;
import hera.example.Data;
import hera.example.lowlevel.AccountTrainingLow;
import hera.example.lowlevel.ContractTrainingLow;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Test;

public class ContractTrainingLowTest extends AbstractTestCase {

  protected final String queryFunction = "get";
  protected final String execFunction = "set";
  protected final int intVal = 300;
  protected final String stringVal = "stringVal";

  protected AccountTrainingLow accountTraining = new AccountTrainingLow();
  protected ContractTrainingLow contractTraining = new ContractTrainingLow();

  private String getPayload() {
    final InputStream inputStream = getClass().getResourceAsStream("/payload");
    String contractPayload;
    try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
      contractPayload = scanner.useDelimiter("\\A").next();
    }
    return contractPayload;
  }

  @Test
  public void testDeploy() throws Exception {
    final Account account = accountTraining.createWithKey();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(account, contractPayload);
    assertNotNull(contractInterface.findFunction(execFunction));
    assertNotNull(contractInterface.findFunction(queryFunction));
  }

  @Test
  public void testExecute() throws Exception {
    final Account account = accountTraining.createWithKey();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(account, contractPayload);

    final ContractTxReceipt executeResult = contractTraining.execute(account,
        contractInterface, execFunction, "key", 300, stringVal);
    assertEquals("SUCCESS", executeResult.getStatus());
  }

  @Test
  public void testQuery() throws Exception {
    final Account account = accountTraining.createWithKey();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(account, contractPayload);

    final ContractTxReceipt executeResult = contractTraining.execute(account,
        contractInterface, execFunction, "key", intVal, stringVal);
    assertEquals("SUCCESS", executeResult.getStatus());

    waitingForNextBlockToGenerate();

    final Data expected = new Data();
    expected.setIntVal(intVal);
    expected.setStringVal(stringVal);
    final Data actual = contractTraining.query(contractInterface, queryFunction, "key");

    assertEquals(expected, actual);
  }

}
