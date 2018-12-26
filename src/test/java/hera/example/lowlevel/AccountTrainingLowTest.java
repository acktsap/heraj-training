package hera.example.lowlevel;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.example.AbstractTestCase;
import hera.example.lowlevel.AccountTrainingLow;
import org.junit.Test;

public class AccountTrainingLowTest extends AbstractTestCase {

  protected AccountTrainingLow accountTraining = new AccountTrainingLow();

  protected final AccountAddress accountAddress =
      AccountAddress.of("AmLbHdVs4dNpRzyLirs8cKdV26rPJJxpVXG1w2LLZ9pKfqAHHdyg");

  @Test
  public void testGetState() throws Exception {
    assertNotNull(accountTraining.getState(accountAddress));
  }

  @Test
  public void testCreateWithKey() throws Exception {
    final Account account = accountTraining.createWithKey();
    assertNotNull(account.getKey());
  }

  @Test
  public void testCreateWithEncryptedKey() throws Exception {
    final String encodedEncryptedPrivateKey =
        "47zhyDHqkjXNmzSj7QCVWYrd9VUnGxCnUZpcriAYop1VBErtSwiD9LvP6chz6fiCDTZZ7gU6w";
    final String password = "newnew";
    final Account account =
        accountTraining.createWithEncryptedKey(encodedEncryptedPrivateKey, password);
    final AccountAddress expected =
        AccountAddress.of("AmMPFynRkbdBQYmTiR4NsDLij5sy9abZFs1Rogt2yChXkMbHuEp3");
    assertEquals(expected, account.getAddress());
  }

  @Test
  public void testExportWithKeyHoldingAccount() throws Exception {
    final Account account = accountTraining.createWithKey();
    final String password = randomUUID().toString();
    final String encodedEncryptedPrivateKey =
        accountTraining.exportWithKeyHoldingAccount(account, password);
    final Account restored =
        accountTraining.createWithEncryptedKey(encodedEncryptedPrivateKey, password);
    assertNotNull(restored);
    assertEquals(account, restored);
  }

  @Test
  public void testCreateWithAddress() {
    final Account account = accountTraining.createWithAddress(accountAddress);
    assertEquals(accountAddress, account.getAddress());
  }

  @Test
  public void testCreateOnServerKeyStore() {
    final String password = randomUUID().toString();
    final Account account = accountTraining.createOnServerKeyStore(password);
    assertNotNull(account.getAddress());
    assertNull(account.getKey());
  }

  @Test
  public void testCreateByImportingToServerKeyStore() {
    final Account account = accountTraining.createWithKey();
    final String password = randomUUID().toString();

    final String encodedEncryptedPrivateKey = account.getKey().export(password).getEncoded();
    final Account imported =
        accountTraining.createByImportingToServerKeyStore(encodedEncryptedPrivateKey, password);
    assertEquals(account.getAddress(), imported.getAddress());
  }

  @Test
  public void testExportFromServer() {
    final Account account = accountTraining.createWithKey();
    final String password = randomUUID().toString();

    final String encodedEncryptedPrivateKey = account.getKey().export(password).getEncoded();
    accountTraining.createByImportingToServerKeyStore(encodedEncryptedPrivateKey, password);
    final String actual = accountTraining.exportFromServer(account.getAddress(), password);
    assertEquals(encodedEncryptedPrivateKey, actual);
  }

}
