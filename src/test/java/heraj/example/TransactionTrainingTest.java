package heraj.example;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import hera.api.model.AccountAddress;
import hera.api.model.ClientManagedAccount;
import hera.api.model.ServerManagedAccount;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.example.AccountTraining;
import hera.example.TransactionTraining;
import org.junit.Test;

public class TransactionTrainingTest extends AbstractTestCase {

  protected AccountTraining accountTraining = new AccountTraining();
  protected TransactionTraining transactionTraining = new TransactionTraining();

  @Test
  public void testCommitWithLocalAccount() throws Exception {
    final ClientManagedAccount account = (ClientManagedAccount) accountTraining.createWithKey();

    final TxHash txHash =
        transactionTraining.commitWithLocalAccount(account);
    assertNotNull(txHash);

    final Transaction mempoolQueried = transactionTraining.getTransaction(txHash);
    assertTrue(!mempoolQueried.isConfirmed());

    waitingForNextBlockToGenerate();

    final Transaction confirmedQueried = transactionTraining.getTransaction(txHash);
    assertTrue(confirmedQueried.isConfirmed());
  }

  @Test
  public void testCommitWithRemoteAccount() throws InterruptedException {
    final String password = randomUUID().toString();
    final ServerManagedAccount account =
        (ServerManagedAccount) accountTraining.createWithPassword(password);

    final TxHash txHash =
        transactionTraining.commitWithServerAccount(account, password);
    assertNotNull(txHash);

    final Transaction mempoolQueried = transactionTraining.getTransaction(txHash);
    assertTrue(!mempoolQueried.isConfirmed());

    waitingForNextBlockToGenerate();

    final Transaction confirmedQueried = transactionTraining.getTransaction(txHash);
    assertTrue(confirmedQueried.isConfirmed());
  }

  @Test
  public void testSend() throws Exception {
    final String password = randomUUID().toString();
    final ServerManagedAccount account =
        (ServerManagedAccount) accountTraining.createWithPassword(password);

    final AccountAddress recipient = accountTraining.createWithKey().getAddress();
    final long origin = accountTraining.getState(recipient).getBalance();

    final long amount = 10L;
    transactionTraining.send(account.getAddress(), password, recipient, amount);

    waitingForNextBlockToGenerate();

    assertEquals(origin + amount, accountTraining.getState(recipient).getBalance());
  }
}
