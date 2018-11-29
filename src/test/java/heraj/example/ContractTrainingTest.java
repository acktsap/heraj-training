package heraj.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import hera.api.model.Account;
import hera.api.model.ContractInterface;
import hera.api.model.ContractTxReceipt;
import hera.example.AccountTraining;
import hera.example.ContractTraining;
import hera.example.Data;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Test;

public class ContractTrainingTest extends AbstractTestCase {

  protected AccountTraining accountTraining = new AccountTraining();
  protected ContractTraining contractTraining = new ContractTraining();

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
    assertTrue(contractInterface.findFunction("set").isPresent());
    assertTrue(contractInterface.findFunction("get").isPresent());
  }

  @Test
  public void testExecute() throws Exception {
    final Account account = accountTraining.createWithKey();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(account, contractPayload);
    account.incrementNonce();

    final ContractTxReceipt executeResult = contractTraining.execute(account,
        contractInterface, "set", "key", "strvalue", 300);
    account.incrementNonce();
    assertEquals("SUCCESS", executeResult.getStatus());
  }

  @Test
  public void testQuery() throws Exception {
    final Account account = accountTraining.createWithKey();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(account, contractPayload);
    account.incrementNonce();

    final ContractTxReceipt executeResult = contractTraining.execute(account,
        contractInterface, "set", "key", "strvalue", 300);
    account.incrementNonce();
    assertEquals("SUCCESS", executeResult.getStatus());

    waitingForNextBlockToGenerate();

    final Data expected = new Data();
    expected.setVal1("strvalue");
    expected.setVal2(300);
    final Data actual = contractTraining.query(contractInterface, "get", "key");

    assertEquals(expected, actual);
  }

}
