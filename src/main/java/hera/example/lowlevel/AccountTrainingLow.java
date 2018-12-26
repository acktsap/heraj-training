package hera.example.lowlevel;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.AccountFactory;
import hera.api.model.AccountState;
import hera.api.model.Authentication;
import hera.api.model.EncryptedPrivateKey;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;

public class AccountTrainingLow {

  public AccountState getState(final AccountAddress accountAddress) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final AccountState state = aergoClient.getAccountOperation().getState(accountAddress);
    aergoClient.close();
    return state;
  }



  public Account createWithKey() {
    return new AccountFactory().create(new AergoKeyGenerator().create());
  }

  public Account createWithEncryptedKey(final String encodedEncryptedPrivateKey,
      final String password) {
    return new AccountFactory().create(AergoKey.of(encodedEncryptedPrivateKey, password));
  }

  public String exportWithKeyHoldingAccount(final Account account, final String password) {
    return account.getKey().export(password).getEncoded();
  }



  public Account createWithAddress(final AccountAddress accountAddress) {
    return new AccountFactory().create(accountAddress);
  }



  public Account createOnServerKeyStore(final String password) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final Account account = aergoClient.getKeyStoreOperation().create(password);
    aergoClient.close();
    return account;
  }

  public Account createByImportingToServerKeyStore(final String encodedEncryptedPrivateKey,
      final String password) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final Account account = aergoClient.getKeyStoreOperation()
        .importKey(EncryptedPrivateKey.of(encodedEncryptedPrivateKey), password, password);
    aergoClient.close();
    return account;
  }

  public String exportFromServer(final AccountAddress accountAddress, final String password) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final String exported =
        aergoClient.getKeyStoreOperation().exportKey(Authentication.of(accountAddress, password))
            .getEncoded();
    aergoClient.close();
    return exported;
  }

}
