package hera.example.highlevel;

import static hera.util.ValidationUtils.assertNotNull;
import static org.junit.Assert.assertEquals;

import hera.api.model.ContractInterface;
import hera.api.model.ContractTxReceipt;
import hera.example.AbstractTestCase;
import hera.example.Data;
import hera.wallet.Wallet;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Test;

public class ContractTrainingHighTest extends AbstractTestCase {

  protected final String execFunction = "set";
  protected final String queryFunction = "get";
  protected final int intVal = 300;
  protected final String stringVal = "stringVal";

  protected WalletTrainingHigh walletTraining = new WalletTrainingHigh();
  protected ContractTrainingHigh contractTraining = new ContractTrainingHigh();

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
    final Wallet wallet = walletTraining.createAndSaveKeyAndUnlock();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(wallet, contractPayload);
    assertNotNull(contractInterface.findFunction(execFunction));
    assertNotNull(contractInterface.findFunction(queryFunction));
    wallet.close();
  }

  @Test
  public void testExecute() throws Exception {
    final Wallet wallet = walletTraining.createAndSaveKeyAndUnlock();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(wallet, contractPayload);

    final ContractTxReceipt executeResult =
        contractTraining.execute(wallet, contractInterface, execFunction, "key", 300, stringVal);
    assertEquals("SUCCESS", executeResult.getStatus());

    wallet.close();
  }

  @Test
  public void testQuery() throws Exception {
    final Wallet wallet = walletTraining.createAndSaveKeyAndUnlock();
    final String contractPayload = getPayload();
    final ContractInterface contractInterface = contractTraining.deploy(wallet, contractPayload);

    final ContractTxReceipt executeResult =
        contractTraining.execute(wallet, contractInterface, execFunction, "key", 300, stringVal);
    assertEquals("SUCCESS", executeResult.getStatus());

    waitingForNextBlockToGenerate();

    final Data expected = new Data();
    expected.setIntVal(intVal);
    expected.setStringVal(stringVal);
    final Data actual = contractTraining.query(wallet, contractInterface, queryFunction, "key");

    assertEquals(expected, actual);

    wallet.close();
  }

}
