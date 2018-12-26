package hera.example.highlevel;

import hera.api.model.ContractInterface;
import hera.api.model.ContractTxReceipt;
import hera.example.Data;
import hera.wallet.Wallet;

public class ContractTrainingHigh {

  public ContractInterface deploy(final Wallet wallet, final String contractPayload) {
    // TODO : use wallet to deploy it
    return null;
  }

  public ContractTxReceipt execute(final Wallet wallet,
      final ContractInterface contractInterface,
      final String functionName, final Object... args) {
    // TODO : use wallet to execute it
    return null;
  }

  public Data query(final Wallet wallet, final ContractInterface contractInterface,
      final String functionName, final Object... args) {
    // TODO : use wallet to query it
    return null;
  }

}
