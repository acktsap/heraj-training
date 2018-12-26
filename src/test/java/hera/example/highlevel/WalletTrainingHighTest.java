package hera.example.highlevel;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import hera.api.model.Authentication;
import hera.example.AbstractTestCase;
import hera.example.highlevel.WalletTrainingHigh;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import hera.wallet.SecureWallet;
import hera.wallet.Wallet;
import java.io.FileInputStream;
import org.junit.Test;

public class WalletTrainingHighTest extends AbstractTestCase {

  protected String keyStorePath =
      System.getProperty("java.io.tmpdir") + "/" + randomUUID().toString();
  protected String keyStorePassword = "password";

  protected WalletTrainingHigh walletTraining = new WalletTrainingHigh();

  protected java.security.KeyStore supplyKeyStore() throws Exception {
    final java.security.KeyStore keyStore = java.security.KeyStore.getInstance("PKCS12");
    keyStore.load(new FileInputStream(keyStorePath), keyStorePassword.toCharArray());
    return keyStore;
  }

  @Test
  public void testCreateAndSaveKeyAndUnlock() {
    final Wallet wallet = walletTraining.createAndSaveKeyAndUnlock();
    assertTrue("Use Naive or ServerKeyStore type", !(wallet instanceof SecureWallet));
    assertNotNull(wallet.getCurrentAccount());
    wallet.close();
  }

  @Test
  public void testCreateSecureWalletAndSaveKeyAndUnlock() throws Exception {
    final java.security.KeyStore keyStore = java.security.KeyStore.getInstance("PKCS12");
    keyStore.load(null, null);
    final Wallet wallet = walletTraining.createSecureWalletAndSaveKeyAndUnlock(keyStore);
    assertTrue("Use Secure type", wallet instanceof SecureWallet);
    assertNotNull(wallet.getCurrentAccount());
    wallet.close();
  }

  @Test
  public void testCreateAndSaveKeyWithJavaKeyStore() throws Exception {
    final AergoKey key = new AergoKeyGenerator().create();
    final String password = randomUUID().toString();
    walletTraining.createAndSaveAndStoreWithJKS(keyStorePath, keyStorePassword, key, password);
    final java.security.KeyStore keyStore = supplyKeyStore();
    final Wallet wallet = walletTraining.createSecureWalletAndSaveKeyAndUnlock(keyStore);
    final boolean unlockResult = wallet.unlock(Authentication.of(key.getAddress(), password));
    assertTrue(unlockResult);
    wallet.close();
  }

}
